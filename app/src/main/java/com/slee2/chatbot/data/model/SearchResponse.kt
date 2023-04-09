package com.slee2.chatbot.data.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SearchResponse(
    @field:Json(name = "choices")
    val choices: List<MessageResponse>,
    @field:Json(name = "created")
    val created: Int,
    @field:Json(name = "id")
    val id: String,
    @field:Json(name = "model")
    val model: String,
    @field:Json(name = "object")
    val objectX: String,
    @field:Json(name = "usage")
    val usage: Usage
)