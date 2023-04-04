package com.slee2.chatbot.repository

import androidx.annotation.WorkerThread
import androidx.lifecycle.asFlow
import com.slee2.chatbot.data.MessageDAO
import com.slee2.chatbot.data.Message
import kotlinx.coroutines.flow.Flow

class MessageRepository(private val messageDao: MessageDAO) {

    val allMessage: Flow<List<Message>> = messageDao.getAllMessage()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(message: Message) {
        messageDao.insert(message)
    }

    @WorkerThread
    suspend fun removeAll() {
        messageDao.deleteAll()
    }
}