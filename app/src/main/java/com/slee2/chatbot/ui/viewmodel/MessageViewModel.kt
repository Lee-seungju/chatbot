package com.slee2.chatbot.data.model

import androidx.lifecycle.*
import com.slee2.chatbot.data.repository.MessageRepository
import com.slee2.chatbot.data.repository.MessageRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MessageViewModel @Inject constructor(
    private val messageRepository: MessageRepository
    ) : ViewModel() {

    // Api
    private val _sendResult = MutableLiveData<SearchResponse>()
    val sendResult: LiveData<SearchResponse> get() = _sendResult

    fun sendMessage(message: String) = viewModelScope.launch(Dispatchers.IO) {
        val response = messageRepository.sendMessage(prompt = message)
        if (response.isSuccessful) {
            response.body()?.let { body ->
                _sendResult.postValue(body)
            }
        }
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