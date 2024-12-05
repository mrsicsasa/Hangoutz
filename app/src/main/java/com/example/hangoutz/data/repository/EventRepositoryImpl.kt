package com.example.hangoutz.data.repository

import com.example.hangoutz.data.models.Event
import com.example.hangoutz.data.models.EventCardDPO
import com.example.hangoutz.data.models.EventRequest
import com.example.hangoutz.data.models.EventsFromInvites
import com.example.hangoutz.data.remote.EventAPI
import com.example.hangoutz.domain.repository.EventRepository
import retrofit2.Response
import java.util.UUID
import javax.inject.Inject

class EventRepositoryImpl @Inject constructor(
    eventAPI: EventAPI
) : EventRepository {
    private val api = eventAPI
    override suspend fun getEvents(): Response<List<Event>> {
        return api.getEvents()
    }

    override suspend fun getEvent(id: UUID): Response<List<Event>> {
        return api.getEventById(id = "eq.${id}")
    }

    override suspend fun deleteEvent(id: UUID): Response<Unit> {
        return api.deleteEvent(id = "eq.${id}")
    }

    override suspend fun getEventsWithAvatar(userID: String): Response<List<EventCardDPO>> {
        return api.getEventsWithAvatar(userId = "eq.$userID")
    }

    override suspend fun getEventsFromInvites(
        eventStatus: String,
        userID: String
    ): Response<List<EventsFromInvites>> {
        return api.getEventsFromInvites(eventStatus = "eq.$eventStatus", id = "eq.$userID")
    }

    override suspend fun patchEventById(
        id: String,
        newTitle: String,
        newPlace: String,
        newDate: String
    ): Response<Unit> {
        return api.patchEventById(
            id = "eq.${id}", EventRequest(title = newTitle, place = newPlace, date = newDate)
        )
    }

}