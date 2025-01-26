package com.example.android_bootcamp.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ServiceApi {
    @POST("login")
    suspend fun login(@Body request: Request): Response<ResponseLogin>

    @POST("register")
    suspend fun register(@Body request:Request) : Response<ResponseRegister>

    @GET("users")
    suspend fun getUsers(@Query("page") page: Int = 1): Response<ApiResponse>
}