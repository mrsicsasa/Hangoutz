package com.example.hangoutz.data.remote

import com.example.hangoutz.BuildConfig
import com.example.hangoutz.data.models.EventCardAvatar
import com.example.hangoutz.data.models.User
import com.example.hangoutz.data.models.UserRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface UserAPI {
    @GET("${BuildConfig.REQUEST_URL}users")
    suspend fun getUserByName(@Query("name") name: String): Response<List<User>>

    @GET("${BuildConfig.REQUEST_URL}users")
    suspend fun getUserById(@Query("id") id: String): Response<List<User>>

    @GET("${BuildConfig.REQUEST_URL}users")
    suspend fun getUserByEmailAndPassword(
        @Query("email") email: String,
        @Query("password_hash") password: String
    ): Response<List<User>>

    @POST("${BuildConfig.REQUEST_URL}users")
    suspend fun insertUser(@Body user: UserRequest) : Response<Unit>

    @GET("${BuildConfig.REQUEST_URL}users?select=avatar")
    suspend fun getUserAvatar(
        @Query("id") id: String
    ): Response<List<EventCardAvatar>>

    @GET("${BuildConfig.REQUEST_URL}users")
    suspend fun getAllUsers(): Response<List<User>>
}