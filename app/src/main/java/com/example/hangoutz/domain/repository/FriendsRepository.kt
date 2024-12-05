package com.example.hangoutz.domain.repository

import com.example.hangoutz.data.models.Friend
import com.example.hangoutz.data.models.ListOfFriends
import retrofit2.Response

interface FriendsRepository {
    suspend fun getFriendsFromUserId(
        id: String,
        startingWith: String = ""
    ): Response<List<ListOfFriends>>
    suspend fun removeFriend(userId: String, friendId: String): Response<Unit>
    suspend fun getNonFriendsFromUserId(
        id: String,
        startingWith: String = ""
    ): Response<List<Friend>>
}