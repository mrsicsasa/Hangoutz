package com.example.hangoutz.ui.screens.splash

import android.content.Context
import android.os.Build
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
            events?.filter { event -> event.date.toDate() < LocalDateTime.now() }?.forEach { event ->
                val invitesForEvent = getInvites(event.id)
                invitesForEvent?.forEach { invite ->
                    inviteRepository.deleteInvite(invite.id)
                }
            }
        }
    }

    fun isUserLoggedIn(context: Context): Boolean {
        return SharedPreferencesManager.getUserId(context = context) != null
    }

    private suspend fun getEvents(): List<Event>? {
        val response = eventRepository.getEvents()
        if (response.isSuccessful && response.body() != null) {
            return response.body()
        }
        return null
    }

    private suspend fun getInvites(id: UUID): List<Invite>? {
        val response = inviteRepository.getInvitesByEventId(id)
        if (response.isSuccessful && response.body() != null) {
            return response.body()
        }
        return null
    }
}