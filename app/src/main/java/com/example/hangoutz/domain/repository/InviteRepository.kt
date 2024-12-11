package com.example.hangoutz.domain.repository

import com.example.hangoutz.data.models.CountOfAcceptedInvitesForEvent
import com.example.hangoutz.data.models.Invite
import com.example.hangoutz.data.models.InviteRequest
import com.example.hangoutz.data.models.UpdateEventStatusDTO
import retrofit2.Response
import java.util.UUID

interface InviteRepository {
    suspend fun getInvites(): Response<List<Invite>>
    suspend fun getInvite(id: UUID): Response<List<Invite>>
    suspend fun deleteInvite(id: UUID): Response<Unit>
    suspend fun getInvitesByEventId(id: UUID): Response<List<Invite>>
    suspend fun getInvitedOrAcceptedByEventId(id: UUID): Response<List<Invite>>
    suspend fun getCountOfAcceptedInvitesByEvent(id: UUID): Response<List<CountOfAcceptedInvitesForEvent>>
    suspend fun updateInviteStatus(userId:String, eventId: UUID, body: UpdateEventStatusDTO): Response<Unit>
    suspend fun deleteInviteByEventId(id: UUID, eventId: UUID): Response<Unit>
    suspend fun insertInvite(inviteRequest: InviteRequest): Response<Unit>
}