package com.example.twinmindandroidassignment.helper

import kotlinx.coroutines.delay
import kotlin.random.Random

class FakeTranscriptionApi(private val failureRate: Double = 0.15) {
    suspend fun transcribe(chunkIndex: Int, filePath: String): String {
        delay(700)
        if (Random.nextDouble() < failureRate) {
            throw RuntimeException("Mock transcription failure for chunk #$chunkIndex")
        }
        return "Chunk #$chunkIndex transcript(mock). File=$filePath"
    }
}