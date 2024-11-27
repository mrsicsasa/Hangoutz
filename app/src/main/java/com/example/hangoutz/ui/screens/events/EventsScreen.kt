package com.example.hangoutz.ui.screens.events

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
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
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.hangoutz.R
import com.example.hangoutz.data.models.EventCardDPO
import com.example.hangoutz.ui.components.customIndicator.CustomTab
import com.example.hangoutz.utils.Constants
import com.example.hangoutz.utils.Dimensions
import com.example.hangoutz.utils.toDate
import com.example.hangoutz.utils.toEventDateDPO
import java.util.UUID

@SuppressLint("StateFlowValueCalledInComposition", "NewApi")
@Composable
fun MyEventsScreen(viewModel: EventScreenViewModel = hiltViewModel()) {
    val data = viewModel.uiState.collectAsState()
    val pagerState = rememberPagerState(pageCount = { 3 })
    val scope = rememberCoroutineScope()
    val (selected, setSelected) = remember {
        mutableStateOf(0)
    }
    Column(
        modifier = Modifier
            .padding(top = 10.dp)
            .padding(horizontal = Dimensions.CONTENT_HORIZONTAL_PADDING)
    ) {
        CustomTab(
            items = listOf("GOING", "INVITED","MINE"),
            selectedItemIndex = pagerState.currentPage,
            onClick = setSelected,
            scope = scope,
            pagerState = pagerState
        )
        HorizontalPager(
            state = pagerState,
            pageSize = PageSize.Fill,
            beyondViewportPageCount = PagerDefaults.BeyondViewportPageCount,
            modifier = Modifier
                .testTag("Pager")

        ) { page ->
            when (page) {
                0 -> EventsList(data, viewModel,page = EventsFilterOptions.GOING.name,data.value.eventsGoing)
                1 -> EventsList(data, viewModel, page = EventsFilterOptions.INVITED.name,data.value.eventsInveted)
                2 -> EventsList(data, viewModel, page = EventsFilterOptions.MINE.name,data.value.eventsMine)
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun EventsList(
    data: State<EventScreenState>,
    viewModel: EventScreenViewModel,
    page: String,
    events:List<EventCardDPO>
) {
    Box(contentAlignment = Alignment.Center) {
        if (data.value.isLoading) {
            CircularProgressIndicator()
        }
        else if(events.isEmpty() && !data.value.isLoading) {
            Text("No events available", color = Color.LightGray)
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
                    .padding(bottom = Dimensions.LAZY_COLUMN_BOTTOM_PADDING)
            ) {
                items(events) { event ->
                    val countOfPeoplePair: Pair<UUID, Int>? =
                        data.value.counts.find { it.first == event.id }
                    EventCard(
                        backgroundColor = viewModel.getCardColor(events.indexOf(event)),
                        imageUrl = event.users.avatar
                            ?: stringResource(R.string.default_user_image),
                        title = event.title,
                        place = event.place ?: "",
                        date = event.date.toDate().toEventDateDPO(),
                        countOfPeople = (countOfPeoplePair?.second ?: 0),
                        modifier = Modifier.semantics {
                            contentDescription = Constants.EVENT_CARD
                        }
                    )
                    Spacer(modifier = Modifier.height(Dimensions.SPACE_HEIGHT_BETWEEN_CARDS))
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
    LaunchedEffect(key1 = true) {
        Log.d("Filter","-------------${page}")
        viewModel.getEvents(page = page)
    }
}