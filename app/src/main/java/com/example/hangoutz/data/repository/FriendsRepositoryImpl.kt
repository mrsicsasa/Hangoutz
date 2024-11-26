package com.example.hangoutz.data.repository

import com.example.hangoutz.data.models.User
import com.example.hangoutz.data.remote.FriendsAPI
import com.example.hangoutz.domain.repository.FriendsRepository
import retrofit2.Response
import javax.inject.Inject

class FriendsRepositoryImpl @Inject constructor(friendsAPI: FriendsAPI): FriendsRepository {
    private val api: FriendsAPI = friendsAPI
    override suspend fun getFriendsFromUserId(id: String): Response<List<User>> {
        return api.getFriendsFromUserId(id = "eq.$id")
    }
}