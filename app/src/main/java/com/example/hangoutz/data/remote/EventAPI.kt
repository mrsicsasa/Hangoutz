package com.example.hangoutz.data.remote

import com.example.hangoutz.BuildConfig
import com.example.hangoutz.data.models.Event
import com.example.hangoutz.data.models.EventCardDPO
import com.example.hangoutz.data.models.EventRequest
import com.example.hangoutz.data.models.EventsFromInvites
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Query


interface EventAPI {
    @GET("${BuildConfig.REQUEST_URL}events?select=*&order=date")
    suspend fun getEvents(): Response<List<Event>>

    @GET("${BuildConfig.REQUEST_URL}events")
    suspend fun getEventById(
        @Query("id") id: String
    ): Response<List<Event>>

    @DELETE("${BuildConfig.REQUEST_URL}events")
    suspend fun deleteEvent(
        @Query("id") id: String
    ): Response<Unit>

    @GET("${BuildConfig.REQUEST_URL}events?select=*,users(avatar)&order=date")
    suspend fun getEventsWithAvatar(
        @Query("owner") userId: String
    ): Response<List<EventCardDPO>>

    @GET("${BuildConfig.REQUEST_URL}invites?select=events(*)")
    suspend fun getEventsFromInvites(
        @Query("event_status") eventStatus: String,
        @Query("user_id") id: String,
        @Query("order") orderBy: String = "events(date)"
    ): Response<List<EventsFromInvites>>

    @PATCH("${BuildConfig.REQUEST_URL}events")
    suspend fun patchEventById(
        @Query("id") id: String, @Body newEventData: EventRequest
    ): Response<Unit>
}