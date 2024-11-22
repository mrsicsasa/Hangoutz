package com.example.hangoutz.ui.screens.myevents

import android.annotation.SuppressLint
import android.util.Log
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.hangoutz.data.models.EventCardDPO
import com.example.hangoutz.ui.theme.GreenDark
import com.example.hangoutz.ui.theme.Orange
import com.example.hangoutz.ui.theme.PurpleDark
import com.example.hangoutz.utils.toDate
import com.example.hangoutz.utils.toEventDateDPO

@SuppressLint("StateFlowValueCalledInComposition", "NewApi")
@Composable
fun MyEventsScreen(navController: NavController, viewModel: EventScreenViewModel = hiltViewModel()) {
    val data = viewModel.uiState.collectAsState()
    Box(modifier = Modifier.padding(10.dp)) {
        Column(
            modifier = Modifier
                .padding(top = 90.dp)
                .padding(horizontal = 5.dp)
                .background(Color.Transparent)
                .fillMaxSize()
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 20.dp)
            ) {
                items(data.value.events) { event ->
                    EventCard(
                        backgroundColor = getCardColor(data.value.events, event),
                        imageUrl = event.users.avatar ?: "avatar3.jpg",
                        title = event.title,
                        place = event.place ?: "",
                        date = event.date.toDate().toEventDateDPO(),
                        isInvited = true
                    )
                    Log.d("Eventsradi", viewModel.uiState.value.events.size.toString())
                    Spacer(modifier = Modifier.height(20.dp))
                }
            }
        }
        FloatingActionButton(
            onClick = {},
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(20.dp)
                .clip(CircleShape)
        ) {
            Icon(Icons.Filled.Add, "floating action button", modifier = Modifier.size(40.dp))
        }
    }
}
private fun getCardColor(list: List<EventCardDPO>, item: EventCardDPO): Color {
    val index = list.indexOf(item)
    if(index % 3 == 0) {
        return PurpleDark
    }
    else if (index % 3 == 1) {
        return GreenDark
    }
    return Orange
}
