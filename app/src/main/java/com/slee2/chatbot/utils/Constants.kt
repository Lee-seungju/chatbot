package com.slee2.chatbot.utils

import com.slee2.chatbot.BuildConfig

object Constants {
    const val API_KEY = BuildConfig.GPT_API_KEY
    const val BASE_URL = "https://api.openai.com/"
    const val DATASTORE_NAME = "preferences_datastore"
}