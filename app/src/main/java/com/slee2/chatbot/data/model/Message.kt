package com.slee2.chatbot.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "message")
data class Message(
    @PrimaryKey(autoGenerate = true)
    var _id: Long = 0,
    var message: String,
    var type: Int,
) {
}
