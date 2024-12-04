package com.example.hangoutz.data.models

import java.util.UUID

data class Friend(
    val id: UUID,
    val name: String,
    val avatar: String?,
)