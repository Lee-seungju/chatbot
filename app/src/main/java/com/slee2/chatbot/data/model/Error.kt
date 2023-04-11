package com.slee2.chatbot.data.model

import com.google.gson.annotations.SerializedName

data class Error (
    @SerializedName("message")
    val message: String,
    @SerializedName("type")
    val type: String,
)