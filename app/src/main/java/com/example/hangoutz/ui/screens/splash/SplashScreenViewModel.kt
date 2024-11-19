package com.example.hangoutz.ui.screens.splash

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hangoutz.data.local.SharedPreferencesManager
import com.example.hangoutz.data.models.Event
import com.example.hangoutz.data.models.Invite
import com.example.hangoutz.domain.repository.EventRepository
import com.example.hangoutz.domain.repository.InviteRepository
import com.example.hangoutz.utils.toDate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class SplashScreenViewModel @Inject constructor(
    private val eventRepository: EventRepository,
    private val inviteRepository: InviteRepository
) : ViewModel() {
    @RequiresApi(Build.VERSION_CODES.O)
    fun deleteEventsFromPast() {
        viewModelScope.launch {
            val events = getEvents()
            events?.filter { it.date.toDate() < LocalDateTime.now() }?.forEach {
                val invitesForEvent = getInvites(it.id)
                invitesForEvent?.forEach {
                    inviteRepository.deleteInvite(it.id)
                }
                Log.d("brisanje", eventRepository.deleteEvent(it.id).code().toString())
            }
        }

    }
    fun isUserLoggedIn(context: Context): Boolean {

        if (SharedPreferencesManager.getUserId(context = context) != null) {
            return true
        }
        return false
    }
    suspend private fun getEvents(): List<Event>? {
        val response = eventRepository.getEvents()
        if (response.isSuccessful && response.body() != null) {
            return response.body()
        }
        return null
    }

    suspend private fun getInvites(id: UUID): List<Invite>? {
        val response = inviteRepository.getInvitesByEventId(id)
        if (response.isSuccessful && response.body() != null) {
            return response.body()
        }
        return null
    }

}