package com.slee2.chatbot.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.slee2.chatbot.adapter.MessageAdapter
import com.slee2.chatbot.data.Message
import com.slee2.chatbot.databinding.ActivityMainBinding
import com.slee2.chatbot.datasource.MessageRoomDatabase
import com.slee2.chatbot.model.MessageViewModel
import com.slee2.chatbot.model.MessageViewModelFactory
import com.slee2.chatbot.repository.MessageRepository

class MainActivity : AppCompatActivity() {

    private lateinit var messageViewModel: MessageViewModel
    private lateinit var binding: ActivityMainBinding
    private lateinit var messageList: MutableList<Message>
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.i("MainActivity", "Start Main Activity")
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val messageDao = MessageRoomDatabase.getDatabase(application).messageDao()
        val messageRepository = MessageRepository(messageDao)
        val messageViewModelFactory = MessageViewModelFactory(messageRepository)
        messageViewModel = ViewModelProvider(this, messageViewModelFactory).get(MessageViewModel::class.java)

        messageList = mutableListOf() // 변경: ArrayList에서 MutableList로 변경
        val messageAdapter = MessageAdapter(this, messageList)

        binding.chatRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.chatRecyclerView.adapter = messageAdapter

        // 메세지 전송
        binding.sendBtn.setOnClickListener {
            val message = binding.messageEdit.text.toString()
            val messageObject = Message(message = message, type = 1)

            // 데이터 저장
            messageViewModel.insert(messageObject)
            Log.i("MainActivity", "Save Success")

            // 입력 부분 초기화
            binding.messageEdit.setText("")
        }

        // 메세지 가져오기
        messageViewModel.allMessages.observe(this, Observer<List<Message>> { messages ->
            messageList.clear()
            for (message in messages) {
                messageList.add(message)
            }
            Log.i("MainActivity", messageList.toString())
            // 적용
            messageAdapter.notifyDataSetChanged()
            println("Count = " + messageAdapter.itemCount)
        })

        // 환경설정 이동
        binding.settingBtn.setOnClickListener {
            val intent = Intent(this, SettingActivity::class.java)
            Log.i("MainActivity", "Start Setting Activity")
            startActivity(intent)
        }
    }
}