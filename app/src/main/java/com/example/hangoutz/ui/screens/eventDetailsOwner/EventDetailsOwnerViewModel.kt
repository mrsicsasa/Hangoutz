package com.example.hangoutz.ui.screens.eventDetailsOwner

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.hangoutz.data.local.SharedPreferencesManager
import com.example.hangoutz.data.models.Friend
import com.example.hangoutz.data.models.InviteRequest
import com.example.hangoutz.data.models.User
import com.example.hangoutz.domain.repository.EventRepository
import com.example.hangoutz.domain.repository.FriendsRepository
import com.example.hangoutz.domain.repository.InviteRepository
import com.example.hangoutz.domain.repository.UserRepository
import com.example.hangoutz.utils.Constants
import com.example.hangoutz.utils.Dimensions
import com.example.hangoutz.utils.checkIfInPast
import com.example.hangoutz.utils.convertTimeToMillis
import com.example.hangoutz.utils.formatDateTime
import com.example.hangoutz.utils.formatForDatabase
import com.example.hangoutz.utils.mapUserToFriend
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
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
    var participants: List<Friend> = emptyList(),
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
    val errorTitle: String = "",
    val errorDesc: String = "",
    val errorCity: String = "",
    val errorStreet: String = "",
    val errorPlace: String = "",
    val errorDate: String = "",
    var errorMessage: String? = "",
    var participantFriends: List<Friend> = emptyList(),
    val selectedParticipants: List<Friend> = emptyList(),
    val searchQuery: String = "",
    val listOfFriends: List<Friend> = emptyList(),
    val isLoading: Boolean = false
)

