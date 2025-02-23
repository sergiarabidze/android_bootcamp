package com.example.android_bootcamp.data.remote.dto

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class PostDto(
    val comments: Int,
    val id: Int,
    val images: List<String>?,
    val likes: Int,
    val owner: OwnerDto,
    @SerializedName("share_content")
    val shareContent: String,
    val title: String
)