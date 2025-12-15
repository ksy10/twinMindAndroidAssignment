package com.example.twinmindandroidassignment.room.entities

data class AudioChunkEntity(
    val id: String,
    val sessionId: String,
    val index: Int,
    val filePath: String,
    val createdAt: Int,
    val durationMs: Int,
    val uploaded: Boolean,
    val transcriptionStatus: String, //PENDING/UPLOADING/DONE/FAILED
    val retryCount: Int
)