package com.example.hangoutz.ui.screens.events

import android.annotation.SuppressLint
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.hangoutz.R
import com.example.hangoutz.data.models.EventCardDPO
import com.example.hangoutz.ui.theme.GreenDark
import com.example.hangoutz.ui.theme.Orange
import com.example.hangoutz.ui.theme.PurpleDark
import com.example.hangoutz.utils.Constants
import com.example.hangoutz.utils.Dimensions
import com.example.hangoutz.utils.toDate
import com.example.hangoutz.utils.toEventDateDPO
import java.util.UUID

@SuppressLint("StateFlowValueCalledInComposition", "NewApi")
@Composable
fun MyEventsScreen(viewModel: EventScreenViewModel = hiltViewModel()) {
    val data = viewModel.uiState.collectAsState()
    Box(modifier = Modifier.padding(Dimensions.SCREEN_PADDING)) {
        Column(
            modifier = Modifier
                .padding(top = Dimensions.CONTENT_TOP_PADDING)
                .padding(horizontal = Dimensions.CONTENT_HORIZONTAL_PADDING)
                .background(Color.Transparent)
                .fillMaxSize()
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = Dimensions.LAZY_COLUMN_BOTTOM_PADDING)
            ) {
                items(data.value.events) { event ->
                    val countOfPeoplePair: Pair<UUID, Int>? =
                        data.value.counts.find { it.first == event.id }
                    EventCard(
                        backgroundColor = getCardColor(data.value.events, event),
                        imageUrl = event.users.avatar
                            ?: stringResource(R.string.default_user_image),
                        title = event.title,
                        place = event.place ?: "",
                        date = event.date.toDate().toEventDateDPO(),
                        countOfPeople = (countOfPeoplePair?.second ?: 0)
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
                .testTag(Constants.CREATE_EVENT_BUTTON)
        ) {
            Icon(
                Icons.Filled.Add,
                stringResource(R.string.floating_action_button_icon_description),
                modifier = Modifier.size(Dimensions.FLOATING_ICON_SIZE)
            )
        }
    }
}

private fun getCardColor(list: List<EventCardDPO>, item: EventCardDPO): Color {
    val index = list.indexOf(item)
    if (index % 3 == 0) {
        return PurpleDark
    } else if (index % 3 == 1) {
        return GreenDark
    }
    return Orange
}
