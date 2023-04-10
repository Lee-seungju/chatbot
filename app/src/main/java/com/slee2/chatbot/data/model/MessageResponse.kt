package com.slee2.chatbot.data.model


import com.google.gson.annotations.SerializedName

data class MessageResponse(
    @SerializedName("finish_reason")
    val finishReason: String,
    @SerializedName("index")
    val index: Int,
    @SerializedName("logprobs")
    val logprobs: Any,
    @SerializedName("text")
    val text: String
)