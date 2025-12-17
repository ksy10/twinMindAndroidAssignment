package com.example.twinmindandroidassignment.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.twinmindandroidassignment.room.entities.MeetingEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MeetingDao {
    @Query("SELECT * FROM meetings ORDER BY startedAtEpochMs DESC")
    fun observeAll(): Flow<List<MeetingEntity>>

    @Query("SELECT * FROM meetings WHERE id = :id LIMIT 1")
    fun observeById(id: String): Flow<MeetingEntity?>

    @Query("SELECT * FROM meetings WHERE id = :id LIMIT 1")
    suspend fun getById(id: String): MeetingEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(meeting: MeetingEntity)

    @Query("UPDATE meetings SET status = :status, errorMessage = :error WHERE id = :id")
    suspend fun updateStatus(id: String, status: String, error: String? = null)
}