package com.example.android_bootcamp.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class StoryDto(
    val cover: String,
    val id: Int,
    val title: String
)