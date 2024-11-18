package com.example.hangoutz.data.remote

import com.example.hangoutz.BuildConfig
import com.example.hangoutz.data.models.Event
import com.example.hangoutz.data.models.Invite
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Query

interface InvitesAPI {
    @GET("${BuildConfig.REQUEST_URL}invites?select=*")
    suspend fun getInvites(): Response<List<Invite>>

    @GET("${BuildConfig.REQUEST_URL}invites")
    suspend fun getInviteById(
        @Query("id") id: String
    ): Response<List<Invite>>

    @GET("${BuildConfig.REQUEST_URL}invites")
    suspend fun getInvitesByEventId(
        @Query("event_id") id: String
    ): Response<List<Invite>>

    @DELETE("${BuildConfig.REQUEST_URL}invites")
    suspend fun deleteInvite(
        @Query("id") id: String
    ): Response<Unit>
}