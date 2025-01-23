package com.example.android_bootcamp.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ServiceApi {
    @POST("login")
    suspend fun login(@Body request: Request): Response<ResponseLogin>

    @POST("register")
    suspend fun register(@Body request:Request) : Response<ResponseRegister>
}
//login and registration posts