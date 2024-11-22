package com.example.hangoutz.ui.screens.myevents

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hangoutz.domain.repository.EventRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EventScreenViewModel @Inject constructor(
    private val eventRepository: EventRepository
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
}