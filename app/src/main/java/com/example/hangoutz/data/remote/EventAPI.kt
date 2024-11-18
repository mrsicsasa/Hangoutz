package com.example.hangoutz.data.remote

import com.example.hangoutz.BuildConfig
import com.example.hangoutz.data.models.Event
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.UUID

interface EventAPI {
    @GET("${BuildConfig.REQUEST_URL}events?select=*")
    suspend fun getEvents(): Response<List<Event>>

    @GET("${BuildConfig.REQUEST_URL}events")
    suspend fun getEventById(
        @Query("id") id: UUID
    ): Response<List<Event>>
}