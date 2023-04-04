package com.slee2.chatbot.data.model

import retrofit2.http.Field

class SearchRequest(
    var model: String = "text-davinci-003",
    var prompt: String,
    var maxTokens: Int = 3900,
    var temperature: Double = 1.0,
    var topP: Int = 1,
    var penalty: Int = 0
)