package com.slee2.chatbot.data.repository

import com.slee2.chatbot.data.model.Setting
import kotlinx.coroutines.flow.Flow

interface SettingRepository {

    suspend fun insert(setting: Setting)

    fun getSettingByType(type: Int): Flow<Setting>

    fun getAllSettings(): Flow<List<Setting>>

    suspend fun saveSortMode(mode: String)

    suspend fun getSortMode(): Flow<String>
}