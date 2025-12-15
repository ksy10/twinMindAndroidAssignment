package com.example.twinmindandroidassignment.room

data class SummaryEntity(
    val sessionId: String,
    val title: String,
    val summary: String,
    val actionItems: String,
    val keyPoints: String,
    val status: String, // IDLE/GENERATING/DONE/FAILED
    val errorMessage: String? = null,
    val partialStreamText: String? = null
)
