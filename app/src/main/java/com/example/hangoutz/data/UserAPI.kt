package com.example.hangoutz.data

import com.example.hangoutz.data.models.User
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers

interface UserAPI {
    @GET("rest/v1/users?name=eq.Android")
    suspend fun getData(): Response<List<User>>
}