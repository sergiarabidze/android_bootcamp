package com.example.android_bootcamp.data.remote.service

import com.example.android_bootcamp.data.remote.dto.account.AccountDto
import retrofit2.Response
import retrofit2.http.GET

    interface AccountServiceApi {
        @GET("/v3/d689fe3e-6faf-446a-9896-c538de3449fa")
        suspend fun getAccounts(): Response<List<AccountDto>>
    }