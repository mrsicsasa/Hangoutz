package com.example.hangoutz.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatter.ofPattern


@RequiresApi(Build.VERSION_CODES.O)
fun String.toDate(): LocalDateTime {
    var dateString = this.replace("T", "")
    val formatter = ofPattern("yyyy-MM-dd HH:mm:ss")
    val dateTime = LocalDateTime.parse(dateString, formatter)
    return dateTime
}