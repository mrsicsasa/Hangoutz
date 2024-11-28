package com.example.hangoutz.data.repository

import com.example.hangoutz.data.models.AvatarRequest
import com.example.hangoutz.data.models.User
import com.example.hangoutz.data.models.UserRequest
import com.example.hangoutz.data.models.UserUpdateRequest
import com.example.hangoutz.data.remote.UserAPI
import com.example.hangoutz.domain.repository.UserRepository
import okhttp3.MultipartBody
import retrofit2.Response
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(userAPI: UserAPI) : UserRepository {
    private val api: UserAPI = userAPI
    override suspend fun getUserByName(name: String): Response<List<User>> {
        return api.getUserByName(name = "eq.${name}")
    }

    override suspend fun getUserById(id: String): Response<List<User>> {
        return api.getUserById(id = "eq.${id}")
    }

    override suspend fun getUserByEmailAndPassword(
        email: String, password: String
    ): Response<List<User>> {
        return api.getUserByEmailAndPassword(email = "eq.${email}", password = "eq.${password}")
    }

    override suspend fun insertUser(userRequest: UserRequest): Response<Unit> {
        return api.insertUser(userRequest)
    }

    override suspend fun patchUserNameById(id: String, newName: String): Response<Unit> {
        return api.patchUserById(
            id = "eq.${id}", UserUpdateRequest(name = newName)
        )
    }

    override suspend fun patchUserAvatarById(id: String, newAvatar: String): Response<Unit> {
        return api.patchUserAvatarById(
            id = "eq.${id}", AvatarRequest(avatar = newAvatar)
        )
    }

    override suspend fun deleteUserAvatarByName(newAvatar: String): Response<Unit> {
        return api.deleteAvatarByName(
            newAvatar
        )
    }

    override suspend fun postAvatar(
        newAvatar: MultipartBody.Part, avatarName: String
    ): Response<Unit> {
        return api.postAvatar(
            newAvatar, avatarName
        )
    }

}