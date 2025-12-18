package com.example.twinmindandroidassignment.helper

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.work.BackoffPolicy
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.example.twinmindandroidassignment.workmanager.TranscribeWorker

object TranscriptionWorkHelper {
    @RequiresApi(Build.VERSION_CODES.O)
    fun enqueueTranscribeAll(context: Context, meetingId: String) {
        val req = OneTimeWorkRequestBuilder<TranscribeWorker>()
            .setInputData(workDataOf(TranscribeWorker.KEY_MEETING_ID to meetingId))
            .setBackoffCriteria(
                BackoffPolicy.EXPONENTIAL,
                java.time.Duration.ofSeconds(10)
            )
            .build()

        WorkManager.getInstance(context).enqueueUniqueWork(
            TranscribeWorker.UNIQUE_NAME_PREFIX + meetingId,
            ExistingWorkPolicy.REPLACE,
            req
        )
    }
}