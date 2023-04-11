package com.slee2.chatbot.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.slee2.chatbot.data.model.Setting
import com.slee2.chatbot.data.repository.SettingRepository
import com.slee2.chatbot.utils.SettingType.TEMPERATURE
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

    fun getTemperature() = viewModelScope.launch(Dispatchers.IO) {
        settingRepository.getSettingByType(TEMPERATURE)
    }

    val allSettings: Flow<List<Setting>> = settingRepository.getAllSettings()
}