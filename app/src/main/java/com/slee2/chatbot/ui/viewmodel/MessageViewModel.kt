package com.slee2.chatbot.data.model

import android.util.Log
import androidx.lifecycle.*
import com.slee2.chatbot.data.repository.MessageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.SocketTimeoutException
import javax.inject.Inject

@HiltViewModel
class MessageViewModel @Inject constructor(
    private val messageRepository: MessageRepository
    ) : ViewModel() {

    // Api
    private val _sendResult = MutableLiveData<SendMessageResponse>()
    val sendResult: LiveData<SendMessageResponse> get() = _sendResult

    fun sendMessage(message: String,
                    temperature: Double,
                    frequencyPenalty: Double
    ) = viewModelScope.launch(Dispatchers.IO) {
        val allMessagesConcatenated = messageRepository.getAllMessagesConcatenated().firstOrNull() + '\n' + message

        val messageObject = Message(
            message = ". . .",
            type = 0
        )
        val saveMessageId = messageRepository.insert(messageObject)
        val tempMessage = messageRepository.getMessageById(saveMessageId).firstOrNull()!!
        Log.i("MainActivity", "TempMessage Is : $tempMessage")

        val response = messageRepository.sendMessage(allMessagesConcatenated, temperature, frequencyPenalty)
        response.enqueue(object : Callback<SendMessageResponse> {
            override fun onResponse(
                call: Call<SendMessageResponse>,
                response: Response<SendMessageResponse>
            ) {
                if (response.isSuccessful) {
                    Log.i("MainActivity", "Success API")
                    Log.i("MainActivity", call.toString())
                    Log.i("MainActivity", response.toString())

                    Log.i("MainActivity", response.body()?.choices?.firstOrNull()?.text!!)
                    val text = response.body()?.choices?.firstOrNull()?.text?.trimStart()
                    text?.let {
                        tempMessage.message = it
                        saveMessage(tempMessage)
                    }
                } else {
                    Log.i("MainActivity", call.toString())
                    Log.i("MainActivity", response.toString())
                    Log.i("MainActivity", response.body().toString())
                }
            }

            override fun onFailure(call: Call<SendMessageResponse>, t: Throwable) {
                var message = "Error:\n"
                when (t) {
                    is SocketTimeoutException -> message += "응답 시간이 초과되었습니다."
                }
                tempMessage.message = message
                saveMessage(tempMessage)
                Log.i("MainActivity", "Fail API")
                Log.e("MainActivity", t.toString())
            }
        })
    }

    // Room
    fun saveMessage(message: Message) = viewModelScope.launch(Dispatchers.IO) {
        messageRepository.insert(message)
    }

    val allMessages: Flow<List<Message>> = messageRepository.getAllMessage()

    fun deleteAllMessage() = viewModelScope.launch(Dispatchers.IO) {
        messageRepository.removeAll()
    }
}