package com.example.twinmindandroidassignment.repository

import com.example.twinmindandroidassignment.helper.FakeTranscriptionApi
import com.example.twinmindandroidassignment.room.dao.*
import com.example.twinmindandroidassignment.room.entities.TranscriptSegmentEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TranscriptionRepository @Inject constructor(
    private val meetingDao: MeetingDao,
    private val chunkDao: AudioChunkDao,
    private val transcriptDao: TranscriptDao,
    private val api: FakeTranscriptionApi
) {
    suspend fun transcribeAll(meetingId: String) = withContext(Dispatchers.IO) {
        meetingDao.updateStatus(meetingId, "TRANSCRIBING", null)

        val chunks = chunkDao.getByMeeting(meetingId)
            .sortedBy { it.indexInMeeting }

        transcriptDao.deleteForMeeting(meetingId)

        var failed = false
        var lastError: String? = null

        for (c in chunks) {
            try {
                val text = api.transcribe(c.indexInMeeting, c.filePath)
                transcriptDao.upsert(
                    TranscriptSegmentEntity(
                        id = UUID.randomUUID().toString(),
                        meetingId = meetingId,
                        chunkIndex = c.indexInMeeting,
                        text = text,
                        createdAtEpochMs = System.currentTimeMillis()
                    )
                )
                chunkDao.markTranscribed(c.id)
            } catch (e: Exception) {
                failed = true
                lastError = e.message ?: "Transcription failed"
                chunkDao.markFailed(c.id)
            }
        }

        if (failed) {
            meetingDao.updateStatus(meetingId, "ERROR", "Transcript failed. Retrying all… ($lastError)")
            throw RuntimeException(lastError ?: "Transcription failed")
        } else {
            meetingDao.updateStatus(meetingId, "STOPPED", null)
        }
    }
}