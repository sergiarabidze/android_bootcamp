package com.example.android_bootcamp.api

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

data class UserApi(
    val additional_data: Any,
    val options: Any,
    val permissions: List<String?>,
    val status: Boolean,
    val users: List<UserA>
)

@Serializable
data class UserA(
    val id: Int,
    val avatar: String?,
    @SerializedName("first_name")
    val firstName: String,
    @SerializedName("last_name")
    val lastName: String,
    val about: String? = null,
    @SerializedName("activation_status")
    val activationStatus: Double
)
