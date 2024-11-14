package com.example.hangoutz.data

import com.example.hangoutz.data.models.User
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface UserAPI {
    @GET("rest/v1/users")
    suspend fun getUserByName(@Query("name") name: String): Response<List<User>>

}