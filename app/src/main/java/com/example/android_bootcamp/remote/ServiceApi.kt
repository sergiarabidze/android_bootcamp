package com.example.android_bootcamp.remote

import retrofit2.Response
import retrofit2.http.GET

    interface ServiceApi {
        @GET("v3/6dffd14a-836f-4566-b024-bd41ace3a874")
        suspend fun getPlaces(): Response<List<Place>>
    }