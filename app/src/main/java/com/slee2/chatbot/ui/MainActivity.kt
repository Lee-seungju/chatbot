package com.slee2.chatbot.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.slee2.chatbot.data.Message
import com.slee2.chatbot.databinding.ActivityMainBinding
import com.slee2.chatbot.datasource.MessageRoomDatabase
import com.slee2.chatbot.model.MessageViewModel
import com.slee2.chatbot.model.MessageViewModelFactory
import com.slee2.chatbot.repository.MessageRepository

class MainActivity : AppCompatActivity() {

    private lateinit var messageViewModel: MessageViewModel
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.i("MainActivity", "aababaa")
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val messageDao = MessageRoomDatabase.getDatabase(application).messageDao()
        val messageRepository = MessageRepository(messageDao)
        val messageViewModelFactory = MessageViewModelFactory(messageRepository)
        messageViewModel = ViewModelProvider(this, messageViewModelFactory).get(MessageViewModel::class.java)

        messageViewModel.allMessages.observe(this, Observer<List<Message>> { messages ->
            for (message in messages) {
                Log.i("MainActivity", message.message)
            }
        })

        binding.sendBtn.setOnClickListener {
            val message = binding.messageEdit.text.toString()
            val messageObject = Message(message = message, type = 1)

            messageViewModel.insert(messageObject)

            Log.i("MainActivity", "ok")
        }

        binding.settingBtn.setOnClickListener {
            val intent = Intent(this, SettingActivity::class.java)
            startActivity(intent)
        }
    }
}