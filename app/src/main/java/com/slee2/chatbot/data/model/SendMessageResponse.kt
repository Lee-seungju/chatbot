package com.slee2.chatbot.data.model


import com.google.gson.annotations.SerializedName

data class SendMessageResponse(
    @SerializedName("choices")
    val choices: List<MessageResponse>,
    @SerializedName("created")
    val created: Int,
    @SerializedName("id")
    val id: String,
    @SerializedName("model")
    val model: String,
    @SerializedName("object")
    val objectX: String,
    @SerializedName("usage")
    val usage: Usage
)