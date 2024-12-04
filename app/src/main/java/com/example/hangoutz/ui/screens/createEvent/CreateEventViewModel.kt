package com.example.hangoutz.ui.screens.createEvent

import android.content.Context
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import com.example.hangoutz.data.models.User
import com.example.hangoutz.domain.repository.UserRepository
import com.example.hangoutz.utils.Dimensions
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID
import javax.inject.Inject


data class EventDetailsData(

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
)

@HiltViewModel
class CreateEventViewModel @Inject constructor(
    private val userRepository: UserRepository,

    @ApplicationContext private val context: Context
) : ViewModel() {

    private val _uiState = MutableStateFlow(EventDetailsData())
    val uiState: StateFlow<EventDetailsData> = _uiState

    fun createEvent() {
        //TODO
    }

    fun onTimePicked(date: Long) {
    }

    fun formatTime(timeMillis: Long): String {
        val timeFormat = SimpleDateFormat("HH.mm", Locale.getDefault())
        return timeFormat.format(Date(timeMillis))
    }

    fun onDatePicked(date: Long) {
        val formattedDate = formatDate(date)
        val formattedTime = formatTime(date)
        onTimeChange(formattedTime)
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

    fun onDateChange(date: String) { //TODO Configure datepicker
        _uiState.value = _uiState.value.copy(date = date)
    }

    fun onTimeChange(time: String) { //TODO Configure timepicker
        _uiState.value = _uiState.value.copy(time = time)
    }
}



