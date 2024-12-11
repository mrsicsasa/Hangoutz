package com.example.hangoutz.data.remote

import com.example.hangoutz.BuildConfig
import com.example.hangoutz.data.models.CountOfAcceptedInvitesForEvent
import com.example.hangoutz.data.models.Invite
import com.example.hangoutz.data.models.InviteRequest
import com.example.hangoutz.data.models.UpdateEventStatusDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Query

interface InviteAPI {
    @GET("${BuildConfig.REQUEST_URL}invites?select=*")
    suspend fun getInvites(): Response<List<Invite>>

    @GET("${BuildConfig.REQUEST_URL}invites")
    suspend fun getInviteById(
        @Query("id") id: String
    ): Response<List<Invite>>

    @GET("${BuildConfig.REQUEST_URL}invites")
    suspend fun getInvitesByEventId(
        @Query("event_id") id: String,
        @Query("event_status") status: String = "in.(accepted,invited)"
    ): Response<List<Invite>>

    @GET("${BuildConfig.REQUEST_URL}invites")
    suspend fun getInvitedOrAcceptedByEventId(
        @Query("event_id") id: String,
        @Query("event_status") status: String = "in.(accepted,invited)"
    ): Response<List<Invite>>

    @DELETE("${BuildConfig.REQUEST_URL}invites")
    suspend fun deleteInvite(
        @Query("id") id: String
    ): Response<Unit>

    @GET("${BuildConfig.REQUEST_URL}invites?select=count&event_status=eq.accepted")
    suspend fun getCountOfAcceptedInvitesByEvent(
        @Query("event_id") id: String,
        @Query("event_status") status: String = "eq.accepted"
    ): Response<List<CountOfAcceptedInvitesForEvent>>

    @PATCH("${BuildConfig.REQUEST_URL}invites?")
    suspend fun updateInviteStatus(
        @Query("event_id") eventID: String,
        @Query("user_id") userID: String,
        @Body body: UpdateEventStatusDTO
    ): Response<Unit>

    @DELETE("${BuildConfig.REQUEST_URL}invites")
    suspend fun deleteInviteByEventId(
        @Query("user_id") id: String,
        @Query("event_id") eventId: String
    ): Response<Unit>

    @POST("${BuildConfig.REQUEST_URL}invites")
    suspend fun insertInvite(@Body invite: InviteRequest): Response<Unit>

    @DELETE("${BuildConfig.REQUEST_URL}invites")
    suspend fun deleteAllInvitesByEventId(
        @Query("event_id") id: String,
    ): Response<List<Invite>>
}