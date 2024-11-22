package com.example.hangoutz.ui.screens.events

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hangoutz.domain.repository.EventRepository
import com.example.hangoutz.domain.repository.InviteRepository
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
): ViewModel() {
    private val _uiState = MutableStateFlow(EventScreenState())
    val uiState: StateFlow<EventScreenState> = _uiState
    init {
        getEvents()
    }
    fun getEvents() {
        viewModelScope.launch {
            val response = eventRepository.getEventsWithAvatar()
            if(response.isSuccessful && !response.body().isNullOrEmpty()) {
                Log.d("event","---------------------")
                response.body()?.let {
                   _uiState.value = _uiState.value.copy(
                        events = it
                    )
                    Log.d("Nije null", "Nije nul---------------")
                }
                Log.d("Velicina",uiState.value.events.size.toString())
            }
            else {
                Log.d("Events","Loading events errors")
            }
        }
    }
    suspend fun getCountOfAcceptedInvitesForEvent(id:UUID): Int {
            val response = inviteRepository.getCountOfAcceptedInvitesByEvent(id = id)
            if (response.isSuccessful && response.body() != null){
                var count = response.body()
                return count?.first()?.count ?: 0
            }

        return 0
    }
}