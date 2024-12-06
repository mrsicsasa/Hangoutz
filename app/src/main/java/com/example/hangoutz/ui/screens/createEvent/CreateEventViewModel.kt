package com.example.hangoutz.ui.screens.createEvent

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hangoutz.data.local.SharedPreferencesManager
import com.example.hangoutz.data.models.EventRequest
import com.example.hangoutz.data.models.Friend
import com.example.hangoutz.domain.repository.EventRepository
import com.example.hangoutz.domain.repository.FriendsRepository
import com.example.hangoutz.utils.Constants
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
import java.util.UUID
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
    private val eventsRepository: EventRepository
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
            formatForDatabase()
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
                if (insertEventResponse?.isSuccessful == true) {
                    onSuccess()
                    Log.i("Info", "Successfully patched event")
                } else Log.e("Info", "${insertEventResponse.code()}")
            }

        } else {
            Log.e("Error", "Fields marked with * cant be empty")
        }
    }

    fun getFriends() {
        _uiState.value = _uiState.value.copy(
            listOfFriends = emptyList(),
            isLoading = true
        )
        viewModelScope.launch {
            val response = withContext(Dispatchers.IO) {
                SharedPreferencesManager.getUserId(context)?.let {
                    MutableStateFlow(friendsRepository.getFriendsFromUserId(it))
                }
            }
            response?.value?.let {
                if (it.isSuccessful) {
                    response.value.body()?.let {
                        _uiState.value =
                            _uiState.value.copy(listOfFriends = it.sortedBy { it.users.name.uppercase() }
                                .map { it.users } - _uiState.value.participants, isLoading = false)
                    }
                }
            }
        }
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

    fun removeUser(userID: UUID) {
        //TODO remove participant from list
    }

    private fun formatForDatabase() {
        val inputDate = _uiState.value.date
        val inputTime = _uiState.value.time

        val combined = "$inputDate $inputTime"

        val inputFormat = SimpleDateFormat("dd.MM.yyyy. HH.mm", Locale.getDefault())
        val outputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val formattedDateTime = outputFormat.format(inputFormat.parse(combined)!!)
        _uiState.value = _uiState.value.copy(formattedDateForDatabase = formattedDateTime)
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

        if (!validateTitle && !checkLength(_uiState.value.title, 25)) {
            validateTitle = true
            _errorState.value = _errorState.value.copy(errorTitle = Constants.ERROR_TOO_LONG)
        }

        if (!validatePlace && !checkLength(_uiState.value.place, 25)) {
            validatePlace = true
            _errorState.value = _errorState.value.copy(errorPlace = Constants.ERROR_TOO_LONG)
        }

        if (!validateDate && !validateTime) {
            val combinedInput = "${_uiState.value.date} ${_uiState.value.time}"
            if (checkIfInPast(combinedInput)) {
                validateDate = true
                validateTime = true
                _errorState.value =
                    _errorState.value.copy(errorMessage = "Date and time cannot be in the past")
            }
        }

        if (_uiState.value.description.length > 100) {
            validateDesc = true
            _errorState.value = _errorState.value.copy(errorDesc = Constants.ERROR_TOO_LONG_DESC)
        }

        if (_uiState.value.city.length > 25) {
            validateCity = true
            _errorState.value = _errorState.value.copy(errorCity = Constants.ERROR_TOO_LONG)
        }

        if (_uiState.value.street.length > 25) {
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