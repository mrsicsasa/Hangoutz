package com.example.hangoutz.ui.screens.createEvent

import com.example.hangoutz.data.models.Friend

data class CreateEventState(
    var title: String = "",
    var description: String = "",
    var city: String = "",
    var street: String = "",
    var place: String = "",
    var date: String = "",
    var time: String = "",
    var showDatePicker: Boolean = false,
    var showTimePicker: Boolean = false,
    val listOfFriends: List<Friend> = emptyList(),
    val isLoading: Boolean = false,
    val participants: List<Friend> = emptyList(),
    val selectedParticipants: List<Friend> = emptyList(),
    var isTitleError: Boolean = false,
    var isPlaceError: Boolean = false,
    var isDateError: Boolean = false,
    var isTimeError: Boolean = false,
    var isDescError: Boolean = false,
    var isCityError: Boolean = false,
    var isStreetError: Boolean = false,
    var formattedDateForDatabase: String = "",

)