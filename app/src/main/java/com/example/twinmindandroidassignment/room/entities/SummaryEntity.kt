package com.example.twinmindandroidassignment.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "summaries")
data class SummaryEntity(
    @PrimaryKey val meetingId: String,
    val title: String = "",
    val summary: String = "",
    val actionItems: String = "",
    val keyPoints: String = "",
    val status: String, //IDLE/GENERATING/DONE/FAILED
    val errorMessage: String? = null,
    val updatedAtEpochMs: Long
)