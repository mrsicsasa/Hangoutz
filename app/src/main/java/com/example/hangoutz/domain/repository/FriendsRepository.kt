package com.example.hangoutz.domain.repository

import com.example.hangoutz.data.models.Friend
import com.example.hangoutz.data.models.ListOfFriends
import retrofit2.Response
import java.util.UUID

interface FriendsRepository {
    suspend fun getFriendsFromUserId(
        id: String,
        startingWith: String = ""
    ): Response<List<ListOfFriends>>

    suspend fun removeFriend(
        userId: String,
        friendId: String
    ): Response<Unit>

    suspend fun getNonFriendsFromUserId(
        id: String,
        startingWith: String = "",
        friendsId: String
    ): Response<List<Friend>>

    suspend fun addFriend(
        userId: UUID,
        friendId: UUID
    ): Response<Unit>
}