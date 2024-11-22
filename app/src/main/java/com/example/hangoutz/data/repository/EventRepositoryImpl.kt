package com.example.hangoutz.data.repository

import com.example.hangoutz.data.models.Event
import com.example.hangoutz.data.models.EventCardDPO
import com.example.hangoutz.data.remote.EventAPI
import com.example.hangoutz.domain.repository.EventRepository
import retrofit2.Response
import java.util.UUID
import javax.inject.Inject

class EventRepositoryImpl @Inject constructor(eventAPI: EventAPI):EventRepository {
    private val api = eventAPI
    override suspend fun getEvents(): Response<List<Event>> {
       return api.getEvents()
    }

    override suspend fun getEvent(id: UUID): Response<List<Event>> {
       return  api.getEventById(id = "eq.${id}")
    }

    override suspend fun deleteEvent(id: UUID): Response<Unit> {
        return api.deleteEvent(id = "eq.${id}")
    }

    override suspend fun getEventsWithAvatar(): Response<List<EventCardDPO>> {
        return api.getEventsWithAvatar()
    }
}