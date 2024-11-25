package com.example.hangoutz.ui.screens.events

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hangoutz.domain.repository.EventRepository
import com.example.hangoutz.domain.repository.InviteRepository
import com.example.hangoutz.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class EventScreenViewModel @Inject constructor(
    private val eventRepository: EventRepository,
    private val inviteRepository: InviteRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(EventScreenState())
    val uiState: StateFlow<EventScreenState> = _uiState

    init {
        getEvents()
    }

    private fun getEvents() {
        viewModelScope.launch {
            val response = eventRepository.getEventsWithAvatar()
            if (response.isSuccessful && !response.body().isNullOrEmpty()) {
                response.body()?.let {
                    _uiState.value = _uiState.value.copy(
                        events = it
                    )
                    it.forEach { event ->
                        getCountOfAcceptedInvitesForEvent(
                            id = event.id
                        )
                    }
                }
            } else {
                Log.d("Events", Constants.GET_EVENTS_ERRORS)
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
}