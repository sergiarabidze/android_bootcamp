package com.example.android_bootcamp.repository


import com.example.android_bootcamp.helper.resource.Resource
import com.example.android_bootcamp.data.remote.ServiceApi

import com.example.android_bootcamp.helper.data_classes.Post
import com.example.android_bootcamp.helper.data_classes.Story
import com.example.android_bootcamp.helper.extension.toPost
import com.example.android_bootcamp.helper.extension.toStory
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val serviceApi: ServiceApi
) : UserRepository {

    override suspend fun fetchPosts(): Resource<List<Post>> {
        return try {
            val response = serviceApi.getPosts()
            if (response.isSuccessful && response.body() != null) {
                val posts = response.body()!!.map { it.toPost() }
                Resource.Success(posts)
            } else {
                Resource.Error("Failed to fetch posts: ${response.message()}")
            }
        } catch (e: Exception) {
            Resource.Error("Error fetching posts: ${e.message}")
        }
    }

    override suspend fun fetchStories(): Resource<List<Story>> {
        return try {
            val response = serviceApi.getStories()
            if (response.isSuccessful && response.body() != null) {
                val stories = response.body()!!.map { it.toStory() }
                Resource.Success(stories)
            } else {
                Resource.Error("Failed to fetch stories: ${response.message()}")
            }
        } catch (e: Exception) {
            Resource.Error("Error fetching stories: ${e.message}")
        }
    }
}

