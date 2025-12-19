package com.example.twinmindandroidassignment.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import java.util.UUID

@HiltViewModel
class MeetingViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    val meetingId: String =
        savedStateHandle["meetingId"] ?: UUID.randomUUID().toString()
}