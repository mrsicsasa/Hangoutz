package com.example.hangoutz.ui.screens.eventDetails

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.hangoutz.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

data class EventDetailsData(
    var name: String = "",
    var email: String = "",
    var isReadOnly: Boolean = true,
    val avatar: String? = null
)

@HiltViewModel
class EventDetailsViewModel @Inject constructor(
    private val userRepository: UserRepository,
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val _uiState = MutableStateFlow(EventDetailsData())
    val uiState: StateFlow<EventDetailsData> = _uiState



    fun getEventId(){


    }
}