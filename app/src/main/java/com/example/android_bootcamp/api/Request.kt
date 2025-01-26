package com.example.android_bootcamp.api

import kotlinx.serialization.Serializable

@Serializable
data class Request(
    val email: String,
    val password: String
)

@Serializable
data class ResponseLogin(
    val token : String
)

@Serializable
data class ResponseRegister(
    val id : String,
    val token : String
)


//data classes for login and register response and request