@HiltViewModel
class EventDetailsOwnerViewModel @Inject constructor(
    private val inviteRepository: InviteRepository,
    private val userRepository: UserRepository,
    private val eventRepository: EventRepository,
    private val friendsRepository: FriendsRepository,
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
        val formattedDateTime = formatForDatabase(uiState.value.date, _uiState.value.time) ?: ""
        _uiState.value = _uiState.value.copy(formattedDateForDatabase = formattedDateTime)

        if (validateInputs()) {
            _uiState.value = _uiState.value.copy(errorMessage = "")
            viewModelScope.launch {
                val userId = SharedPreferencesManager.getUserId(context)
                val eventResponse = eventRepository.patchEventById(
                    id = _uiState.value.eventId.toString(),
                    newTitle = _uiState.value.title,
                    newDesc = _uiState.value.description,
                    newCity = _uiState.value.city,
                    newStreet = _uiState.value.street,
                    newPlace = _uiState.value.place,
                    newDate = _uiState.value.formattedDateForDatabase,
                    owner = userId ?: ""
                )
                if (eventResponse?.isSuccessful == true) {
                    insertNewParticipantsIntoDatabase(_uiState.value.eventId.toString())
                    onSuccess()
                    Log.i("Info", "Successfully patched event")
                } else Log.e("Info", "${eventResponse.code()}")
            }

        } else {
            Log.e("Error", "Fields marked with * cant be empty")
        }
    }

    private fun insertNewParticipantsIntoDatabase(eventId: String) {
        viewModelScope.launch {
            for (participant in _uiState.value.participantFriends.filter { friend -> friend !in _uiState.value.participants }) {
                val response = inviteRepository.insertInvite(
                    InviteRequest(
                        "invited", participant.id.toString(), eventId
                    )
                )
                if (response.isSuccessful) {
                    Log.e("Info", "Successfully added new invites")
                } else Log.e(
                    "Info", "An error has occurred while adding new invites ${response.code()}"
                )

            }
        }
    }

    fun deleteEvent(onSuccess: () -> Unit, onError: (String) -> Unit) {
        val eventId = _uiState.value.eventId
        if (eventId == null) {
            onError("Event ID is missing.")
            return
        }
        viewModelScope.launch {

            val invitesResponse = _uiState.value.eventId?.let {
                inviteRepository.getInvitesByEventId(
                    it
                )
            }
            val invites = invitesResponse?.body()
            //TODO
            val deleteParticipantResponses = _uiState.value.participants.map { participant ->
                inviteRepository.deleteInviteByEventId(participant.id.toString(), eventId)
            }
            val allParticipantDeletionsSuccessful =
                deleteParticipantResponses.all { it?.isSuccessful == true }
            if (allParticipantDeletionsSuccessful) {
                Log.i("EventDetailsOwner", "Successfully deleted invites for event")

                val deleteEventResponse = eventRepository.deleteEvent(id = eventId)
                if (deleteEventResponse?.isSuccessful == true) {
                    onSuccess()
                    Log.i("EventDetailsOwner", "Successfully deleted the event")
                } else {
                    onError("Failed to delete the event.")
                }
            } else {
                onError("Failed to delete invites.")
            }
        }
    }

    fun getInitialTimeForPicker(): Long {
        val time = _uiState.value.time
        return convertTimeToMillis(time)
    }

    fun getInitialDateForPicker(): Long {
        val formatter = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
        val date = formatter.parse(_uiState.value.date)
        val calendar = Calendar.getInstance()

        calendar.time = date
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        calendar.add(Calendar.DAY_OF_YEAR, 1)
        return calendar.timeInMillis ?: 0L
    }

    fun removeUser(userID: UUID) {

            viewModelScope.launch {
                val updatedParticipants =
                    _uiState.value.participantFriends.filter { it.id != userID }
                _uiState.value = _uiState.value.copy(participantFriends = updatedParticipants)

                val response = _uiState.value.eventId?.let {
                    inviteRepository.deleteInviteByEventId(
                        userID.toString(), it
                    )
                }

                if (response?.isSuccessful == true) {
                    Log.i("RemoveUser", "User successfully removed from event")
                } else {
                    Log.e("RemoveUser", "Failed to remove user from event")
                }
            }
            getData()

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

                    val invitesResponse = _uiState.value.eventId?.let {
                        inviteRepository.getInvitedOrAcceptedByEventId(
                            it
                        )
                    }
                    if (invitesResponse?.isSuccessful == true && invitesResponse.body() != null) {
                        val acceptedUserIds: List<UUID> =
                            invitesResponse.body()!!.map { invite -> invite.userId }
                        val usersResponse = userRepository.getAllUsers()
                        if (usersResponse?.isSuccessful == true && usersResponse.body() != null) {
                            val allUsers: List<User> = usersResponse.body()!!
                            val acceptedUsers: List<User> = allUsers.filter { user ->
                                user.id in acceptedUserIds
                            }
                            _uiState.value = _uiState.value.copy(
                                participants = mapUserToFriend(
                                    acceptedUsers
                                )
                            )
                            _uiState.value =
                                _uiState.value.copy(participantFriends = _uiState.value.participantFriends + _uiState.value.participants.filter { friend -> friend !in _uiState.value.participantFriends })

                        }
                    }
                }
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
            listOfFriends = emptyList(), isLoading = true
        )
        viewModelScope.launch {
            val response = withContext(Dispatchers.IO) {
                SharedPreferencesManager.getUserId(context)?.let {
                    MutableStateFlow(
                        friendsRepository.getFriendsFromUserId(
                            it, _uiState.value.searchQuery
                        )
                    )
                }
            }
            response?.value?.let {
                if (it.isSuccessful) {
                    response.value.body()?.let { friends ->
                        _uiState.value =
                            _uiState.value.copy(listOfFriends = friends.sortedBy { friend -> friend.users.name.uppercase() }
                                .map { friendData -> friendData.users } - _uiState.value.participantFriends,
                                isLoading = false)
                    }
                }
            }
        }
    }

    fun clearSearchQuery() {
        _uiState.value = _uiState.value.copy(
            searchQuery = "", listOfFriends = emptyList()
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
            participantFriends = _uiState.value.participantFriends + _uiState.value.selectedParticipants,
            selectedParticipants = emptyList()
        )
    }


    fun checkLength(text: String, length: Int): Boolean {
        if (text.length <= length) return true
        else return false
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
        _uiState.value = _uiState.value.copy(title = title, isTitleError = false)
    }

    fun onDescriptionChange(description: String) {
        _uiState.value = _uiState.value.copy(description = description, isDescError = false)
    }

    fun onCityChange(city: String) {
        _uiState.value = _uiState.value.copy(city = city, isCityError = false)
    }

    fun onStreetChange(street: String) {
        _uiState.value = _uiState.value.copy(street = street, isStreetError = false)
    }

    fun onPlaceChange(place: String) {
        _uiState.value = _uiState.value.copy(place = place, isPlaceError = false)
    }

    fun onDateChange(date: String) {
        _uiState.value = _uiState.value.copy(date = date, isDateError = false)
    }

    fun onTimeChange(time: String) {
        _uiState.value = _uiState.value.copy(time = time, isTimeError = false)
    }

    private fun validateInputs(): Boolean {
        resetErrorState()

        val validateTitle = validateTitleField()
        val validatePlace = validatePlaceField()
        val validateDesc = validateDescriptionField()
        val validateCity = validateCityField()
        val validateStreet = validateStreetField()
        val validateDateTime = checkCombinedDateTime()

        updateUiState(
            validateTitle,
            validatePlace,
            validateDateTime,
            validateDesc,
            validateCity,
            validateStreet
        )

        return !(validateTitle || validatePlace || validateDateTime || validateDesc || validateCity || validateStreet)
    }

    private fun resetErrorState() {
        _uiState.value = _uiState.value.copy(
            errorMessage = "",
            errorTitle = "",
            errorDesc = "",
            errorCity = "",
            errorStreet = "",
            errorDate = "",
            errorPlace = ""
        )
    }

    private fun validateTitleField(): Boolean {
        var isError = _uiState.value.title.trim().isEmpty()
        if (isError) {
            _uiState.value = _uiState.value.copy(errorMessage = Constants.ERROR_EMPTY_FIELD)
            Log.e("e", "empty title error ${_uiState.value.errorMessage}")
        } else if (!checkLength(_uiState.value.title, Dimensions.MAX_LENGTH)) {
            isError = true
            _uiState.value = _uiState.value.copy(errorTitle = Constants.ERROR_TOO_LONG)
        }
        return isError
    }

    private fun validatePlaceField(): Boolean {
        var isError = _uiState.value.place.trim().isEmpty()
        if (!isError && !checkLength(_uiState.value.place, Dimensions.MAX_LENGTH)) {
            isError = true
            _uiState.value = _uiState.value.copy(errorPlace = Constants.ERROR_TOO_LONG)
        }
        return isError
    }

    private fun checkCombinedDateTime(): Boolean {
        val combinedInput = "${_uiState.value.date} ${_uiState.value.time}"
        if (_uiState.value.date.isNullOrEmpty() || _uiState.value.time.isNullOrEmpty()) {
            _uiState.value = _uiState.value.copy(errorMessage = Constants.ERROR_EMPTY_FIELD)
            return true
        } else if (checkIfInPast(combinedInput)) {
            _uiState.value = _uiState.value.copy(errorMessage = Constants.DATE_IN_PAST)
            return true
        } else {
            return false
        }
    }

    private fun validateDescriptionField(): Boolean {
        return if (_uiState.value.description.length > Dimensions.MAX_LENGTH_DESC) {
            _uiState.value = _uiState.value.copy(errorDesc = Constants.ERROR_TOO_LONG_DESC)
            true
        } else {
            false
        }
    }

    private fun validateCityField(): Boolean {
        return if (_uiState.value.city.length > Dimensions.MAX_LENGTH) {
            _uiState.value = _uiState.value.copy(errorCity = Constants.ERROR_TOO_LONG)
            true
        } else {
            false
        }
    }

    private fun validateStreetField(): Boolean {
        return if (_uiState.value.street.length > Dimensions.MAX_LENGTH) {
            _uiState.value = _uiState.value.copy(errorStreet = Constants.ERROR_TOO_LONG)
            true
        } else {
            false
        }
    }

    private fun updateUiState(
        validateTitle: Boolean,
        validatePlace: Boolean,
        validateDateTime: Boolean,
        validateDesc: Boolean,
        validateCity: Boolean,
        validateStreet: Boolean
    ) {
        _uiState.value = _uiState.value.copy(
            isTitleError = validateTitle,
            isPlaceError = validatePlace,
            isDateError = validateDateTime,
            isDescError = validateDesc,
            isCityError = validateCity,
            isStreetError = validateStreet
        )
    }
}