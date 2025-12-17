package com.example.twinmindandroidassignment.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.twinmindandroidassignment.room.entities.SummaryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SummaryDao {
    @Query("SELECT * FROM summaries WHERE meetingId = :meetingId LIMIT 1")
    fun observe(meetingId: String): Flow<SummaryEntity?>

    @Query("SELECT * FROM summaries WHERE meetingId = :meetingId LIMIT 1")
    suspend fun get(meetingId: String): SummaryEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(summary: SummaryEntity)
}