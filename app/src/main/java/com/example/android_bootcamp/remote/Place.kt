package com.example.android_bootcamp.remote

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class Place(
    val id: Int,
    val cover: String,
    val price: String,
    val title: String,
    val location: String,
    @SerializedName("reaction_count") val reactionCount: Int,
    val rate: Int?
)