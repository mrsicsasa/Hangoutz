package com.example.hangoutz.ui.screens.createEvent

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hangoutz.data.local.SharedPreferencesManager
import com.example.hangoutz.data.models.EventRequest
import com.example.hangoutz.data.models.Friend
import com.example.hangoutz.data.models.InviteRequest
import com.example.hangoutz.domain.repository.EventRepository
import com.example.hangoutz.domain.repository.FriendsRepository
import com.example.hangoutz.domain.repository.InviteRepository
import com.example.hangoutz.utils.Constants
import com.example.hangoutz.utils.Dimensions
import com.example.hangoutz.utils.formatForDatabase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

data class ErrorState(
    val errorTitle: String = "",
    val errorDesc: String = "",
    val errorCity: String = "",
    val errorStreet: String = "",
    val errorPlace: String = "",
    val errorDate: String = "",
    var errorMessage: String? = ""
)


@HiltViewModel
class CreateEventViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val friendsRepository: FriendsRepository,
    private val eventsRepository: EventRepository,
    private val inviteRepository: InviteRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(CreateEventState())
    val uiState: StateFlow<CreateEventState> = _uiState

    private val _errorState = MutableStateFlow(ErrorState())
    val errorState: StateFlow<ErrorState> = _errorState

    init {
        getFriends()
    }

    fun createEvent(onSuccess: () -> Unit, onFailure: () -> Unit) {

        if (validateInputs()) {
            val formattedDateTime = formatForDatabase(uiState.value.date, _uiState.value.time) ?: ""
            _uiState.value = _uiState.value.copy(formattedDateForDatabase = formattedDateTime)

            _errorState.value = _errorState.value.copy(errorMessage = "")

            viewModelScope.launch {
                val owner = SharedPreferencesManager.getUserId(context)

                val insertEventResponse = eventsRepository.insertEvent(
                    EventRequest(
                        title = _uiState.value.title,
                        description = _uiState.value.description,
                        city = _uiState.value.city,
                        street = _uiState.value.street,
                        place = _uiState.value.place,
                        date = _uiState.value.formattedDateForDatabase,
                        owner = owner ?: ""
                    )
                )
                if(insertEventResponse.isSuccessful) {
                    Log.i("Info", "Event was created successfully")
                }
                if(_uiState.value.participants.isNotEmpty()){
                    val response =  owner?.let { eventsRepository.getEventsByOwnerTitleAndDate(
                            it,
                            _uiState.value.title,

                        )
                       }
                    if (response != null && response.isSuccessful) {
                        val events = response.body()

                      if (events != null && events.isNotEmpty()) {
                        val eventId = events.first().id
                        postFriendInvites(eventId, _uiState.value.participants)

                    } else {
                        Log.e("Error", "An error has occurred while getting user")
                    }
                }
                    onSuccess()
                    Log.i("Info", "Successfully patched event")
                }
            }
        }
        else {
            Log.e("Error", "Fields marked with * cant be empty")
        }
    }
    fun postFriendInvites(eventId: String, participants: List<Friend>) {

        viewModelScope.launch {
            try {
                for (participant in participants) {
                    val response = inviteRepository.insertInvite(
                        InviteRequest("invited", participant.id.toString(), eventId)
                    )
                    Log.e("informacije", "${eventId} , ${participant.id}")

                    if (response.isSuccessful) {
                        Log.i(
                            "Info",
                            "Successfully added participant ${participant.name} to event $eventId"
                        )
                    } else {
                        Log.e("Error----------", "${response.errorBody()}")
                        Log.e(
                            "Error",
                            "Failed to add participant ${participant.name} to event $eventId"
                        )
                        break
                    }
                }
            } catch (exception: Exception) {

                Log.e("Error", "Exception while sending invites: ${exception.localizedMessage}")
            }
        }
    }
    fun onSearchInput(newText: String) {
        _uiState.value = _uiState.value.copy(searchQuery = newText)
        if (_uiState.value.searchQuery.length >= Constants.MIN_SEARCH_LENGTH) {
            getFriends()
        } else {
            _uiState.value = _uiState.value.copy(
                listOfFriends = emptyList()
            )
        }
    }

    private fun getFriends() {
        _uiState.value = _uiState.value.copy(
            listOfFriends = emptyList(),
            isLoading = true
        )
        viewModelScope.launch {
            val response = withContext(Dispatchers.IO) {
                SharedPreferencesManager.getUserId(context)?.let {
                    MutableStateFlow(
                        friendsRepository.getFriendsFromUserId(
                            it,
                            _uiState.value.searchQuery
                        )
                    )
                }
            }
            response?.value?.let {
                if (it.isSuccessful) {
                    response.value.body()?.let { friends ->
                        _uiState.value =
                            _uiState.value.copy(listOfFriends = friends.sortedBy { friend -> friend.users.name.uppercase() }
                                .map { friendData -> friendData.users } - _uiState.value.participants,
                                isLoading = false)
                    }
                }
            }
        }
    }

    fun clearSearchQuery() {
        _uiState.value = _uiState.value.copy(
            searchQuery = "",
            listOfFriends = emptyList()
        )
    }

    fun addParticipant(user: Friend) {
        _uiState.value = _uiState.value.copy(
            selectedParticipants = _uiState.value.selectedParticipants + user
        )
    }

    fun removeParticipant(user: Friend) {
        _uiState.value = _uiState.value.copy(
            selectedParticipants = _uiState.value.selectedParticipants - user
        )
    }

    fun addSelectedParticipants() {
        _uiState.value = _uiState.value.copy(
            participants = _uiState.value.participants + _uiState.value.selectedParticipants,
            selectedParticipants = emptyList()
        )
    }

    fun removeSelectedParticipant(friend: Friend) {
        _uiState.value = _uiState.value.copy(
            participants = _uiState.value.participants - friend,
        )
        removeParticipant(friend)
    }


    fun onTimePicked(date: Long) {
        val formattedTime = formatTime(date)
        onTimeChange(formattedTime)
    }

    private fun formatTime(timeMillis: Long): String {
        val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        return timeFormat.format(Date(timeMillis))
    }

    fun onDatePicked(date: Long) {
        val formattedDate = formatDate(date)
        onDateChange(formattedDate)
    }

    private fun formatDate(dateMillis: Long): String {
        val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
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

    fun onDateChange(date: String) {
        _uiState.value = _uiState.value.copy(date = date)
    }

    fun onTimeChange(time: String) {
        _uiState.value = _uiState.value.copy(time = time)
    }

    fun checkLength(text: String, length: Int): Boolean {
        if (text.length <= length) return true
        else return false
    }

    fun checkIfInPast(date: String): Boolean {
        var isValid: Boolean = false
        val inputFormat = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())
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

    private fun validateInputs(): Boolean {

        _errorState.value = _errorState.value.copy(
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
            _errorState.value = _errorState.value.copy(errorMessage = Constants.ERROR_EMPTY_FIELD)
            Log.e("e", "empty fields error ${_errorState.value.errorMessage}")
        }

        if (!validateTitle && !checkLength(_uiState.value.title, Dimensions.MAX_LENGTH)) {
            validateTitle = true
            _errorState.value = _errorState.value.copy(errorTitle = Constants.ERROR_TOO_LONG)
        }

        if (!validatePlace && !checkLength(_uiState.value.place, Dimensions.MAX_LENGTH)) {
            validatePlace = true
            _errorState.value = _errorState.value.copy(errorPlace = Constants.ERROR_TOO_LONG)
        }

        if (!validateDate && !validateTime) {
            val combinedInput = "${_uiState.value.date} ${_uiState.value.time}"
            if (checkIfInPast(combinedInput)) {
                validateDate = true
                validateTime = true
                _errorState.value =
                    _errorState.value.copy(errorMessage = Constants.DATE_IN_PAST)
            }
        }

        if (_uiState.value.description.length > Dimensions.MAX_LENGTH_DESC) {
            validateDesc = true
            _errorState.value = _errorState.value.copy(errorDesc = Constants.ERROR_TOO_LONG_DESC)
        }

        if (_uiState.value.city.length > Dimensions.MAX_LENGTH) {
            validateCity = true
            _errorState.value = _errorState.value.copy(errorCity = Constants.ERROR_TOO_LONG)
        }

        if (_uiState.value.street.length > Dimensions.MAX_LENGTH) {
            validateStreet = true
            _errorState.value = _errorState.value.copy(errorStreet = Constants.ERROR_TOO_LONG)
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
}