package com.example.twinmindandroidassignment.di

import com.example.twinmindandroidassignment.helper.FakeTranscriptionApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TranscriptionModule {
    @Provides
    @Singleton
    fun provideFakeTranscriptionApi(): FakeTranscriptionApi = FakeTranscriptionApi()
}