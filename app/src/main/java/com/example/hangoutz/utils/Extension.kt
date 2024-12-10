package com.example.hangoutz.utils

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter.ofPattern
import java.util.Date
import java.util.Locale


@RequiresApi(Build.VERSION_CODES.O)
fun String.toDate(): LocalDateTime {
    val dateString = this.replace("T", " ")
    val formatter = ofPattern("yyyy-MM-dd HH:mm:ss")
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
 fun formatDateTime(dateTimeString: String): Pair<String, String> {

    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm", Locale.getDefault())
    val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
    val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

    val date = inputFormat.parse(dateTimeString)
    val formattedDate = dateFormat.format(date)
    val formattedTime = timeFormat.format(date)

    return Pair(formattedDate, formattedTime)
}

fun formatForDatabase(date : String, time : String) : String? {
    val inputDate = date
    val inputTime = time

    val combined = "$inputDate $inputTime"

    val inputFormat = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())
    val outputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    val formattedDateTime = inputFormat.parse(combined)?.let { outputFormat.format(it) }
    return formattedDateTime
}

fun convertTimeToMillis(dateTimeString: String? = null): Long {
    val formatter = SimpleDateFormat("HH:mm", Locale.getDefault())
    return if (dateTimeString != null) {
        val date = formatter.parse(dateTimeString)
        date?.time ?: 0L
    } else {
        System.currentTimeMillis()
    }
}

 fun checkIfInPast(date: String): Boolean {
    var isValid: Boolean = false
    val inputFormat = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())
    try {
        val inputDateTime = inputFormat.parse(date)
        if (inputDateTime != null && inputDateTime.before(Date())) {
            isValid = true
        } else isValid = false
    } catch (e: Exception) {
        Log.e("Error", "Exception while parsing")
    }
    return isValid
}

@RequiresApi(Build.VERSION_CODES.O)
fun String.toMilliseconds(): Long {
    val localDateTime = this.toDate()
    return localDateTime.atZone(ZoneId.systemDefault())
        .toInstant()
        .toEpochMilli()
}