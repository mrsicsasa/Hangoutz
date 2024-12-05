package com.example.hangoutz.domain.repository

import com.example.hangoutz.data.models.Event
import com.example.hangoutz.data.models.EventCardDPO
import com.example.hangoutz.data.models.EventsFromInvites
import com.example.hangoutz.data.models.User
import retrofit2.Response
import java.util.UUID

interface EventRepository {
    suspend fun getEvents(): Response<List<Event>>
    suspend fun getEvent(id: UUID): Response<List<Event>>
    suspend fun deleteEvent(id: UUID): Response<Unit>
    suspend fun getEventsWithAvatar(userID: String): Response<List<EventCardDPO>>
    suspend fun getEventsFromInvites(eventStatus: String, userID: String): Response<List<EventsFromInvites>>
    suspend fun patchEventById(id: String, newTitle : String, newPlace : String, newDate: String): Response<Unit>
}