package com.slee2.chatbot.data.repository

import com.slee2.chatbot.data.model.Message
import com.slee2.chatbot.data.model.SearchResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface MessageRepository {

    suspend fun sendMessage(
        prompt: String,
        temperature: Double = 1.0,
        frequencyPenalty: Double = 0.0,
    ) : Response<SearchResponse>

    suspend fun insert(message: Message)
    suspend fun removeAll()

    fun getAllMessage(): Flow<List<Message>>

    suspend fun saveSortMode(mode: String)

    suspend fun getSortMode(): Flow<String>
}