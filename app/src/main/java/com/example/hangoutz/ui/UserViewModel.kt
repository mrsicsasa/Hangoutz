package com.example.hangoutz.ui

import androidx.lifecycle.ViewModel
import com.example.hangoutz.data.models.User
import com.example.hangoutz.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val repository: UserRepository
): ViewModel() {
    suspend fun getUserByName(name: String): Response<List<User>>{
        return repository.getUserByName(name)
    }
}