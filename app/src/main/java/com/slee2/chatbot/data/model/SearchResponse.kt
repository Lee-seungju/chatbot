package com.slee2.chatbot.data.model

data class SearchResponse (
    val choices: List<ChatResult>,
    val usage: Usage
    ) {
    class ChatResult (
        val text: String,
        val index: Int
            )

    class Usage (
        val promptTokens: Int,
        val completionTokens: Int,
        val totalTokens: Int
            )
}