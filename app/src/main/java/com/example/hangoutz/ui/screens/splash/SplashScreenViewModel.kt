package com.example.hangoutz.ui.screens.splash

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hangoutz.data.models.Event
import com.example.hangoutz.domain.repository.EventRepository
import com.example.hangoutz.utils.toDate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class SplashScreenViewModel @Inject constructor(
    private val repository: EventRepository
): ViewModel() {
    @RequiresApi(Build.VERSION_CODES.O)
     fun deleteEventsFromPast() {
       viewModelScope.launch {
           val events = getEvents()
           val oldEvents = events?.filter { it.date.toDate() < LocalDateTime.now() }
           Log.d("Test",oldEvents.toString())
       }

    }
    suspend private fun getEvents(): List<Event>? {
        val response = repository.getEvents()
        if(response.isSuccessful && response.body() != null) {
            return response.body()
        }
        return null
    }

}