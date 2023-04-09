package com.slee2.chatbot.data.repository

import com.slee2.chatbot.adapter.MessageAdapter
import com.slee2.chatbot.data.model.Message
import com.slee2.chatbot.data.model.MessageViewModel
import com.slee2.chatbot.databinding.ActivityMainBinding

class GptAPIResource {

    lateinit var messageViewModel: MessageViewModel
    lateinit var binding: ActivityMainBinding
    lateinit var messageList: MutableList<Message>
    lateinit var messageAdapter: MessageAdapter

    fun sendChat(message: String) {
        val body = HashMap<String, Any>()
        body.put("model", "text-davinci-003")
        body.put("max_tokens", 3900)
        body.put("temperature", 1.0)
        body.put("top_p", 1)
        body.put("presence_penalty", 0)
        body.put("prompt", message)
//        RetrofitBuilder.instance.sendChat(body)
//            .enqueue(object : Callback<SearchResponse> {
//                override fun onResponse(call: Call<SearchResponse>, response: Response<SearchResponse>) {
//                    if (response.isSuccessful) {
//                        Log.i("MainActivity", "Success API")
//                        Log.i("MainActivity", call.toString())
//                        Log.i("MainActivity", response.toString())
//                        val text = response.body()!!.choices
//                        Log.i("MainActivity", text[0].text)
//
//
//
//                    } else {
//                        Log.i("MainActivity", call.toString())
//                        Log.i("MainActivity", response.toString())
//                        Log.i("MainActivity", response.body().toString())
//                    }
//                }
//
//                override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
//                    Log.i("MainActivity", "Fail API")
//                    Log.e("MainActivity", t.toString())
//                }
//            })
    }
}