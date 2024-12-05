package com.example.hangoutz.ui.screens.eventDetailsOwner

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.RequestState
import com.example.hangoutz.data.models.User
import com.example.hangoutz.domain.repository.EventRepository
import com.example.hangoutz.domain.repository.InviteRepository
import com.example.hangoutz.domain.repository.UserRepository
import com.example.hangoutz.utils.Constants
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
    var isMine: Boolean = true,
    var eventId: UUID? = null,
    var title: String = "",
    var description: String = "",
    var city: String = "",
    var street: String = "",
    var place: String = "",
    var date: String = "",
    var time: String = "",
    var participants: List<User> = emptyList(),
    var showDatePicker: Boolean = false,
    var showTimePicker: Boolean = false,
    var isTitleError: Boolean = false,
    var isPlaceError: Boolean = false,
    var isDateError: Boolean = false,
    var isTimeError: Boolean = false,
    var isDescError: Boolean = false,
    var isCityError: Boolean = false,
    var isStreetError: Boolean = false,
    var formattedDateForDatabase: String = "",
    val errorTitle : String = "",
    val errorDesc : String = "",
    val errorCity : String = "",
    val errorStreet : String = "",
    val errorPlace : String = "",
    val errorDate : String = "",
    var errorMessage: String? = ""
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

    fun editEvent(onSuccess: () -> Unit) {
        formatForDatabase()
        Log.e(
            "DATA",
            "id=  ${_uiState.value.eventId.toString()}, newTitle =  ${_uiState.value.title}, newPlace = ${_uiState.value.place}, newDate =  ${_uiState.value.formattedDateForDatabase}"
        )
        if (validateInputs()) {
            _uiState.value = _uiState.value.copy(errorMessage = "")
            viewModelScope.launch {

                val eventResponse = eventRepository.patchEventById(
                    id = _uiState.value.eventId.toString(),
                    newTitle = _uiState.value.title,
                    newPlace = _uiState.value.place,
                    newDate = _uiState.value.formattedDateForDatabase,

                    )
                if (eventResponse?.isSuccessful == true) {
                    onSuccess()
                    Log.i("Info", "Successfully patched event")
                } else Log.e("Info", "${eventResponse.code()}")
            }

        } else {
            Log.e("Error", "Fields marked with * cant be empty")
        }
    }

    fun formatForDatabase() {
        val inputDate = _uiState.value.date
        val inputTime = _uiState.value.time

        val combined = "$inputDate $inputTime"

        val inputFormat = SimpleDateFormat("dd.MM.yyyy. HH.mm", Locale.getDefault())
        val outputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val formattedDateTime = outputFormat.format(inputFormat.parse(combined)!!)
        _uiState.value = _uiState.value.copy(formattedDateForDatabase = formattedDateTime)
    }

    fun deleteEvent() {
        //TODO
    }

    fun removeUser(userID: UUID) {
        //TODO
    }

    private fun formatDateTime(dateTimeString: String): Pair<String, String> {

        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:ss", Locale.getDefault())
        val dateFormat = SimpleDateFormat("dd.MM.yyyy.", Locale.getDefault())
        val timeFormat = SimpleDateFormat("HH.mm", Locale.getDefault())

        val date = inputFormat.parse(dateTimeString)
        val formattedDate = dateFormat.format(date)
        val formattedTime = timeFormat.format(date)

        return Pair(formattedDate, formattedTime)
    }


    fun getData() {
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
                        place = it.place ?: "",
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
                            _uiState.value = _uiState.value.copy(participants = acceptedUsers)
                        }
                    }
                }
            }
        }
    }

    fun checkLength(text: String, length: Int): Boolean {
        if (text.length <= length) return true
        else return false
    }

    fun checkIfInPast(date: String): Boolean {
        var isValid: Boolean = false
        val inputFormat = SimpleDateFormat("dd.MM.yyyy. HH.mm", Locale.getDefault())
        try {
            val inputDateTime = inputFormat.parse(date)
            if (inputDateTime != null && inputDateTime.before(Date())) {
                isValid = true
            } else isValid = false
        } catch (e: Exception) {
            Log.e("Error", "Exception while parsing")
        }
    return isValid
    }

        fun validateInputs(): Boolean {

            _uiState.value = _uiState.value.copy(
                errorMessage = "",
                errorTitle = "",
                errorDesc = "",
                errorCity = "",
                errorStreet = "",
                errorDate = "",
                errorPlace = ""
            )
            var validateTitle = _uiState.value.title.trim().isEmpty()
            var validatePlace = _uiState.value.place.trim().isEmpty()
            var validateDate = _uiState.value.date.trim().isEmpty()
            var validateTime = _uiState.value.time.trim().isEmpty()
            var validateDesc = false
            var validateCity = false
            var validateStreet = false

            if (validateTitle || validatePlace || validateDate || validateTime) {
                _uiState.value = _uiState.value.copy(errorMessage = Constants.ERROR_EMPTY_FIELD)
            }

            if (!validateTitle && !checkLength(_uiState.value.title, 25)) {
                validateTitle = true
                _uiState.value = _uiState.value.copy(errorTitle = Constants.ERROR_TOO_LONG)
            }

            if (!validatePlace && !checkLength(_uiState.value.place, 25)) {
                validatePlace = true
                _uiState.value = _uiState.value.copy(errorPlace = Constants.ERROR_TOO_LONG)
            }

            if (!validateDate && !validateTime) {
                val combinedInput = "${_uiState.value.date} ${_uiState.value.time}"
                if (checkIfInPast(combinedInput)) {
                    validateDate = true
                    validateTime = true
                    _uiState.value =
                        _uiState.value.copy(errorMessage = "Date and time cannot be in the past")
                }
            }

            if (_uiState.value.description.length > 100) {
                validateDesc = true
                _uiState.value = _uiState.value.copy(errorDesc = Constants.ERROR_TOO_LONG_DESC)
            }

            if (_uiState.value.city.length > 25) {
                validateCity = true
                _uiState.value = _uiState.value.copy(errorCity = Constants.ERROR_TOO_LONG)
            }

            if (_uiState.value.street.length > 25) {
                validateStreet = true
                _uiState.value = _uiState.value.copy(errorStreet = Constants.ERROR_TOO_LONG)
            }

            _uiState.value = _uiState.value.copy(
                isTitleError = validateTitle,
                isPlaceError = validatePlace,
                isDateError = validateDate,
                isTimeError = validateTime,
                isDescError = validateDesc,
                isCityError = validateCity,
                isStreetError = validateStreet
            )

            return !(validateTitle || validatePlace || validateDate || validateTime || validateDesc || validateCity || validateStreet)
        }


        fun onTimePicked(date: Long) {
        val formattedTime = formatTime(date)
        onTimeChange(formattedTime)
    }

    private fun formatTime(timeMillis: Long): String {
        val timeFormat = SimpleDateFormat("HH.mm", Locale.getDefault())
        return timeFormat.format(Date(timeMillis))
    }

    fun onDatePicked(date: Long) {
        val formattedDate = formatDate(date)
        onDateChange(formattedDate)
    }

    private fun formatDate(dateMillis: Long): String {
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
        _uiState.value = _uiState.value.copy(title = title, isTitleError = false)
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
        _uiState.value = _uiState.value.copy(place = place, isPlaceError = false)
    }

    fun onDateChange(date: String) { //TODO Configure datepicker validation
        _uiState.value = _uiState.value.copy(date = date, isDateError = false)
    }

    fun onTimeChange(time: String) { //TODO Configure timepicker validation
        _uiState.value = _uiState.value.copy(time = time, isDateError = false)
    }
}