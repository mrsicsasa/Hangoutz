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
import com.example.hangoutz.utils.connectivityObserver.AndroidConnectivityObserver
import com.example.hangoutz.utils.connectivityObserver.ConnectivityObserver
import com.example.hangoutz.utils.toDate
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class SplashScreenViewModel @Inject constructor(
    private val eventRepository: EventRepository,
    private val inviteRepository: InviteRepository,
    @ApplicationContext private val context: Context
) : ViewModel() {
    val isConnected = AndroidConnectivityObserver(context)
        .isConnected
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            false
        )

    @RequiresApi(Build.VERSION_CODES.O)
    fun deleteEventsFromPast() {
        viewModelScope.launch {
            try {
                val events = getEvents()
                events?.filter { it.date.toDate() < LocalDateTime.now() }?.forEach { it ->
                    val invitesForEvent = getInvites(it.id)
                    invitesForEvent?.forEach {
                        inviteRepository.deleteInvite(it.id)
                    }
                }
            } catch (e: Exception) {
                Log.d("ERROR", e.message.toString())
            }
        }
    }

    fun isUserLoggedIn(context: Context): Boolean {
        return SharedPreferencesManager.getUserId(context = context) != null
    }

    private suspend fun getEvents(): List<Event>? {
        try {
            val response = eventRepository.getEvents()
            if (response.isSuccessful && response.body() != null) {
                return response.body()
            }
        } catch (e: Exception) {
            Log.d("ERROR", e.message.toString())
        }
        return null
    }

    private suspend fun getInvites(id: UUID): List<Invite>? {
        try {
            val response = inviteRepository.getInvitesByEventId(id)
            if (response.isSuccessful && response.body() != null) {
                return response.body()
            }
        } catch (e: Exception) {
            Log.d("ERROR", e.message.toString())
        }
        return null
    }
}