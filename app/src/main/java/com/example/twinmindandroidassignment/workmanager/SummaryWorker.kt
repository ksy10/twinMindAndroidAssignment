package com.example.twinmindandroidassignment.workmanager

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.twinmindandroidassignment.repository.SummaryRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class SummaryWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted params: WorkerParameters,
    private val summaryRepo: SummaryRepository
) : CoroutineWorker(appContext, params) {

    override suspend fun doWork(): Result {
        val meetingId = inputData.getString(KEY_MEETING_ID) ?: return Result.failure()
        return try {
            summaryRepo.generateSummaryStreaming(meetingId)
            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }

    companion object {
        const val KEY_MEETING_ID = "meeting_id"
        const val UNIQUE_NAME_PREFIX = "summary_"
    }
}