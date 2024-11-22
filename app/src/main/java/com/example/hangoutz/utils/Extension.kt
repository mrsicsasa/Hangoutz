package com.example.hangoutz.utils

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter.ofPattern


@RequiresApi(Build.VERSION_CODES.O)
fun String.toDate(): LocalDateTime {
    var dateString = this.replace("T", " ")
    val formatter = ofPattern("yyyy-MM-dd HH:mm:ss")
    val dateTime = LocalDateTime.parse(dateString, formatter)
    return dateTime
}

@SuppressLint("NewApi")
fun LocalDateTime.toEventDateDPO(): String {
    val currentDate = LocalDateTime.now()
    if (currentDate.year == this.year && this.dayOfYear - currentDate.dayOfYear < 7) {
        return "${this.dayOfWeek.name.firstLetterUppercase()} @ ${this.hour.toZeroPaddedString()}:${this.minute.toZeroPaddedString()}"
    }
    return "${this.month.name.firstLetterUppercase()} ${this.dayOfMonth.toZeroPaddedString()}th @ ${this.hour.toZeroPaddedString()}:${this.minute.toZeroPaddedString()} "
}

fun Int.toZeroPaddedString(): String {
    if (this < 10) {
        return "0${this}"
    }
    return this.toString()
}

fun String.firstLetterUppercase(): String {
    return this.lowercase().replaceFirstChar { it.uppercase() }
}