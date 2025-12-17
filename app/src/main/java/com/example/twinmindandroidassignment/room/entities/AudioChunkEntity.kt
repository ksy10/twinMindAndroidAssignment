package com.example.twinmindandroidassignment.room.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "audio_chunks",
    indices = [Index(value = ["meetingId", "indexInMeeting"], unique = true)]
)
data class AudioChunkEntity(
    @PrimaryKey val id: String,
    val sessionId: String,
    val meetingId: String,
    val indexInMeeting: Int,
    val filePath: String,
    val durationSec: Int = 30,
    val uploadedForTranscription: Boolean = false,
    val transcribed: Boolean = false,
    val failed: Boolean = false,
    val createdAtEpochMs: Long
)