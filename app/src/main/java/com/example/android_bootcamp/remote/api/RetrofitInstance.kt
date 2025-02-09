package com.example.android_bootcamp.remote.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    val api : ServiceApi by lazy {
        Retrofit.Builder()
            .baseUrl("https://reqres.in/api/")//url
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(ServiceApi::class.java)
    }
}