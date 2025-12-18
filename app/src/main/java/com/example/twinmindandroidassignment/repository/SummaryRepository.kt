package com.example.twinmindandroidassignment.repository

import com.example.twinmindandroidassignment.SummaryParser
import com.example.twinmindandroidassignment.helper.FakeSummaryApi
import com.example.twinmindandroidassignment.room.dao.*
import com.example.twinmindandroidassignment.room.entities.SummaryEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SummaryRepository @Inject constructor(
    private val meetingDao: MeetingDao,
    private val transcriptDao: TranscriptDao,
    private val summaryDao: SummaryDao,
    private val api: FakeSummaryApi
) {
    suspend fun generateSummaryStreaming(meetingId: String) = withContext(Dispatchers.IO) {
        meetingDao.updateStatus(meetingId, "SUMMARIZING", null)

        val segments = transcriptDao.getSegments(meetingId).sortedBy { it.chunkIndex }
        val transcript = segments.joinToString(separator = " ") { it.text }

        val now = System.currentTimeMillis()
        summaryDao.upsert(
            SummaryEntity(
                meetingId = meetingId,
                status = "GENERATING",
                updatedAtEpochMs = now
            )
        )

        val buffer = StringBuilder()
        try {
            api.streamSummary(transcript).collect { piece ->
                buffer.append(piece)
                val parsed = SummaryParser.parse(buffer.toString())
                summaryDao.upsert(
                    SummaryEntity(
                        meetingId = meetingId,
                        title = parsed.title,
                        summary = parsed.summary,
                        actionItems = parsed.actionItems,
                        keyPoints = parsed.keyPoints,
                        status = "GENERATING",
                        updatedAtEpochMs = System.currentTimeMillis()
                    )
                )
            }

            val finalParsed = SummaryParser.parse(buffer.toString())
            summaryDao.upsert(
                SummaryEntity(
                    meetingId = meetingId,
                    title = finalParsed.title,
                    summary = finalParsed.summary,
                    actionItems = finalParsed.actionItems,
                    keyPoints = finalParsed.keyPoints,
                    status = "DONE",
                    updatedAtEpochMs = System.currentTimeMillis()
                )
            )
            meetingDao.updateStatus(meetingId, "DONE", null)
        } catch (e: Exception) {
            summaryDao.upsert(
                SummaryEntity(meetingId = meetingId,
                    status = "ERROR",
                    errorMessage = e.message ?: "Summary failed",
                    updatedAtEpochMs = System.currentTimeMillis()
                )
            )
            meetingDao.updateStatus(meetingId, "ERROR", "Summary failed: ${e.message}")
            throw e
        }
    }
}