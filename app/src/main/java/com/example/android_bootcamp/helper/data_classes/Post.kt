package com.example.android_bootcamp.helper.data_classes


data class Post(
    val comments: Int,
    val id: Int,
    val images: List<String>,
    val likes: Int,
    val owner: Owner,
    val shareContent: String,
    val title: String
)