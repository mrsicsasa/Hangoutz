package com.example.hangoutz.ui.screens.createEvent

import com.example.hangoutz.data.models.User

data class CreateEventState(
    var title: String = "",
    var description: String = "",
    var city: String = "",
    var street: String = "",
    var place: String = "",
    var date: String = "",
    var time: String = "",
    var participants: List<User> = emptyList(),
    var showDatePicker: Boolean = false,
    var showTimePicker: Boolean = false,
)
