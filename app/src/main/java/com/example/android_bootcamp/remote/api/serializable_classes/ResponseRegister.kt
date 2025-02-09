package com.example.android_bootcamp.remote.api.serializable_classes

import kotlinx.serialization.Serializable


@Serializable
data class ResponseRegister(
    val id : String,
    val token : String
)