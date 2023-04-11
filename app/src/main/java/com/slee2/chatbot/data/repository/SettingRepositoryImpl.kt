package com.slee2.chatbot.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.slee2.chatbot.data.db.SettingRoomDatabase
import com.slee2.chatbot.data.model.Setting
import com.slee2.chatbot.data.repository.SettingRepositoryImpl.PreferencesKeys.SORT_MODE
import com.slee2.chatbot.utils.Sort
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SettingRepositoryImpl @Inject constructor(
    private val db: SettingRoomDatabase,
    private val dataStore: DataStore<Preferences>
) : SettingRepository {

    override suspend fun insert(setting: Setting) {
        db.settingDao().insert(setting)
    }

    override fun getSettingByType(type: Int): Flow<Setting> {
        return db.settingDao().getSettingByType(type)
    }

    override fun getAllSettings(): Flow<List<Setting>> {
        return db.settingDao().getAllSettings()
    }

    // DataStore
    private object PreferencesKeys {
        val SORT_MODE = stringPreferencesKey("sort_mode")
    }

    override suspend fun saveSortMode(mode: String) {
        dataStore.edit { prefs ->
            prefs[SORT_MODE] = mode
        }
    }

    override suspend fun getSortMode(): Flow<String> {
        return dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    exception.printStackTrace()
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map { prefs ->
                prefs[SORT_MODE] ?: Sort.ACCURACY.value
            }
    }
}