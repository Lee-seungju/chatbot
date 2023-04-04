package com.slee2.chatbot.data.repository

import androidx.annotation.WorkerThread
import com.slee2.chatbot.data.db.MessageDAO
import com.slee2.chatbot.data.model.Message
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