package com.slee2.chatbot.di

import com.slee2.chatbot.data.repository.MessageRepository
import com.slee2.chatbot.data.repository.MessageRepositoryImpl
import com.slee2.chatbot.data.repository.SettingRepository
import com.slee2.chatbot.data.repository.SettingRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun bindMessageRepository(
        messageRepositoryImpl: MessageRepositoryImpl,
    ): MessageRepository

    @Singleton
    @Binds
    abstract fun bindSettingRepository(
        settingRepositoryImpl: SettingRepositoryImpl,
    ): SettingRepository
}