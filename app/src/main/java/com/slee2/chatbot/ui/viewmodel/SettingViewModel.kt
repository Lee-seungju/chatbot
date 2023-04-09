package com.slee2.chatbot.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.slee2.chatbot.data.model.Message
import com.slee2.chatbot.data.model.SearchResponse
import com.slee2.chatbot.data.model.Setting
import com.slee2.chatbot.data.repository.MessageRepository
import com.slee2.chatbot.data.repository.SettingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val settingRepository: SettingRepository
) : ViewModel() {

    // Room
    fun saveSetting(setting: Setting) = viewModelScope.launch(Dispatchers.IO) {
        settingRepository.insert(setting)
    }

    val allSettings: Flow<List<Setting>> = settingRepository.getAllSettings()
}