package com.example.hangoutz.domain.repository

import com.example.hangoutz.data.models.EventCardAvatar
import com.example.hangoutz.data.models.User
import com.example.hangoutz.data.models.UserRequest
import retrofit2.Response

interface UserRepository {
    suspend fun getUserByName(name: String): Response<List<User>>
    suspend fun getUserByEmailAndPassword(email: String, password: String): Response<List<User>>
    suspend fun insertUser(userRequest: UserRequest): Response<Unit>
    suspend fun getUserById(id: String):Response<List<User>>
    suspend fun getUserAvatar(id: String): Response<List<EventCardAvatar>>
    suspend fun getAllUsers(): Response<List<User>>
}