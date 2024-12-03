package com.example.hangoutz.domain.repository

import com.example.hangoutz.data.models.ListOfFriends
import retrofit2.Response

interface FriendsRepository {
    suspend fun getFriendsFromUserId(id: String): Response<List<ListOfFriends>>
    suspend fun removeFriend(userId: String, friendId: String): Response<Unit>
}