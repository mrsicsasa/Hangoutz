package com.example.hangoutz.ui.screens.events

import com.example.hangoutz.data.models.EventCardDPO
import java.util.UUID

data class EventScreenState(
    var events: List<EventCardDPO> = emptyList(),
    var counts: List<Pair<UUID, Int>> = emptyList()
)
