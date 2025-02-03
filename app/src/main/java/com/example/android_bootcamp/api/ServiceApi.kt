package com.example.android_bootcamp.api

import retrofit2.Response
import retrofit2.http.GET

interface ServiceApi {

    @GET("v3/f3f41821-7434-471f-9baa-ae3dee984e6d")
    suspend fun getUsers(): Response<UserApi>

}