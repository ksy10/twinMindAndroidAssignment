package com.example.twinmindandroidassignment.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.twinmindandroidassignment.room.entities.TranscriptSegmentEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TranscriptDao {
    @Query("SELECT * FROM transcript_segments WHERE meetingId = :meetingId ORDER BY chunkIndex ASC")
    fun observeSegments(meetingId: String): Flow<List<TranscriptSegmentEntity>>

    @Query("SELECT * FROM transcript_segments WHERE meetingId = :meetingId ORDER BY chunkIndex ASC")
    suspend fun getSegments(meetingId: String): List<TranscriptSegmentEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(segment: TranscriptSegmentEntity)

    @Query("DELETE FROM transcript_segments WHERE meetingId = :meetingId")
    suspend fun deleteForMeeting(meetingId: String)
}