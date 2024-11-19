package com.example.hangoutz.domain.repository

import com.example.hangoutz.data.models.Invite
import retrofit2.Response
import java.util.UUID

interface InviteRepository {
    suspend fun getInvites(): Response<List<Invite>>
    suspend fun getInvite(id: UUID): Response<List<Invite>>
    suspend fun deleteInvite(id: UUID): Response<Unit>
    suspend fun getInvitesByEventId(id: UUID): Response<List<Invite>>
}