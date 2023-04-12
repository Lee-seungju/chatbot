package com.slee2.chatbot.data.model

import android.util.Log
import androidx.lifecycle.*
import com.slee2.chatbot.data.repository.MessageRepository
import com.slee2.chatbot.utils.StatusType.ERROR
import com.slee2.chatbot.utils.StatusType.LOADING
import com.slee2.chatbot.utils.StatusType.SUCCESS
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
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
                    frequencyPenalty: Double,
                    messageList: List<Message>
    ) = viewModelScope.launch(Dispatchers.IO) {
        var allMessagesConcatenated = "\n" + message
        Log.i("MainActivity", "allMessage: $messageList")
        var index = messageList.lastIndex
        println(index)
        while (index >= 0
            && (allMessagesConcatenated + messageList[index].message).length < 512) {
            allMessagesConcatenated = "\n" + messageList[index].message + allMessagesConcatenated
            index--
        }

        Log.i("MainActivity", "MessageConcate: $allMessagesConcatenated")

        val messageObject = Message(
            message = ". . .",
            type = 0,
            status = LOADING
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
                    Log.i("MainActivity", response.body()?.choices?.firstOrNull()?.text!!)
                    val text = response.body()?.choices?.firstOrNull()?.text?.trimStart()
                    text?.let {
                        tempMessage.message = it
                        tempMessage.status = SUCCESS
                    }
                } else {
                    Log.i("MainActivity", "Failed API")
                    var text = "Error Message: "
                    response.errorBody()?.let {
                        val errorJsonString = it.string()
                        val errorMessage = JSONObject(errorJsonString)
                            .getJSONObject("error").getString("message")
                        Log.i("MainActivity", "Error Message: $errorMessage")
                        text += errorMessage
                    }
                    tempMessage.message  = text
                    tempMessage.status = ERROR
                }
                saveMessage(tempMessage)
            }

            override fun onFailure(call: Call<SendMessageResponse>, t: Throwable) {
                Log.i("MainActivity", "Error API")
                var message = "Error: " + t.message
                tempMessage.message = message
                tempMessage.status = ERROR
                saveMessage(tempMessage)
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