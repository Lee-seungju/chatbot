package com.slee2.chatbot.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.slee2.chatbot.data.model.Message

@Database(
    entities = [Message::class],
    version = 1, exportSchema = false
)
abstract class MessageRoomDatabase : RoomDatabase() {

    abstract fun messageDao(): MessageDAO

}