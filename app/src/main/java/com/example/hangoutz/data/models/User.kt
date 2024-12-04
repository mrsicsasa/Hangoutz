package com.example.hangoutz.data.models

import java.util.UUID

data class User(
    val id: UUID,
    val name: String,
    val avatar: String?,
    val email: String,
    val password: String
)