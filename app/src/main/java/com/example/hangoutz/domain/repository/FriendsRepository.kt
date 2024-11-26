package com.example.hangoutz.domain.repository

import com.example.hangoutz.data.models.User
import retrofit2.Response

interface FriendsRepository {
    suspend fun getFriendsFromUserId(id: String): Response<List<User>>
}