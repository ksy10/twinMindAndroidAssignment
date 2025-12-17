package com.example.twinmindandroidassignment.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.twinmindandroidassignment.room.dao.*
import com.example.twinmindandroidassignment.room.entities.*

@Database(
    entities = [
        MeetingEntity::class,
        AudioChunkEntity::class,
        TranscriptSegmentEntity::class,
        SummaryEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun meetingDao(): MeetingDao
    abstract fun audioChunkDao(): AudioChunkDao
    abstract fun transcriptDao(): TranscriptDao
    abstract fun summaryDao(): SummaryDao
}