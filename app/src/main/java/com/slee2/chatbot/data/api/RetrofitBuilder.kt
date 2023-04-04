package com.slee2.chatbot.data.api

import android.util.Log
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBuilder {

    private val BASE_URL = "https://api.openai.com/"

    val interceptorClient = OkHttpClient().newBuilder()
        .addInterceptor(ResponseInterceptor())
        .build()

    val instance : GptAPI by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(interceptorClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        retrofit.create(GptAPI::class.java)
    }
}

class ResponseInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)

        Log.i("MainActivity", request.headers().toString())
        Log.i("MainActivity", request.toString())

        return response
    }
}