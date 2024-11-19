package com.example.hangoutz.data.models

import com.google.gson.annotations.SerializedName
import java.util.UUID

data class Invite(
    val id: UUID,
    @SerializedName("events_status")
    val eventStatus: String,
    @SerializedName("user_id")
    val userId: UUID,
    @SerializedName("event_id")
    val eventId: UUID
)
