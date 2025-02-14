package com.example.android_bootcamp.repository

import com.example.android_bootcamp.remote.api.ServiceApi
import com.example.android_bootcamp.remote.api.serializable_classes.Request
import com.example.android_bootcamp.remote.api.serializable_classes.ResponseLogin
import com.example.android_bootcamp.remote.api.serializable_classes.ResponseRegister
import com.example.android_bootcamp.remote.httpRequest.ApiHelper
import com.example.android_bootcamp.remote.httpRequest.Resource
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val apiHelper: ApiHelper,
    private val serviceApi: ServiceApi
) {

    suspend fun loginUser(email: String, password: String): Resource<ResponseLogin> {
        val requestLogin = Request(email, password)
        return apiHelper.handleHttpRequest {
            serviceApi.login(requestLogin)
        }
    }

    suspend fun registerUser(email: String, password: String): Resource<ResponseRegister> {
        val requestRegister = Request(email, password)
        return apiHelper.handleHttpRequest {
            serviceApi.register(requestRegister)
        }
    }
}
