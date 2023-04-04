package com.slee2.chatbot.data.api

import com.slee2.chatbot.BuildConfig.GPT_API_KEY
import com.slee2.chatbot.data.model.SearchRequest
import com.slee2.chatbot.data.model.SearchResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

interface GptAPI {

    @Headers("Authorization: Bearer ${GPT_API_KEY}",
    "Content-Type: application/json")
    @POST("v1/completions")
    fun sendChat(
        @Body params: HashMap<String, Any>
    ): Call<SearchResponse>
}