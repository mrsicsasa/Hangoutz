package com.example.hangoutz.ui.screens.myevents

import com.example.hangoutz.data.models.EventCardDPO

data class EventScreenState(
    var events: List<EventCardDPO> = emptyList()
)
