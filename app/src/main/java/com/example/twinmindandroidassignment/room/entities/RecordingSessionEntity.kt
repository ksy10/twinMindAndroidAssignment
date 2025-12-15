package com.example.twinmindandroidassignment.room.entities

data class RecordingSessionEntity(
    val id: String,
    val startedAt: Int,
    val endedAt: Int,
    val status: String, //RECORDING/PAUSED_CALL/PAUSED_FOCUS/STOPPED/ERROR
    val currentChunkIndex: Int,
    val audioSource: String,
    val errorMessage: String? = null
)