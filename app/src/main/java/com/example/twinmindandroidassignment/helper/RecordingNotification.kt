package com.example.twinmindandroidassignment.helper

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.twinmindandroidassignment.service.RecordingForegroundService

object RecordingNotification {

    const val CHANNEL_ID = "recording_channel"
    const val NOTIF_ID = 101

    fun ensureChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val nm = context.getSystemService(NotificationManager::class.java)
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Recording",
                NotificationManager.IMPORTANCE_LOW
            )
            nm.createNotificationChannel(channel)
        }
    }

    fun build(
        context: Context,
        status: String,
        isPaused: Boolean,
        elapsedSec: Long
    ): Notification {
        ensureChannel(context)

        val stopIntent = Intent(context, RecordingForegroundService::class.java).apply {
            //action = RecordingForegroundService.ACTION_STOP
        }
        val stopPI = PendingIntent.getService(
            context,
            1,
            stopIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        val title = if (isPaused) "Paused" else "Recording"
        val time = format(elapsedSec)
        return NotificationCompat.Builder(context, CHANNEL_ID)
            //.setSmallIcon(R.drawable.)
            .setContentTitle("$title • $time")
            .setContentText(status)
            .setOngoing(true)
            .setOnlyAlertOnce(true)
            .addAction(
                NotificationCompat.Action(
                    0,
                    "Stop",
                    stopPI
                )
            )
            .build()
    }

    private fun format(sec: Long): String {
        val m = sec / 60
        val s = sec % 60
        return "%02d:%02d".format(m, s)
    }
}