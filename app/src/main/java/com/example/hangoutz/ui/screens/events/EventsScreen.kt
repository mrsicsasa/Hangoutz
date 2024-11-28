package com.example.hangoutz.ui.screens.events

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.hangoutz.R
import com.example.hangoutz.data.models.EventCardDPO
import com.example.hangoutz.utils.Constants
import com.example.hangoutz.utils.Dimensions
import com.example.hangoutz.utils.toDate
import com.example.hangoutz.utils.toEventDateDPO
import java.util.UUID

@SuppressLint("StateFlowValueCalledInComposition", "NewApi")
@Composable
fun MyEventsScreen(viewModel: EventScreenViewModel = hiltViewModel()) {
    val data = viewModel.uiState.collectAsState()
    val scope = rememberCoroutineScope()
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .padding(
                    top = Dimensions.FILTER_BAR_TOP_PADDING,
                    start = Dimensions.CONTENT_START_PADDING,
                    end = Dimensions.CONTENT_END_PADDING
                )
        ) {
            FilterBar(
                items = listOf(
                    EventsFilterOptions.GOING.name.uppercase(),
                    EventsFilterOptions.INVITED.name.uppercase(),
                    EventsFilterOptions.MINE.name.uppercase()
                ),
                selectedItemIndex = data.value.pagerState.currentPage,
                scope = scope,
                pagerState = data.value.pagerState
            )
            HorizontalPager(
                state = data.value.pagerState,
                pageSize = PageSize.Fill,
                beyondViewportPageCount = PagerDefaults.BeyondViewportPageCount,
                modifier = Modifier
                    .semantics {
                        contentDescription = Constants.HORIZONTAL_PAGER
                    }

            ) { page ->
                when (page) {
                    0 -> EventsList(
                        page = EventsFilterOptions.GOING.name,
                        events = data.value.eventsGoing,
                        isLoading = data.value.isLoading,
                        counts = data.value.counts,
                        avatars = data.value.avatars,
                        getBackgroundColor = { viewModel.getCardColor(it) },
                        getEvents = { viewModel.getEvents(it) }
                    )

                    1 -> EventsList(
                        page = EventsFilterOptions.INVITED.name,
                        events = data.value.eventsInveted,
                        isLoading = data.value.isLoading,
                        counts = data.value.counts,
                        avatars = data.value.avatars,
                        getBackgroundColor = { viewModel.getCardColor(it) },
                        getEvents = { viewModel.getEvents(it) }
                    )

                    2 -> EventsList(
                        page = EventsFilterOptions.MINE.name,
                        events = data.value.eventsMine,
                        isLoading = data.value.isLoading,
                        counts = data.value.counts,
                        avatars = data.value.avatars,
                        getBackgroundColor = { viewModel.getCardColor(it) },
                        getEvents = { viewModel.getEvents(it) }
                    )
                }
            }
        }
        FloatingActionButton(
            onClick = {},
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(Dimensions.FLOATING_BUTTON_PADDING)
                .clip(CircleShape)
                .semantics {
                    contentDescription = Constants.CREATE_EVENT_BUTTON
                }
        ) {
            Icon(
                Icons.Filled.Add,
                stringResource(R.string.floating_action_button_icon_description),
                modifier = Modifier.size(Dimensions.FLOATING_ICON_SIZE)
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun EventsList(
    page: String,
    events: List<EventCardDPO>,
    isLoading: Boolean,
    counts: List<Pair<UUID, Int>>,
    avatars: List<Pair<UUID, String?>>,
    getBackgroundColor: (index: Int) -> Color,
    getEvents: (page: String) -> Unit
) {
    Box(contentAlignment = Alignment.Center) {
        if (isLoading) {
            CircularProgressIndicator()
        } else if (events.isEmpty()) {
            Text(stringResource(R.string.no_events_available), color = Color.LightGray)
        }
        Column(
            modifier = Modifier
                .padding(top = Dimensions.CONTENT_TOP_PADDING)
                .background(Color.Transparent)
                .fillMaxSize()
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                items(events) { event ->
                    val countOfPeoplePair: Pair<UUID, Int>? =
                        counts.find { it.first == event.id }
                    val userAvatar: Pair<UUID, String?>? =
                        avatars.find { it.first == event.owner }
                    (if (page == EventsFilterOptions.MINE.name) {
                        event.users.avatar
                            ?: stringResource(R.string.default_user_image)
                    } else {
                        userAvatar?.second ?: stringResource(R.string.default_user_image)
                    }).let {
                        EventCard(
                            backgroundColor = getBackgroundColor(events.indexOf(event)),
                            imageUrl = it,
                            title = event.title,
                            place = event.place ?: "",
                            date = event.date.toDate().toEventDateDPO(),
                            countOfPeople = (countOfPeoplePair?.second ?: 0),
                            modifier = Modifier.semantics {
                                contentDescription = Constants.EVENT_CARD
                            },
                            isInvited = page == EventsFilterOptions.INVITED.name
                        )
                    }
                    Spacer(modifier = Modifier.height(Dimensions.SPACE_HEIGHT_BETWEEN_CARDS))
                }
            }
        }
    }
    LaunchedEffect(key1 = true) {
        getEvents(page)
    }
}