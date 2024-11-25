package com.example.hangoutz.data.remote

import com.example.hangoutz.BuildConfig
import com.example.hangoutz.data.models.Event
import com.example.hangoutz.data.models.EventCardDPO
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
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
    suspend fun getEventsWithAvatar():Response<List<EventCardDPO>>
}