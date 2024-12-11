package com.example.hangoutz.utils

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter.ofPattern
import java.util.Locale


@RequiresApi(Build.VERSION_CODES.O)
fun String.toDate(): LocalDateTime {
    val dateString = this.replace("T", " ")
    val formatter = ofPattern("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    val dateTime = LocalDateTime.parse(dateString, formatter)
    return dateTime
}

@SuppressLint("NewApi")
fun LocalDateTime.toEventDateDPO(): String {
    val currentDate = LocalDateTime.now()
    if (currentDate.year == this.year && this.dayOfYear - currentDate.dayOfYear < Constants.DAY_THRESHOLD) {
        return "${this.dayOfWeek.name.firstLetterUppercase()} @ ${this.hour.toZeroPaddedString()}:${this.minute.toZeroPaddedString()}"
    }
    return "${this.month.name.firstLetterUppercase()} ${this.dayOfMonth.toZeroPaddedString()}th @ ${this.hour.toZeroPaddedString()}:${this.minute.toZeroPaddedString()} "
}

fun Int.toZeroPaddedString(): String {
    if (this < Constants.PADDING_ZERO_THRESHOLD) {
        return "0${this}"
    }
    return this.toString()
}

fun String.firstLetterUppercase(): String {
    return this.lowercase().replaceFirstChar { it.uppercase() }
}
@RequiresApi(Build.VERSION_CODES.O)
fun String.toMilliseconds(): Long {
    val localDateTime = this.toDate()
    return localDateTime.atZone(ZoneId.systemDefault())
        .toInstant()
        .toEpochMilli()
}