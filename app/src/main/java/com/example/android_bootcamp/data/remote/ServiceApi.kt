package com.example.android_bootcamp.data.remote

import com.example.android_bootcamp.data.dto.ExcavatorDto
import retrofit2.Response
import retrofit2.http.GET

    interface ServiceApi {
        @GET("v3/499e0ffd-db69-4955-8d86-86ee60755b9c")
        suspend fun getExcavators(): Response<ExcavatorDto>
    }