package com.slee2.chatbot.data.db

import androidx.room.*
import com.slee2.chatbot.data.model.Message
import kotlinx.coroutines.flow.Flow

@Dao
interface MessageDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(message: Message): Long

    @Query("SELECT * FROM message WHERE _id = :id")
    fun getMessageById(id: Long): Flow<Message>

    @Query("SELECT GROUP_CONCAT(message, '\n') FROM message")
    fun getAllMessagesConcatenated(): Flow<String>

    @Query("DELETE FROM message")
    suspend fun deleteAll()

    @Query("SELECT * FROM message ORDER BY _id ASC")
    fun getAllMessage(): Flow<List<Message>>
}