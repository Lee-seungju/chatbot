package com.slee2.chatbot.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface MessageDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(message: Message)

    @Query("DELETE FROM message")
    suspend fun deleteAll()

    @Query("SELECT * FROM message ORDER BY _id ASC")
    fun getAllMessage(): Flow<List<Message>>
}