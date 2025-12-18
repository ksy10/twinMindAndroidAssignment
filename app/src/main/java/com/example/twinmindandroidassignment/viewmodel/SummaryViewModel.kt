package com.example.twinmindandroidassignment.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.twinmindandroidassignment.helper.AppContextProvider
import com.example.twinmindandroidassignment.helper.SummaryWorkHelper
import com.example.twinmindandroidassignment.room.dao.SummaryDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class SummaryViewModel @Inject constructor(
    private val summaryDao: SummaryDao,
    private val appContextProvider: AppContextProvider,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val meetingId: String = requireNotNull(savedStateHandle["meetingId"]) {
        "meetingId is required"
    }

    val uiState: StateFlow<SummaryUiState> =
        summaryDao.observe(meetingId)
            .map { s ->
                if (s == null) SummaryUiState()
                else SummaryUiState(
                    title = s.title,
                    summary = s.summary,
                    actionItems = s.actionItems,
                    keyPoints = s.keyPoints,
                    status = s.status,
                    errorMessage = s.errorMessage
                )
            }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), SummaryUiState())

    @RequiresApi(Build.VERSION_CODES.O)
    fun generateOrRetry() {
        SummaryWorkHelper.enqueue(appContextProvider.context, meetingId)
    }
}

data class SummaryUiState(
    val title: String = "",
    val summary: String = "",
    val actionItems: String = "",
    val keyPoints: String = "",
    val status: String = "IDLE", //IDLE/GENERATING/DONE/ERROR
    val errorMessage: String? = null
)