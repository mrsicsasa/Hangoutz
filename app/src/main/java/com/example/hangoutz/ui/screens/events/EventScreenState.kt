package com.example.hangoutz.ui.screens.events

import androidx.compose.foundation.pager.PagerState
import com.example.hangoutz.data.models.EventCardDPO
import com.example.hangoutz.utils.Constants
import java.util.UUID

data class EventScreenState(
    var eventsGoing: List<EventCardDPO> = emptyList(),
    var eventsInvited: List<EventCardDPO> = emptyList(),
    var eventsMine: List<EventCardDPO> = emptyList(),
    var counts: List<Pair<UUID, Int>> = emptyList(),
    var isLoading: Boolean = true,
    var avatars: List<Pair<UUID, String?>> = emptyList(),
    var pagerState: PagerState = object : PagerState() {
        override val pageCount: Int = Constants.FILTER_BAR_ITEM_COUNT
    },
    var countOfInvites: Int = 0
)
