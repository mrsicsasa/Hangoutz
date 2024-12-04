package com.example.hangoutz.ui.screens.eventDetailsOwner

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
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
import java.util.Date
import java.util.Locale
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
    var participants: List<User> = emptyList(),
    var showDatePicker: Boolean = false,
    var showTimePicker: Boolean = false
)

@HiltViewModel
class EventDetailsOwnerViewModel @Inject constructor(
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

    fun editEvent() {
        //TODO
    }

    fun deleteEvent() {
        //TODO
    }

    fun removeUser(userID: UUID) {
        //TODO
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
                    val dateTemp = _uiState.value.date ?: ""
                    val (formattedDate, formattedTime) = formatDateTime(dateTemp)

                    _uiState.value = _uiState.value.copy(date = formattedDate, time = formattedTime)
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
//                            Log.e("prihv ", acceptedUsers[1].avatar + "")
                            _uiState.value = _uiState.value.copy(participants = acceptedUsers)

                        }
                    }
                }
            }
        }
    }

    fun onTimePicked(date: Long) {
        val formattedTime = formatTime(date)
        onTimeChange(formattedTime)
    }

    fun formatTime(timeMillis: Long): String {
        val timeFormat = SimpleDateFormat("HH.mm", Locale.getDefault())
        return timeFormat.format(Date(timeMillis))
    }

    fun onDatePicked(date: Long) {
        val formattedDate = formatDate(date)
        onDateChange(formattedDate)
    }

    fun formatDate(dateMillis: Long): String {
        val dateFormat = SimpleDateFormat("dd.MM.yyyy.", Locale.getDefault())
        return dateFormat.format(Date(dateMillis))
    }

    fun setShowDatePicker() {
        val showDatePicker = !_uiState.value.showDatePicker
        _uiState.value = _uiState.value.copy(showDatePicker = showDatePicker)
    }

    fun setShowTimePicker() {
        val showTimePicker = !_uiState.value.showTimePicker
        _uiState.value = _uiState.value.copy(showTimePicker = showTimePicker)
    }

    fun onTitleChange(title: String) {
        _uiState.value = _uiState.value.copy(title = title)
    }

    fun onDescriptionChange(description: String) {
        _uiState.value = _uiState.value.copy(description = description)
    }

    fun onCityChange(city: String) {
        _uiState.value = _uiState.value.copy(city = city)
    }

    fun onStreetChange(street: String) {
        _uiState.value = _uiState.value.copy(street = street)
    }

    fun onPlaceChange(place: String) {
        _uiState.value = _uiState.value.copy(place = place)
    }

    fun onDateChange(date: String) { //TODO Configure datepicker validation
        _uiState.value = _uiState.value.copy(date = date)
    }

    fun onTimeChange(time: String) { //TODO Configure timepicker validation
        _uiState.value = _uiState.value.copy(time = time)
    }
}


