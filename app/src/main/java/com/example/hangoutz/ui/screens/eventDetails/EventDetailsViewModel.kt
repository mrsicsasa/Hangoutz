package com.example.hangoutz.ui.screens.eventDetails

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.hangoutz.data.models.User
import com.example.hangoutz.domain.repository.InviteRepository
import com.example.hangoutz.domain.repository.UserRepository
import com.example.hangoutz.utils.Constants.DEFAULT_USER_PHOTO
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject


data class Invite(
    val userId: UUID,
    val eventStatus: String? = null, // Make sure this is nullable
    val eventId: UUID
)

data class EventDetailsData(
    var eventId: UUID? = null,
    var participants: List<User> = emptyList()
)

@HiltViewModel
class EventDetailsViewModel @Inject constructor(
    private val inviteRepository: InviteRepository,
    private val userRepository: UserRepository,
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

fun leaveEvent(onLeave: () -> Unit) {

    onLeave()
}

fun getParticipants() {
    viewModelScope.launch {
        _uiState.value.eventId?.let { getEventParticipants(eventId = it) }
    }

}

suspend fun getEventParticipants(eventId: UUID) {
    try {
        val invitesResponse = inviteRepository.getInvitesByEventId(eventId)


        if (invitesResponse?.isSuccessful == true && !invitesResponse.body().isNullOrEmpty()) {
            val invites = invitesResponse.body() ?: emptyList()
            val acceptedUserIds = invites.filter { it.eventStatus == "accepted" }.map { it.userId }

            val usersResponse = userRepository.getAllUsers()

            if (usersResponse?.isSuccessful == true && !usersResponse.body().isNullOrEmpty()) {
                val users = usersResponse.body() ?: emptyList()

                val acceptedUsers = users.filter { user ->
                    user.id in acceptedUserIds
                }
                invites.forEach { participant ->
                    Log.d("invinites", "${participant.eventId} -" + participant.eventStatus)
                }
                acceptedUsers.forEach { participant ->
                   Log.d("ACCEPTEDUSERS", participant.name)
                }
                _uiState.value = _uiState.value.copy(
                    eventId = eventId,
                    participants = acceptedUsers
                )
                Log.d("EventDetailsViewModel", "Fetched participants: ${acceptedUsers.size}")
            }

        }
    } catch (e: Exception) {
        Log.e("Error", "Error has occurred while getting events")
    }

}

}



