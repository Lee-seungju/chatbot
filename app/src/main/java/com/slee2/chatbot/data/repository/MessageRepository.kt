package com.slee2.chatbot.data.repository

import com.slee2.chatbot.data.model.Message
import com.slee2.chatbot.data.model.SendMessageResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Call
import retrofit2.Response

interface MessageRepository {

    suspend fun sendMessage(
        prompt: String,
        temperature: Double = 1.0,
        frequencyPenalty: Double = 0.0,
    ) : Call<SendMessageResponse>

    suspend fun insert(message: Message): Long

    suspend fun getMessageById(id: Long): Flow<Message>

    fun getAllMessagesConcatenated(): Flow<String>

    fun getLastTowMessage(): Flow<String>
    suspend fun removeAll()

    fun getAllMessage(): Flow<List<Message>>

    suspend fun saveSortMode(mode: String)

    suspend fun getSortMode(): Flow<String>
}