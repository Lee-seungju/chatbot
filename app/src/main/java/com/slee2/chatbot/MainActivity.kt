package com.slee2.chatbot

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.slee2.chatbot.chat.Message
import com.slee2.chatbot.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var receiverName: String
    private lateinit var receiverUid: String
    private lateinit var db: DBHelper

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = DBHelper(this)

        receiverName = intent.getStringExtra("name").toString()
        receiverUid = intent.getStringExtra("uid").toString()

        supportActionBar?.title = receiverName

        binding.sendBtn.setOnClickListener {

            val message = binding.messageEdit.text.toString()
            val messageObject = Message(message, "1")

            db.addMessage(messageObject)

            db.test()
        }
    }
}