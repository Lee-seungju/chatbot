package com.slee2.chatbot.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.slee2.chatbot.data.Message
import com.slee2.chatbot.databinding.ActivitySettingBinding
import com.slee2.chatbot.datasource.MessageRoomDatabase
import com.slee2.chatbot.model.MessageViewModel
import com.slee2.chatbot.model.MessageViewModelFactory
import com.slee2.chatbot.repository.MessageRepository

class SettingActivity : AppCompatActivity() {

    private lateinit var messageViewModel: MessageViewModel
    private lateinit var binding: ActivitySettingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Log.i("SettingActivity", "abc")

        val messageDao = MessageRoomDatabase.getDatabase(application).messageDao()
        val messageRepository = MessageRepository(messageDao)
        val messageViewModelFactory = MessageViewModelFactory(messageRepository)
        messageViewModel = ViewModelProvider(this, messageViewModelFactory).get(MessageViewModel::class.java)

        binding.resetBtn.setOnClickListener {
            messageViewModel.deleteAll()
            finish()
        }
    }
}