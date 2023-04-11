package com.slee2.chatbot.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.room.Room
import com.slee2.chatbot.data.api.GptAPI
import com.slee2.chatbot.data.db.MessageRoomDatabase
import com.slee2.chatbot.data.db.SettingRoomDatabase
import com.slee2.chatbot.utils.Constants.BASE_URL
import com.slee2.chatbot.utils.Constants.DATASTORE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    // Retrofit
    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .build()
    }

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): GptAPI {
        return retrofit.create(GptAPI::class.java)
    }

    // Room
    @Singleton
    @Provides
    fun provideMessageDatabase(@ApplicationContext context: Context): MessageRoomDatabase =
        Room.databaseBuilder(
            context.applicationContext,
            MessageRoomDatabase::class.java,
            "message-data"
        ).build()

    @Singleton
    @Provides
    fun provideSettingDatabase(@ApplicationContext context: Context): SettingRoomDatabase =
        Room.databaseBuilder(
            context.applicationContext,
            SettingRoomDatabase::class.java,
            "setting-data"
        ).build()

    // DataStore
    @Singleton
    @Provides
    fun providePreferencesDataStore(@ApplicationContext context: Context): DataStore<Preferences> =
        PreferenceDataStoreFactory.create(
            produceFile = { context.preferencesDataStoreFile(DATASTORE_NAME) }
        )
}