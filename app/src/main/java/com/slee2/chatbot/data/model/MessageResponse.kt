package com.slee2.chatbot.data.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MessageResponse(
    @field:Json(name = "finish_reason")
    val finishReason: String,
    @field:Json(name = "index")
    val index: Int,
    @field:Json(name = "logprobs")
    val logprobs: Any,
    @field:Json(name = "text")
    val text: String
)