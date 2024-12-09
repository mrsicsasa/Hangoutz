package com.example.hangoutz.ui.screens.events

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.hangoutz.data.local.SharedPreferencesManager
import com.example.hangoutz.data.models.EventCardDPO
import com.example.hangoutz.data.models.UpdateEventStatusDTO
import com.example.hangoutz.data.models.toEventCardDPO
import com.example.hangoutz.domain.repository.EventRepository
import com.example.hangoutz.domain.repository.InviteRepository
import com.example.hangoutz.domain.repository.UserRepository
import com.example.hangoutz.ui.theme.GreenDark
import com.example.hangoutz.ui.theme.Orange
import com.example.hangoutz.ui.theme.PurpleDark
import com.example.hangoutz.utils.Constants
import com.example.hangoutz.utils.notifications.NotificationWorker
import com.example.hangoutz.utils.toMilliseconds
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.UUID
import java.util.concurrent.TimeUnit
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


    @RequiresApi(Build.VERSION_CODES.O)
    fun getEvents(page: String = EventsFilterOptions.GOING.name) {
        viewModelScope.launch {
            getCountOfInvites()
            when (page) {
                EventsFilterOptions.CREATED.name -> {
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
    }

    private suspend fun getCountOfAcceptedInvitesForEvent(id: UUID) {
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
            Log.d("Error", response.message())
        }
    }

    private suspend fun getAvatars(id: UUID) {
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

    @RequiresApi(Build.VERSION_CODES.O)
    private suspend fun getMineEvents() {
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
                        scheduleNotificationIfNeeded(eventId = event.id, eventName = event.title, eventTime = event.date.toMilliseconds())
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

    private suspend fun getEventsFromInvites(page: String) {
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
                Log.d("Events", Constants.GET_EVENTS_ERRORS)
            }
        }
        _uiState.value = _uiState.value.copy(
            isLoading = false
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun updateInvitesStatus(status: String, eventId: UUID) {
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
    }

    private fun getCountOfInvites() {
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
    }
    fun isCurrentUserOwner(event: EventCardDPO): Boolean {
        val userId = SharedPreferencesManager.getUserId(context)
        return userId == event.owner.toString()
    }

    private fun scheduleNotificationIfNeeded(eventId: UUID, eventName: String, eventTime: Long) {
        val workManager = WorkManager.getInstance(context)
        val delay = eventTime - System.currentTimeMillis() - TimeUnit.HOURS.toMillis(3)
        Log.d("Notifikacija", "$eventName - $delay")

        if (delay > 0) {
            workManager.getWorkInfosByTag(eventId.toString()).get().let { workInfos ->
                val hasActiveWork = workInfos.any { workInfo ->
                    workInfo.state == androidx.work.WorkInfo.State.ENQUEUED ||
                            workInfo.state == androidx.work.WorkInfo.State.RUNNING
                }

                if (!hasActiveWork) {
                    val data = Data.Builder()
                        .putString("eventId", eventId.toString())
                        .putString("eventName", eventName)
                        .build()

                    val workRequest = OneTimeWorkRequestBuilder<NotificationWorker>()
                        .setInitialDelay(delay, TimeUnit.MICROSECONDS)
                        .setInputData(data)
                        .addTag(eventId.toString())
                        .build()

                    workManager.enqueue(workRequest)
                }
            }
        }
    }

}