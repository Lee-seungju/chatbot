package com.slee2.chatbot.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.slee2.chatbot.data.model.Setting
import kotlinx.coroutines.flow.Flow

@Dao
interface SettingDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(setting: Setting)

    @Query("SELECT * FROM setting WHERE type = :type")
    fun getSettingByType(type: Int): Flow<Setting>

    @Query("SELECT * FROM setting")
    fun getAllSettings(): Flow<List<Setting>>
}