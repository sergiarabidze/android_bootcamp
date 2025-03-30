package com.example.android_bootcamp.data.remote.service

import com.example.android_bootcamp.data.remote.dto.success.SuccessResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ToAccountConfirmationServiceApi {
    @GET("v3/29d002d4-3ccd-4eaa-95eb-a9d1601ce123")
    suspend fun confirmAccount(
        @Query("account_number") accountNumber: String
    ): Response<SuccessResponseDto>
}
