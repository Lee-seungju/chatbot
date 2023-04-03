package com.slee2.chatbot.chat

data class Message(
    var message: String?,
    var sendId: String?
) {
    constructor():this("","")
}
