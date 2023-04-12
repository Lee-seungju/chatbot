package com.slee2.chatbot.data.repository

import androidx.annotation.WorkerThread
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import com.slee2.chatbot.data.api.GptAPI
import com.slee2.chatbot.data.db.MessageRoomDatabase
import com.slee2.chatbot.data.model.Message
import com.slee2.chatbot.data.model.SendMessageResponse
import com.slee2.chatbot.data.repository.MessageRepositoryImpl.PreferencesKeys.SORT_MODE
import com.slee2.chatbot.utils.Sort
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import retrofit2.Call
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MessageRepositoryImpl @Inject constructor (
    private val db: MessageRoomDatabase,
    private val dataStore: DataStore<Preferences>,
    private val api: GptAPI
    ): MessageRepository {

    override suspend fun sendMessage(
        prompt: String,
        temperature: Double,
        frequencyPenalty: Double
    ): Call<SendMessageResponse> {
        val body = HashMap<String, Any>()
        body.put("model", "text-davinci-003")
        body.put("max_tokens", 3900)
        body.put("temperature", temperature)
        body.put("top_p", 1)
        body.put("frequency_penalty", frequencyPenalty)
        body.put("prompt", prompt)
        return api.sendChat(body)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    override suspend fun insert(message: Message): Long {
        return db.messageDao().insert(message)
    }

    @WorkerThread
    override suspend fun getMessageById(id: Long): Flow<Message> {
        return db.messageDao().getMessageById(id)
    }

    override fun getAllMessagesConcatenated(): Flow<String> {
        return db.messageDao().getAllMessagesConcatenated()
    }

//    override fun getLastTowMessage(): Flow<String> {
//        return db.messageDao().getLastTowMessage()
//    }

    @WorkerThread
    override suspend fun removeAll() {
        db.messageDao().deleteAll()
    }

    override fun getAllMessage(): Flow<List<Message>> {
        return db.messageDao().getAllMessage()
    }

    override fun getAllMessageRecent(): Flow<List<Message>> {
        return db.messageDao().getAllMessageRecent()
    }

    // DataStore
    private object PreferencesKeys {
        val SORT_MODE = stringPreferencesKey("sort_mode")
        val CACHE_DELETE_MODE = booleanPreferencesKey("cache_delete_mode")
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