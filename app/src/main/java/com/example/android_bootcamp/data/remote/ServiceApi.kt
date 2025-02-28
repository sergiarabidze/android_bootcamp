package com.example.android_bootcamp.data.remote

import com.example.android_bootcamp.data.remote.dto.LocationDto
import retrofit2.Response
import retrofit2.http.GET

    interface ServiceApi {
        @GET("v3/c4c64996-4ed9-4cbc-8986-43c4990d495a")
        suspend fun getUsers(): Response<List<LocationDto>>
    }