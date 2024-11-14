package com.example.hangoutz.data

import com.example.hangoutz.data.models.Event
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import java.util.UUID

interface EventAPI {
    @GET("rest/v1/events?select=*")
    suspend fun getEvents(): Response<List<Event>>
    @GET("rest/v1/events?id=eq.{id}")
    suspend fun getEventById(
        @Path("id") id: UUID
    ): Response<List<Event>>
}