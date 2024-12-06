package com.example.hangoutz.ui.screens.createEvent

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hangoutz.data.local.SharedPreferencesManager
import com.example.hangoutz.data.models.EventCardDPO
import com.example.hangoutz.data.models.Friend
import com.example.hangoutz.domain.repository.FriendsRepository
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



@HiltViewModel
class CreateEventViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val friendsRepository: FriendsRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(CreateEventState())
    val uiState: StateFlow<CreateEventState> = _uiState

    init {
        getFriends()
    }

    fun createEvent() {
        //TODO
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
            participants = _uiState.value.participants + _uiState.value.selectedParticipants
        )
    }

    fun removeUser(userID: UUID) {
            //TODO remove participant from list
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

    fun onDateChange(date: String) { //TODO Configure datepicker
        _uiState.value = _uiState.value.copy(date = date)
    }

    fun onTimeChange(time: String) { //TODO Configure timepicker
        _uiState.value = _uiState.value.copy(time = time)
    }
}