package com.slee2.chatbot.model

import androidx.lifecycle.*
import com.slee2.chatbot.data.Message
import com.slee2.chatbot.repository.MessageRepository
import kotlinx.coroutines.launch

class MessageViewModel(private val repository: MessageRepository) : ViewModel() {

    val allMessages: LiveData<List<Message>> = repository.allMessage.asLiveData()

    fun insert(message: Message) = viewModelScope.launch {
        repository.insert(message)
    }
}

class MessageViewModelFactory(private val repository: MessageRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MessageViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MessageViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}