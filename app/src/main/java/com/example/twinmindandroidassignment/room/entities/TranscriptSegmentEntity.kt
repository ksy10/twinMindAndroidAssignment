package com.example.twinmindandroidassignment.room.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "transcript_segments",
    indices = [Index(value = ["meetingId", "chunkIndex"], unique = true)]
)
data class TranscriptSegmentEntity(
    @PrimaryKey val id: String,
    val meetingId: String,
    val chunkIndex: Int,
    val text: String,
    val createdAtEpochMs: Long
)