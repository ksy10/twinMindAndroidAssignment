package com.example.twinmindandroidassignment

data class ParsedSummary(
    val title: String,
    val summary: String,
    val actionItems: String,
    val keyPoints: String
)

object SummaryParser {
    fun parse(markdown: String): ParsedSummary {
        fun section(
            afterHeader: String,
            untilHeader1: String? = null,
            untilHeader2: String? = null
        ): String {
            val start = markdown.indexOf(afterHeader)
            if (start == -1) return ""
            val contentStart = start + afterHeader.length
            val endCandidates = listOfNotNull(
                untilHeader1?.let { markdown.indexOf(it, contentStart).takeIf { it != -1 } },
                untilHeader2?.let { markdown.indexOf(it, contentStart).takeIf { it != -1 } }
            )
            val end = endCandidates.minOrNull() ?: markdown.length
            return markdown.substring(contentStart, end).trim()
        }

        val title = section("Title", "Summary", "Action Items")
        val summary = section("Summary", "Action Items", "Key Points")
        val action = section("Action Items", "Key Points", null)
        val keyPoints = section("Key Points", null, null)

        return ParsedSummary(
            title = title,
            summary = summary,
            actionItems = action,
            keyPoints = keyPoints
        )
    }
}
