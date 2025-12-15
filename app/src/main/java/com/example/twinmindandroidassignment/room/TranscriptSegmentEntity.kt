package com.example.twinmindandroidassignment.room

data class TranscriptSegmentEntity(
    val id: String,
    val sessionId: String,
    val chunkIndex: Int,
    val text: String,
    val createdAt: Int
)
