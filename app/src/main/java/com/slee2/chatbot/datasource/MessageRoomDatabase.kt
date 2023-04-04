package com.slee2.chatbot.datasource

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.slee2.chatbot.data.Message
import com.slee2.chatbot.data.MessageDAO
import com.slee2.chatbot.utils.Converters

@Database(entities = arrayOf(Message::class), version = 1, exportSchema = false)
@TypeConverters(Converters::class)
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