package com.example.android_bootcamp.data.remote

import com.example.android_bootcamp.data.remote.dto.PostDto
import com.example.android_bootcamp.data.remote.dto.StoryDto
import retrofit2.Response
import retrofit2.http.GET

    interface ServiceApi {
        @GET("v3/00a18030-a8c7-47c4-b0c5-8bff92a29ebf")
        suspend fun getStories(): Response<List<StoryDto>>

        @GET("v3/1ba8b612-8391-41e5-8560-98e4a48decc7")
        suspend fun getPosts(): Response<List<PostDto>>

    }