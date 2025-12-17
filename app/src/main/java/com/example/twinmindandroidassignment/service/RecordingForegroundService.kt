package com.example.twinmindandroidassignment.service

import android.app.Service
import android.content.Intent
import android.os.IBinder

class RecordingForegroundService : Service() {
    override fun onBind(intent: Intent?): IBinder? = null
}