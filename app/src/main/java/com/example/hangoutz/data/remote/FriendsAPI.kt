package com.example.hangoutz.data.remote

import com.example.hangoutz.BuildConfig
import com.example.hangoutz.data.models.Friend
import com.example.hangoutz.data.models.FriendId
import com.example.hangoutz.data.models.ListOfFriends
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface FriendsAPI {
    @GET("${BuildConfig.REQUEST_URL}friends?select=users!friend_id(name,avatar)&users=not.is.null")
    suspend fun getFriendsFromUserId(
        @Query("user_id") id: String,
        @Query("users.name") startingWith: String
    ): Response<List<ListOfFriends>>

    @GET("${BuildConfig.REQUEST_URL}friends?select=friend_id")
    suspend fun getFriendIdsFromUserId(
        @Query("user_id") id: String,
    ): Response<List<FriendId>>

    @GET("${BuildConfig.REQUEST_URL}users?select=name,avatar")
    suspend fun getNonFriendsFromUserId(
        @Query("id") id: String,
        @Query("name") startingWith: String
    ): Response<List<Friend>>
}