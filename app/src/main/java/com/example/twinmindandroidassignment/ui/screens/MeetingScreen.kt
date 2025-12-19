package com.example.twinmindandroidassignment.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.twinmindandroidassignment.viewmodel.MeetingViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MeetingScreen(
    onSummaryClick: (String) -> Unit,
    viewModel: MeetingViewModel = hiltViewModel()
) {
    val meetingId = viewModel.meetingId

    Scaffold(
        topBar = { TopAppBar(title = { Text("Meeting") }) }
    ) { pad ->
        Column(
            modifier = Modifier
                .padding(pad)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text("Meeting ID:")
            Text(meetingId, style = MaterialTheme.typography.bodySmall)

            Button(onClick = { onSummaryClick(meetingId) }) {
                Text("Go to Summary")
            }
        }
    }
}