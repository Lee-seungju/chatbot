package com.slee2.chatbot

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import com.slee2.chatbot.chat.Message

class DBHelper(
    val context: Context?
    ) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
        companion object {
            const val DATABASE_VERSION = 1
            const val DATABASE_NAME = "USER_AUTH"

            //Table
            const val TABLE_NAME = "MESSAGE"
            const val UID = "UID"
            const val COL_CONTEXT = "CONTEXT"
        }

    override fun onCreate(db: SQLiteDatabase) {
        var sql: String = "CREATE TABLE IF NOT EXISTS " +
                "$TABLE_NAME ($UID integer primary key autoincrement, " +
                "$COL_CONTEXT text);"
        db.execSQL(sql)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        val sql = "DROP TABLE IF EXISTS $TABLE_NAME"

        db.execSQL(sql)
        onCreate(db)
    }

    fun addMessage(message: Message) {
        var db = this.writableDatabase
        val values = ContentValues()
        values.put(COL_CONTEXT, message.message)

        db.insert(TABLE_NAME, null, values)
        db.close()
        Toast.makeText(this.context, "Success", Toast.LENGTH_LONG).show()
    }

    fun test() {
        val db = this.writableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_NAME", null)
        println(cursor)
        println(cursor.count)
    }
}