package com.example.hangoutz.data.models

import java.sql.Timestamp
import java.util.UUID

data class Event(
    val id : UUID,
    val title: String,
    val description: String,
    val city: String,
    val street: String,
    val place: String,
    val date: Timestamp
)
