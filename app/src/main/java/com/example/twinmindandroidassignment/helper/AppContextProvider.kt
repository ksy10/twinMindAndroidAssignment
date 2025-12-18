package com.example.twinmindandroidassignment.helper

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import jakarta.inject.Inject
import jakarta.inject.Singleton

@Singleton
class AppContextProvider @Inject constructor(
    @ApplicationContext val context: Context
)