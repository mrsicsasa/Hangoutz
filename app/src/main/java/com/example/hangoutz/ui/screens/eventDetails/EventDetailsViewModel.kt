package com.example.hangoutz.ui.screens.eventDetails

import android.content.Context
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.hangoutz.data.local.SharedPreferencesManager
import com.example.hangoutz.data.models.UpdateEventStatusDTO
import com.example.hangoutz.data.models.User
import com.example.hangoutz.domain.repository.EventRepository
import com.example.hangoutz.domain.repository.InviteRepository
import com.example.hangoutz.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.UUID
import javax.inject.Inject

data class EventDetailsData(
    var eventId: UUID? = null,
    var title: String = "",
    var description: String = "",
    var city: String = "",
    var street: String = "",
    var place: String = "",
    var date: String = "",
    var time: String = "",
    var participants: List<User> = emptyList()
)

@HiltViewModel
class EventDetailsViewModel @Inject constructor(
    private val inviteRepository: InviteRepository,
    private val userRepository: UserRepository,
    private val eventRepository: EventRepository,
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val _uiState = MutableStateFlow(EventDetailsData())
    val uiState: StateFlow<EventDetailsData> = _uiState

    fun getEventIdFromController(navController: NavController) {
        val eventId = navController.currentBackStackEntry?.arguments?.getString("eventId")?.let {
            UUID.fromString(it)
        }
        _uiState.value = _uiState.value.copy(eventId = eventId)
    }

    fun formatDateTime(dateTimeString: String): Pair<String, String> {

        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
        val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
        val timeFormat = SimpleDateFormat("HH.mm", Locale.getDefault())

        val date = inputFormat.parse(dateTimeString)
        val formattedDate = dateFormat.format(date)
        val formattedTime = timeFormat.format(date)

        return Pair(formattedDate, formattedTime)
    }

    fun onLeave(onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        viewModelScope.launch {
            try {
                val userId = SharedPreferencesManager.getUserId(context) // Retrieve current user ID
                val eventId = _uiState.value.eventId

                if (userId != null && eventId != null) {
                    val response = inviteRepository.updateInviteStatus(
                        userId,
                        eventId,
                        body = UpdateEventStatusDTO(event_status = "declined")
                    )

                    if (response.isSuccessful) {
                        getParticipants()

                        Log.d("EventDetailsViewModel", "Successfully left event: $eventId")
                        onSuccess()
                    } else {
                        Log.e("EventDetailsViewModel", "Failed to leave event: ${response.code()}")
                        onFailure("Failed to leave the event. Please try again.")
                    }
                } else {
                    Log.e("EventDetailsViewModel", "User ID or Event ID is null")
                    onFailure("Unable to identify the user or event.")
                }
            } catch (e: Exception) {
                Log.e("EventDetailsViewModel", "Error leaving event: ${e.message}")
                onFailure("An unexpected error occurred. Please try again.")
            }
        }
    }
        fun getParticipants() {
            viewModelScope.launch {

                val eventResponse = _uiState.value.eventId?.let { eventRepository.getEvent(it) }
                if (eventResponse?.isSuccessful == true && eventResponse.body() != null) {
                    val event = eventResponse.body()?.first()
                    event?.let {
                        _uiState.value = _uiState.value.copy(
                            title = it.title,
                            description = it.description ?: "",
                            city = it.city ?: "",
                            street = it.street ?: "",
                            place = it.place ?: "" ,
                            date = it.date,
                        )

                        val dateTemp = _uiState.value.date ?: ""
                        val (formattedDate, formattedTime) = formatDateTime(dateTemp)

                        _uiState.value =
                            _uiState.value.copy(date = formattedDate, time = formattedTime)

                        Log.d(
                            "EventDetailsViewModel",
                            "Fetching event details for eventId: ${_uiState.value.eventId}"
                        )

                        val invitesResponse =
                            _uiState.value.eventId?.let { inviteRepository.getInvitesByEventId(it) }
                        if (invitesResponse?.isSuccessful == true && invitesResponse.body() != null) {
                            val acceptedUserIds: List<UUID> =
                                invitesResponse.body()!!.map { invite -> invite.userId }
                            Log.d("EventDetailsViewModel", "Accepted user IDs: $acceptedUserIds")

                            val usersResponse = userRepository.getAllUsers()
                            if (usersResponse?.isSuccessful == true && usersResponse.body() != null) {
                                val allUsers: List<User> = usersResponse.body()!!

                                val acceptedUsers: List<User> = allUsers.filter { user ->
                                    user.id in acceptedUserIds
                                }

                                _uiState.value = _uiState.value.copy(participants = acceptedUsers)
                            }
                        }
                    }
                }
            }
        }
    }


