package com.example.hangoutz.ui.screens.events

import com.example.hangoutz.data.models.EventCardDPO
import java.util.UUID

data class EventScreenState(
    var eventsGoing: List<EventCardDPO> = emptyList(),
    var eventsInveted: List<EventCardDPO> = emptyList(),
    var eventsMine: List<EventCardDPO> = emptyList(),
    var counts: List<Pair<UUID, Int>> = emptyList(),
    var isLoading: Boolean = true,
    var avatars: List<Pair<UUID, String?>> = emptyList()
)
