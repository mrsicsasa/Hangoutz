package com.example.hangoutz.data.models

data class UserRequest(
    val name: String,
    val avatar: String?,
    val email: String,
    val password_hash: String
)
