package com.example.twinmindandroidassignment.helper

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeSummaryApi {
    fun streamSummary(transcript: String): Flow<String> = flow {
        val full = buildString {
            appendLine("Title")
            appendLine("Weekly Sync Meeting")
            appendLine()
            appendLine("Summary")
            appendLine("Discussed progress, blockers, and next steps. Transcript length=${transcript.length}.")
            appendLine()
            appendLine("Action Items")
            appendLine("Follow up on open tasks")
            appendLine("Send recap to team")
            appendLine()
            appendLine("Key Points")
            appendLine("Main decisions captured")
            appendLine("Risks and mitigations noted")
        }
        val chunkSize = 30
        var i = 0
        while (i < full.length) {
            val end = (i + chunkSize).coerceAtMost(full.length)
            emit(full.substring(i, end))
            i = end
            delay(120)
        }
    }
}