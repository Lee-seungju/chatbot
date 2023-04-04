package com.slee2.chatbot.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.slee2.chatbot.databinding.ActivitySettingBinding
import com.slee2.chatbot.data.db.MessageRoomDatabase
import com.slee2.chatbot.data.model.MessageViewModel
import com.slee2.chatbot.data.model.MessageViewModelFactory
import com.slee2.chatbot.data.repository.MessageRepository

class SettingActivity : AppCompatActivity() {

    private lateinit var messageViewModel: MessageViewModel
    private lateinit var binding: ActivitySettingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.i("SettingActivity", "Start Setting Activity")
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val messageDao = MessageRoomDatabase.getDatabase(application).messageDao()
        val messageRepository = MessageRepository(messageDao)
        val messageViewModelFactory = MessageViewModelFactory(messageRepository)
        messageViewModel = ViewModelProvider(this, messageViewModelFactory).get(MessageViewModel::class.java)

        binding.resetBtn.setOnClickListener {
            Log.i("SettingActivity", "Reset Button Click")
            messageViewModel.deleteAll()
            Log.i("SettingActivity", "Delete Success")
            finish()
        }
    }
}