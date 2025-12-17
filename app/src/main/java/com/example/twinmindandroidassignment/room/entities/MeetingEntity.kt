package com.example.twinmindandroidassignment.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "meetings")
data class MeetingEntity(
    @PrimaryKey val id: String,
    val startedAtEpochMs: Long,
    val endedAtEpochMs: Long? = null,
    val status: String, //RECORDING/STOPPED/TRANSCRIBING/SUMMARIZING/DONE/ERROR
    val errorMessage: String? = null
)