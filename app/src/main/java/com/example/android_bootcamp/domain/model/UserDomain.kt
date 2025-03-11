package com.example.android_bootcamp.domain.model

data class UserDomain(
    val id: Int,
    val email: String,
    val firstName: String,
    val lastName: String,
    val avatar: String
)