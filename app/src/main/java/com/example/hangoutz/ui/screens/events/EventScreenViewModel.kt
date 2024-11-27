package com.example.hangoutz.ui.screens.events

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hangoutz.data.local.SharedPreferencesManager
import com.example.hangoutz.data.models.toEventCardDPO
import com.example.hangoutz.domain.repository.EventRepository
import com.example.hangoutz.domain.repository.InviteRepository
import com.example.hangoutz.ui.theme.GreenDark
import com.example.hangoutz.ui.theme.Orange
import com.example.hangoutz.ui.theme.PurpleDark
import com.example.hangoutz.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class EventScreenViewModel @Inject constructor(
    private val eventRepository: EventRepository,
    private val inviteRepository: InviteRepository,
    @ApplicationContext private val context: Context
) : ViewModel() {
    private val _uiState = MutableStateFlow(EventScreenState())
    val uiState: StateFlow<EventScreenState> = _uiState

    @SuppressLint("SuspiciousIndentation")
    fun getEvents(page: String = EventsFilterOptions.GOING.name) {
        viewModelScope.launch {
            if (page == EventsFilterOptions.MINE.name) {
                getMineEvents()
            } else if (page == EventsFilterOptions.GOING.name) {
                getGoingEvents()
            }
            else if(page == EventsFilterOptions.INVITED.name){
                getInvitedEvents()
            }
        }
    }

    private suspend fun getCountOfAcceptedInvitesForEvent(id: UUID) {
        val response = inviteRepository.getCountOfAcceptedInvitesByEvent(id = id)
        if (response.isSuccessful) {
            val count = response.body()
            _uiState.value = _uiState.value.copy(
                counts = _uiState.value.counts + Pair(
                    id,
                    count?.first()?.count ?: 0
                )
            )
        } else {
            Log.d("Error", response.message())
        }
    }

    fun getCardColor(cardIndex: Int): Color {
        if (cardIndex % 3 == 0) {
            return PurpleDark
        } else if (cardIndex % 3 == 1) {
            return GreenDark
        }
        return Orange
    }

    private suspend fun getMineEvents() {
        _uiState.value = _uiState.value.copy(
            eventsMine = emptyList(),
            counts = emptyList(),
            isLoading = true
        )
        val response =
            SharedPreferencesManager.getUserId(context)
                ?.let { eventRepository.getEventsWithAvatar(it) }
        if (response != null) {
            if (response.isSuccessful && !response.body().isNullOrEmpty()) {
                response.body()?.let {
                    _uiState.value = _uiState.value.copy(
                        eventsMine = it
                    )
                    it.forEach { event ->
                        getCountOfAcceptedInvitesForEvent(id = event.id)
                    }
                }

            } else {
                Log.d("Events", Constants.GET_EVENTS_ERRORS)
            }
        }
        _uiState.value = _uiState.value.copy(
            isLoading = false
        )
    }

    private suspend fun getGoingEvents() {
        _uiState.value = _uiState.value.copy(
            eventsGoing = emptyList(),
            avatars = emptyList(),
            isLoading = true
        )
        val response =
            SharedPreferencesManager.getUserId(context)
                ?.let {
                    eventRepository.getEventsFromInvites(
                        eventStatus = "accepted",
                        userID = it
                    )
                }
        Log.d("Events", response?.body().toString())
        if (response != null) {
            if (response.isSuccessful && !response.body().isNullOrEmpty()) {
                response.body()?.let {
                    _uiState.value = _uiState.value.copy(
                        eventsGoing = _uiState.value.eventsGoing + it.map { event ->
                            event.toEventCardDPO()
                        }
                    )
                    it.forEach { event ->
                        // getCountOfAcceptedInvitesForEvent(id = event.)
                    }
                }

            } else {
                Log.d("Events", Constants.GET_EVENTS_ERRORS)
            }
        }
        _uiState.value = _uiState.value.copy(
            isLoading = false
        )
    }
    private suspend fun getInvitedEvents() {
        _uiState.value = _uiState.value.copy(
            eventsInveted = emptyList(),
            avatars = emptyList(),
            isLoading = true
        )
        val response =
            SharedPreferencesManager.getUserId(context)
                ?.let {
                    eventRepository.getEventsFromInvites(
                        eventStatus = "invited",
                        userID = it
                    )
                }
        Log.d("Events", response?.body().toString())
        if (response != null) {
            if (response.isSuccessful && !response.body().isNullOrEmpty()) {
                response.body()?.let {
                    _uiState.value = _uiState.value.copy(
                        eventsInveted = _uiState.value.eventsInveted + it.map { event ->
                                event.toEventCardDPO()
                            }
                    )
                    it.forEach { event ->
                        // getCountOfAcceptedInvitesForEvent(id = event.)
                    }
                }

            } else {
                Log.d("Events", Constants.GET_EVENTS_ERRORS)
            }
        }
        _uiState.value = _uiState.value.copy(
            isLoading = false
        )
    }
}