package com.example.hangoutz.data

import com.example.hangoutz.data.models.User
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface UserAPI {
    @GET("rest/v1/users?name=eq.{name}")
    suspend fun getUserByName(@Path("name") name : String): Response<List<User>>

}