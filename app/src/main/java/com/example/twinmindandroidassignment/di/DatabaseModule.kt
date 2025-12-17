package com.example.twinmindandroidassignment.di

import android.content.Context
import androidx.room.Room
import com.example.twinmindandroidassignment.room.AppDatabase
import com.example.twinmindandroidassignment.room.dao.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDb(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, "twinmind.db")
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    fun provideMeetingDao(db: AppDatabase): MeetingDao = db.meetingDao()
    @Provides
    fun provideChunkDao(db: AppDatabase): AudioChunkDao = db.audioChunkDao()
    @Provides
    fun provideTranscriptDao(db: AppDatabase): TranscriptDao = db.transcriptDao()
    @Provides
    fun provideSummaryDao(db: AppDatabase): SummaryDao = db.summaryDao()
}