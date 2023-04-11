package com.slee2.chatbot.ui.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.slee2.chatbot.adapter.MessageAdapter
import com.slee2.chatbot.data.model.*
import com.slee2.chatbot.databinding.ActivityMainBinding
import com.slee2.chatbot.ui.viewmodel.SettingViewModel
import com.slee2.chatbot.utils.SettingType.FREQUENCY_PENALTY
import com.slee2.chatbot.utils.SettingType.TEMPERATURE
import com.slee2.chatbot.utils.StatusType.SUCCESS
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity(){

    private lateinit var binding: ActivityMainBinding
    private lateinit var messageList: MutableList<Message>
    private lateinit var messageAdapter: MessageAdapter

    private val messageViewModel: MessageViewModel by viewModels()
    private val settingViewModel: SettingViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i("MainActivity", "Start Main Activity")
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        messageList = mutableListOf() // 변경: ArrayList에서 MutableList로 변경
        messageAdapter = MessageAdapter(this, messageList)

        binding.chatRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.chatRecyclerView.adapter = messageAdapter

        // 메세지 전송
        binding.sendBtn.setOnClickListener {
            val message = binding.messageEdit.text.toString()
            if (message.isNotBlank()) {
                val messageObject = Message(
                    message = message,
                    type = 1,
                    status = SUCCESS
                )

                // 데이터 저장
                messageViewModel.saveMessage(messageObject)
                var temperature = 1.0
                var frequencyPenalty = 0.0
                settingViewModel.allSettings.map { settings ->
                    for (setting in settings) {
                        when (setting.type) {
                            TEMPERATURE -> temperature = setting.value
                            FREQUENCY_PENALTY -> frequencyPenalty = setting.value
                        }
                    }
                }
                messageViewModel.sendMessage(message, temperature, frequencyPenalty)
                Log.i("MainActivity", "Save Success")

                // 입력 부분 초기화
                binding.messageEdit.setText("")
            }
        }

        // 메세지 가져오기
        lifecycleScope.launch {
            messageViewModel.allMessages.collectLatest {messages ->
                messageList.clear()
                messageList.addAll(messages)
                Log.i("MainActivity", messageList.toString())
                // 적용
                messageAdapter.notifyDataSetChanged()
                binding.chatRecyclerView.scrollToPosition(messageList.size - 1)
            }
        }

        // 환경설정 이동
        binding.settingBtn.setOnClickListener {
            val intent = Intent(this, SettingActivity::class.java)
            Log.i("MainActivity", "Start Setting Activity")
            startActivity(intent)
        }
    }
}