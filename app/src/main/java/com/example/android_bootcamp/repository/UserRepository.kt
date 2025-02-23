package com.example.android_bootcamp.repository

import com.example.android_bootcamp.helper.resource.Resource
import com.example.android_bootcamp.helper.data_classes.Post
import com.example.android_bootcamp.helper.data_classes.Story

interface UserRepository {
     suspend fun fetchStories(): Resource<List<Story>>
     suspend fun fetchPosts(): Resource<List<Post>>

}
