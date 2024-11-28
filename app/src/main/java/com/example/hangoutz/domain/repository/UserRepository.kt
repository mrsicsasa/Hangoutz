package com.example.hangoutz.domain.repository

import com.example.hangoutz.data.models.User
import com.example.hangoutz.data.models.UserRequest
import okhttp3.MultipartBody
import retrofit2.Response

interface UserRepository {
    suspend fun getUserByName(name: String): Response<List<User>>
    suspend fun getUserByEmailAndPassword(email: String, password: String): Response<List<User>>
    suspend fun insertUser(userRequest: UserRequest): Response<Unit>
    suspend fun getUserById(id: String): Response<List<User>>
    suspend fun patchUserNameById(id: String, newName: String): Response<Unit>
    suspend fun patchUserAvatarById(id: String, newName: String): Response<Unit>
    suspend fun deleteUserAvatarByName(newName: String): Response<Unit>
    suspend fun postAvatar(newAvatar: MultipartBody.Part, avatarName: String): Response<Unit>

}