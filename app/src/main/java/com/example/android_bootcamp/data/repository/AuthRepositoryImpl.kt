package com.example.android_bootcamp.data.repository

import com.example.android_bootcamp.data.remote.api.ServiceApi
import com.example.android_bootcamp.data.remote.api.serializable_classes.Request
import com.example.android_bootcamp.data.remote.api.serializable_classes.ResponseLogin
import com.example.android_bootcamp.data.remote.api.serializable_classes.ResponseRegister
import com.example.android_bootcamp.data.remote.httpRequest.ApiHelper
import com.example.android_bootcamp.data.remote.httpRequest.Resource
import com.example.android_bootcamp.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val apiHelper: ApiHelper,
    private val serviceApi: ServiceApi
) : AuthRepository {

    override suspend fun loginUser(email: String, password: String): Resource<ResponseLogin> {
        val requestLogin = Request(email, password)
        return apiHelper.handleHttpRequest {
            serviceApi.login(requestLogin)
        }
    }

    override suspend fun registerUser(email: String, password: String): Resource<ResponseRegister> {
        val requestRegister = Request(email, password)
        return apiHelper.handleHttpRequest {
            serviceApi.register(requestRegister)
        }
    }
}
