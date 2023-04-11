package com.slee2.chatbot.utils

import kotlin.math.round

class Calculator {

    fun convertIntToPreference(value: Int): Double {
        return value.toDouble() / 10
    }

    fun convertIntByPreference(value: Double): Int {
        return (value * 10).toInt()
    }

    fun convertIntToFrequencyPenalty(value: Int): Double {
        return round((value.toDouble() / 10 - 2) * 10) / 10
    }

    fun convertIntByFrequencyPenalty(value: Double): Int {
        return ((value + 2) * 10).toInt()
    }
}