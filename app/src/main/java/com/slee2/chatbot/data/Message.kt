package com.slee2.chatbot.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "message")
data class Message(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "_id") var _id: Int = 0,
    @ColumnInfo(name = "message") var message: String,
    @ColumnInfo(name = "type") var type: Int,
) {
}
