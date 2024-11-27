package com.example.hangoutz.domain.repository

import com.example.hangoutz.data.models.FriendRoot
import retrofit2.Response

interface FriendsRepository {
    suspend fun getFriendsFromUserId(id: String): Response<List<FriendRoot>>
}