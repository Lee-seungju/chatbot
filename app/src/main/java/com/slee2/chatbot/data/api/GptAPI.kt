package com.slee2.chatbot.data.api

import com.slee2.chatbot.utils.Constants.API_KEY
import com.slee2.chatbot.data.model.SearchResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface GptAPI {

    @Headers("Authorization: Bearer ${API_KEY}",
    "Content-Type: application/json")
    @POST("v1/completions")
    fun sendChat(
        @Body params: HashMap<String, Any>
    ): Response<SearchResponse>
}