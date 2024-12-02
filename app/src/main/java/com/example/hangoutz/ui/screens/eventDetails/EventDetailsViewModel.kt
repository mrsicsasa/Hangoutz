package com.example.hangoutz.ui.screens.eventDetails

import android.content.ClipDescription
import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.hangoutz.data.models.Invite
import com.example.hangoutz.data.models.User
import com.example.hangoutz.domain.repository.EventRepository
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


data class EventDetailsData(
    var eventId: UUID? = null,
    var title: String? = "",
    var description: String? = "",
    var city: String? = "",
    var street: String? = "",
    var place: String? = "",
    var date: String? = "",
    var time: String? = "",
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

    fun leaveEvent(onLeave: () -> Unit) {

        onLeave()
    }

    fun getParticipants() {
        viewModelScope.launch {

            val eventResponse = _uiState.value.eventId?.let { eventRepository.getEvent(it) }
            if (eventResponse?.isSuccessful == true && eventResponse.body() != null) {
                val event = eventResponse.body()?.first()
                event?.let {
                    _uiState.value = _uiState.value.copy(
                        city = it.city,
                        street = it.street,
                        place = it.place,
                        date = it.date,


                        )


                    Log.d(
                        "EventDetailsViewModel",
                        "Fetching event details for eventId: ${_uiState.value.eventId}"
                    )


                    val invitesResponse =
                        _uiState.value.eventId?.let { inviteRepository.getInvitesByEventId(it) }
                    if (invitesResponse?.isSuccessful == true && invitesResponse.body() != null) {
                        val acceptedUserIds: List<UUID> =
                            invitesResponse.body()!!.map { invite -> invite.userId }

//
                        Log.d("EventDetailsViewModel", "Accepted user IDs: $acceptedUserIds")


                        val usersResponse = userRepository.getAllUsers()
                        if (usersResponse?.isSuccessful == true && usersResponse.body() != null) {
                            val allUsers: List<User> = usersResponse.body()!!

                            val acceptedUsers: List<User> = allUsers.filter { user ->
                                user.id in acceptedUserIds
                            }

                            Log.e("prihv ", acceptedUsers[1].avatar + "")
                            _uiState.value = _uiState.value.copy(participants = acceptedUsers)


                        }


                    }
                }


            }
        }
    }}


