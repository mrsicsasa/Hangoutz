package com.example.hangoutz.data.repository

import com.example.hangoutz.data.models.ListOfFriends
import com.example.hangoutz.data.remote.FriendsAPI
import com.example.hangoutz.domain.repository.FriendsRepository
import retrofit2.Response
import javax.inject.Inject

class FriendsRepositoryImpl @Inject constructor(friendsAPI: FriendsAPI) : FriendsRepository {
    private val api: FriendsAPI = friendsAPI
    override suspend fun getFriendsFromUserId(
        id: String,
        startingWith: String
    ): Response<List<ListOfFriends>> {
        return api.getFriendsFromUserId(id = "eq.$id", startingWith = "ilike.$startingWith*")
    }

    override suspend fun removeFriend(userId: String, friendId: String): Response<Unit> {
        return api.removeFriend(userId = "eq.$userId", friendId = "eq.$friendId")
    }
}