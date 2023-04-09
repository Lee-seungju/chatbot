package com.slee2.chatbot.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.slee2.chatbot.data.model.Setting

@Database(
    entities = [Setting::class],
    version = 1, exportSchema = false
)
abstract class SettingRoomDatabase : RoomDatabase() {

    abstract fun settingDao(): SettingDAO
}