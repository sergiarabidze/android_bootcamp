package com.example.android_bootcamp

import com.squareup.moshi.Json

data class Chat(
    val id: Int,
    val image: String?,
    val owner: String,
    val last_message: String,
    val last_active: String,
    val unread_messages: Int,
    val is_typing: Boolean,
    @Json(name = "last_message_type") val lastMessageType: Type
)

enum class Type {
    @Json(name = "text") TEXT,
    @Json(name = "voice") VOICE,
    @Json(name = "file") FILE
}
