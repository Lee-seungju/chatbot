package com.slee2.chatbot.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.slee2.chatbot.data.model.Message

@Database(entities = arrayOf(Message::class), version = 1, exportSchema = false)
abstract class MessageRoomDatabase : RoomDatabase() {

    abstract fun messageDao(): MessageDAO

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: MessageRoomDatabase? = null

        fun getDatabase(context: Context): MessageRoomDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MessageRoomDatabase::class.java,
                    "message"
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}