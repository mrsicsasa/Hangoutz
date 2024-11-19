package com.example.hangoutz.data.repository

import com.example.hangoutz.data.models.Invite
import com.example.hangoutz.data.remote.InviteAPI
import com.example.hangoutz.domain.repository.InviteRepository
import retrofit2.Response
import java.util.UUID
import javax.inject.Inject

class InviteRepositoryImpl @Inject constructor(invitesAPI: InviteAPI): InviteRepository {
    private val api = invitesAPI
    override suspend fun getInvites(): Response<List<Invite>> {
        return api.getInvites()
    }

    override suspend fun getInvite(id: UUID): Response<List<Invite>> {
        return api.getInviteById(id = "eq.${id}")
    }

    override suspend fun deleteInvite(id: UUID): Response<Unit> {
        return api.deleteInvite(id = "eq.${id}")
    }

    override suspend fun getInvitesByEventId(id: UUID): Response<List<Invite>> {
        return api.getInvitesByEventId(id = "eq.${id}")
    }

}