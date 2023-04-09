package com.slee2.chatbot.data.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Usage(
    @field:Json(name = "completion_tokens")
    val completionTokens: Int,
    @field:Json(name = "prompt_tokens")
    val promptTokens: Int,
    @field:Json(name = "total_tokens")
    val totalTokens: Int
)