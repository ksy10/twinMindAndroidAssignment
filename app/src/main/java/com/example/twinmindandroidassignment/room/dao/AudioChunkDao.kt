package com.example.twinmindandroidassignment.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.twinmindandroidassignment.room.entities.AudioChunkEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AudioChunkDao {
    @Query("SELECT * FROM audio_chunks WHERE meetingId = :meetingId ORDER BY indexInMeeting ASC")
    fun observeByMeeting(meetingId: String): Flow<List<AudioChunkEntity>>

    @Query("SELECT * FROM audio_chunks WHERE meetingId = :meetingId ORDER BY indexInMeeting ASC")
    suspend fun getByMeeting(meetingId: String): List<AudioChunkEntity>

    @Query("SELECT * FROM audio_chunks WHERE meetingId = :meetingId AND transcribed = 0 ORDER BY indexInMeeting ASC")
    suspend fun getPendingForTranscription(meetingId: String): List<AudioChunkEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(chunk: AudioChunkEntity)

    @Query("UPDATE audio_chunks SET transcribed = 1, failed = 0 WHERE id = :chunkId")
    suspend fun markTranscribed(chunkId: String)

    @Query("UPDATE audio_chunks SET failed = 1 WHERE id = :chunkId")
    suspend fun markFailed(chunkId: String)
}