package com.slee2.chatbot.ui.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.slee2.chatbot.adapter.MessageAdapter
import com.slee2.chatbot.data.api.RetrofitBuilder
import com.slee2.chatbot.databinding.ActivityMainBinding
import com.slee2.chatbot.data.db.MessageRoomDatabase
import com.slee2.chatbot.data.model.*
import com.slee2.chatbot.data.repository.MessageRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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
            val body = HashMap<String, Any>()
            body.put("model", "text-davinci-003")
            body.put("max_tokens", 3900)
            body.put("temperature", 1.0)
            body.put("top_p", 1)
            body.put("presence_penalty", 0)
            body.put("prompt", message)
            RetrofitBuilder.instance.sendChat(body)
                .enqueue(object : Callback<SearchResponse> {
                    override fun onResponse(call: Call<SearchResponse>, response: Response<SearchResponse>) {
                        if (response.isSuccessful) {
                            Log.i("MainActivity", "Success API")
                            Log.i("MainActivity", call.toString())
                            Log.i("MainActivity", response.toString())
                            val text = response.body()!!.choices
                            Log.i("MainActivity", text[0].text)
                        } else {
                            Log.i("MainActivity", call.toString())
                            Log.i("MainActivity", response.toString())
                            Log.i("MainActivity", response.body().toString())
                        }
                    }

                    override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                        Log.i("MainActivity", "Fail API")
                        Log.e("MainActivity", t.toString())
                    }
                })
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
        })

        // 환경설정 이동
        binding.settingBtn.setOnClickListener {
            val intent = Intent(this, SettingActivity::class.java)
            Log.i("MainActivity", "Start Setting Activity")
            startActivity(intent)
        }
    }
}