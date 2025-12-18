package com.example.twinmindandroidassignment.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.twinmindandroidassignment.viewmodel.SummaryViewModel

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SummaryScreen(
    viewModel: SummaryViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = { TopAppBar(title = { Text("Summary") }) }
    ) { pad ->
        Column(
            modifier = Modifier
                .padding(pad)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            when (state.status) {
                "GENERATING" -> {
                    Text("Generating summary…", style = MaterialTheme.typography.titleMedium)
                    LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
                }

                "ERROR" -> {
                    Text(
                        text = "Error: ${state.errorMessage ?: "Unknown"}",
                        color = MaterialTheme.colorScheme.error
                    )
                    Button(onClick = viewModel::generateOrRetry) { Text("Retry") }
                }
            }

            SectionCard("Title", state.title)
            SectionCard("Summary", state.summary)
            SectionCard("Action Items", state.actionItems)
            SectionCard("Key Points", state.keyPoints)

            Spacer(Modifier.height(8.dp))
            Button(
                onClick = viewModel::generateOrRetry,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Generate / Regenerate Summary")
            }
        }
    }
}

@Composable
private fun SectionCard(header: String, body: String) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(Modifier.padding(12.dp)) {
            Text(header, style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(6.dp))
            Text(body.ifBlank { "—" })
        }
    }
}