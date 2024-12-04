package com.example.hangoutz.ui.screens.events

import android.content.Context
import android.util.Log
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hangoutz.data.local.SharedPreferencesManager
import com.example.hangoutz.data.models.UpdateEventStatusDTO
import com.example.hangoutz.data.models.toEventCardDPO
import com.example.hangoutz.domain.repository.EventRepository
import com.example.hangoutz.domain.repository.InviteRepository
import com.example.hangoutz.domain.repository.UserRepository
import com.example.hangoutz.ui.theme.GreenDark
import com.example.hangoutz.ui.theme.Orange
import com.example.hangoutz.ui.theme.PurpleDark
import com.example.hangoutz.utils.Constants
import com.example.hangoutz.utils.connectivityObserver.AndroidConnectivityObserver
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.net.UnknownHostException
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class EventScreenViewModel @Inject constructor(
    private val eventRepository: EventRepository,
    private val inviteRepository: InviteRepository,
    private val userRepository: UserRepository,
    @ApplicationContext private val context: Context
) : ViewModel() {
    private val _uiState = MutableStateFlow(EventScreenState())
    val uiState: StateFlow<EventScreenState> = _uiState
    val isConnected = AndroidConnectivityObserver(context)
        .isConnected
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            false
        )

    fun getEvents(page: String = EventsFilterOptions.GOING.name) {
        if (!isConnected.value) {
            // Skip network operations if no internet
            Log.d("ERROR", "No internet connection, skipping fetching events")
            return
        }

        try {
            viewModelScope.launch {
                getCountOfInvites()
                when (page) {
                    EventsFilterOptions.MINE.name -> {
                        getMineEvents()
                    }

                    EventsFilterOptions.GOING.name -> {
                        getEventsFromInvites(page = page)
                    }

                    EventsFilterOptions.INVITED.name -> {
                        getEventsFromInvites(page = page)
                    }
                }
            }
        } catch (e: Exception) {
            Log.d("ERROR", e.message.toString())
        }
    }

    private suspend fun getCountOfAcceptedInvitesForEvent(id: UUID) {
        if (!isConnected.value) {
            // Handle the case when there is no internet connection
            Log.d("ERROR", "No internet connection, skipping network request for invites count")
            return
        }

        try {
            val response = inviteRepository.getCountOfAcceptedInvitesByEvent(id = id)
            if (response.isSuccessful) {
                val count = response.body()
                if (_uiState.value.counts.find { it.first == id } == null) {
                    _uiState.value = _uiState.value.copy(
                        counts = _uiState.value.counts + Pair(
                            id,
                            (count?.first()?.count?.plus(1)) ?: 1
                        )
                    )
                } else {
                    _uiState.value = _uiState.value.copy(
                        counts = _uiState.value.counts.map { pair ->
                            if (pair.first == id) {
                                pair.copy(second = count?.first()?.count?.plus(1) ?: 1)
                            } else {
                                pair
                            }
                        }
                    )
                }
            } else {
                Log.d("ERROR", "Failed to get invites count: ${response.message()}")
            }
        } catch (e: UnknownHostException) {
            // Handle DNS resolution failure
            Log.d("ERROR", "Unknown host exception: ${e.message}")
        } catch (e: Exception) {
            Log.d("ERROR", "Error while fetching invites count: ${e.message}")
        }
    }

    private suspend fun getAvatars(id: UUID) {
        if (!isConnected.value) {
            // Handle the case when there is no internet connection
            Log.d("ERROR", "No internet connection, skipping avatar fetch")
            return
        }

        try {
            val response = userRepository.getUserAvatar(id.toString())
            if (response.isSuccessful) {
                response.body()?.let {
                    _uiState.value = _uiState.value.copy(
                        avatars = _uiState.value.avatars + Pair(
                            id,
                            it.first().avatar
                        )
                    )
                }
            } else {
                Log.d("ERROR", "Failed to fetch avatar: ${response.message()}")
            }
        } catch (e: UnknownHostException) {
            Log.d("ERROR", "Unknown host exception: ${e.message}")
        } catch (e: Exception) {
            Log.d("ERROR", "Error while fetching avatar: ${e.message}")
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
        if (!isConnected.value) {
            // Skip network operations if no internet
            Log.d("ERROR", "No internet connection, skipping mine events fetch")
            return
        }

        try {
            _uiState.value = _uiState.value.copy(
                eventsMine = emptyList(),
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
                    Log.d("ERROR", Constants.GET_EVENTS_ERRORS)
                }
            }
            _uiState.value = _uiState.value.copy(
                isLoading = false
            )
        } catch (e: Exception) {
            Log.d("ERROR", e.message.toString())
        }
    }

    private suspend fun getEventsFromInvites(page: String) {
        if (!isConnected.value) {
            // Skip network operations if no internet
            Log.d("ERROR", "No internet connection, skipping events from invites fetch")
            return
        }

        try {
            if (page == EventsFilterOptions.GOING.name) {
                _uiState.value = _uiState.value.copy(
                    eventsGoing = emptyList(),
                    isLoading = true
                )
            } else {
                _uiState.value = _uiState.value.copy(
                    eventsInvited = emptyList(),
                    isLoading = true
                )
            }
            val response =
                SharedPreferencesManager.getUserId(context)
                    ?.let {
                        eventRepository.getEventsFromInvites(
                            eventStatus = if (page == EventsFilterOptions.GOING.name) Constants.INVITE_STATUS_ACCEPTED else Constants.INVITE_STATUS_INVITED,
                            userID = it
                        )
                    }
            if (response != null) {
                if (response.isSuccessful && !response.body().isNullOrEmpty()) {
                    response.body()?.let {
                        if (page == EventsFilterOptions.GOING.name) {
                            _uiState.value = _uiState.value.copy(
                                eventsGoing = _uiState.value.eventsGoing + it.map { event ->
                                    event.toEventCardDPO()
                                },
                            )
                        } else {
                            _uiState.value = _uiState.value.copy(
                                eventsInvited = _uiState.value.eventsInvited + it.map { event ->
                                    event.toEventCardDPO()
                                },
                            )
                        }
                        it.forEach { event ->
                            getAvatars(event.events.owner)
                            if (page == EventsFilterOptions.GOING.name) {
                                getCountOfAcceptedInvitesForEvent(event.events.id)
                            }
                        }
                    }
                } else {
                    Log.d("ERROR", Constants.GET_EVENTS_ERRORS)
                }
            }
            _uiState.value = _uiState.value.copy(
                isLoading = false
            )
        } catch (e: Exception) {
            Log.d("ERROR", e.message.toString())
        }
    }

    fun updateInvitesStatus(status: String, eventId: UUID) {
        try {
            viewModelScope.launch {
                val response = SharedPreferencesManager.getUserId(context)
                    ?.let {
                        inviteRepository.updateInviteStatus(
                            eventId = eventId,
                            userId = it,
                            body = UpdateEventStatusDTO(event_status = status)
                        )
                    }
                if (response?.isSuccessful == true) {
                    getEvents(EventsFilterOptions.INVITED.name)
                } else {
                    Log.d("ERROR", response?.code().toString())
                }
            }
        } catch (e: Exception) {
            Log.d("ERROR", e.message.toString())
        }
    }

    private fun getCountOfInvites() {
        try {
            viewModelScope.launch {
                val response =
                    SharedPreferencesManager.getUserId(context)
                        ?.let {
                            eventRepository.getEventsFromInvites(
                                eventStatus = "invited",
                                userID = it
                            )
                        }
                if (response != null) {
                    if (response.isSuccessful && response.body() != null) {
                        _uiState.value = response.body()?.size?.let {
                            _uiState.value.copy(
                                countOfInvites = it
                            )
                        }!!
                    }
                }
            }
        } catch (e: Exception) {
            Log.d("ERROR", e.message.toString())
        }
    }
}