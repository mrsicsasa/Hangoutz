package com.example.hangoutz.domain.repository

import com.example.hangoutz.data.models.Event
import com.example.hangoutz.data.models.EventCardDPO
import retrofit2.Response
import java.util.UUID

interface EventRepository {
    suspend fun getEvents(): Response<List<Event>>
    suspend fun getEvent(id: UUID): Response<List<Event>>
    suspend fun deleteEvent(id: UUID): Response<Unit>
    suspend fun getEventsWithAvatar(): Response<List<EventCardDPO>>
}