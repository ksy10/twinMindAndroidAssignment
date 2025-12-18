package com.example.twinmindandroidassignment.di

import com.example.twinmindandroidassignment.helper.FakeSummaryApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SummaryModule {
    @Provides
    @Singleton
    fun provideFakeSummaryApi(): FakeSummaryApi = FakeSummaryApi()
}