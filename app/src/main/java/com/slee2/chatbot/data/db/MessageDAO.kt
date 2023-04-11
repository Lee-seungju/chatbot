package com.slee2.chatbot.data.db

import androidx.room.*
import com.slee2.chatbot.data.model.Message
import com.slee2.chatbot.utils.StatusType.SUCCESS
import kotlinx.coroutines.flow.Flow

@Dao
interface MessageDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(message: Message): Long

    @Query("SELECT * FROM message WHERE _id = :id")
    fun getMessageById(id: Long): Flow<Message>

    @Query("SELECT GROUP_CONCAT(message, '\n') FROM message WHERE status = $SUCCESS")
    fun getAllMessagesConcatenated(): Flow<String>

    @Query("SELECT message FROM message ORDER BY _id DESC LIMIT 2")
    fun getLastTowMessage(): Flow<String>

    @Query("DELETE FROM message")
    suspend fun deleteAll()

    @Query("SELECT * FROM message ORDER BY _id ASC")
    fun getAllMessage(): Flow<List<Message>>
}