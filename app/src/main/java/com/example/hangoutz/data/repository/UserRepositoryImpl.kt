package com.example.hangoutz.data.repository

import com.example.hangoutz.data.models.User
import com.example.hangoutz.data.models.UserRequest
import com.example.hangoutz.data.remote.UserAPI
import com.example.hangoutz.domain.repository.UserRepository
import retrofit2.Response
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(userAPI: UserAPI): UserRepository {
    private val api: UserAPI = userAPI
    override suspend fun getUserByName(name: String): Response<List<User>> {
        return api.getUserByName(name = "eq.${name}")
    }

    override suspend fun getUserByEmailAndPassword(email: String, password: String): Response<List<User>> {
        return api.getUserByEmailAndPassword(email = "eq.${email}", password= "eq.${password}")
    }

    override suspend fun insertUser(userRequest: UserRequest): Response<Unit> {
        return  api.insertUser(userRequest)
    }
}