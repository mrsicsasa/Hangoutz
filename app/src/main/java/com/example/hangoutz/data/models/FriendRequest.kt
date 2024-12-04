package com.example.hangoutz.data.models

import java.util.UUID

data class FriendRequest(
    val user_id: UUID,
    val friend_id: UUID
)
