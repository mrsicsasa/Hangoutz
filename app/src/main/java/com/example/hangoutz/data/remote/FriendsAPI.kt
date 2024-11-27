package com.example.hangoutz.data.remote

import com.example.hangoutz.BuildConfig
import com.example.hangoutz.data.models.ListOfFriends
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface FriendsAPI {
    @GET("${BuildConfig.REQUEST_URL}friends?select=users!friend_id(name,avatar)")
    suspend fun getFriendsFromUserId(
        @Query("user_id") id: String
    ): Response<List<ListOfFriends>>
}