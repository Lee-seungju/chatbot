package com.slee2.chatbot.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.slee2.chatbot.data.model.Message

@Database(
    entities = [Message::class],
    version = 2, exportSchema = false
)
abstract class MessageRoomDatabase : RoomDatabase() {

    abstract fun messageDao(): MessageDAO

    companion object {
        @Volatile
        private var INSTANCE: MessageRoomDatabase? = null

        fun getDatabase(context: Context): MessageRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MessageRoomDatabase::class.java,
                    "message-data"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }
    }
}