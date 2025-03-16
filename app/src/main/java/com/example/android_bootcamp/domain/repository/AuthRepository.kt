package com.example.android_bootcamp.domain.repository

import com.example.android_bootcamp.data.remote.api.serializable_classes.ResponseLogin
import com.example.android_bootcamp.data.remote.api.serializable_classes.ResponseRegister
import com.example.android_bootcamp.data.remote.httpRequest.Resource
import com.example.android_bootcamp.domain.model.LoginDomain

interface AuthRepository {
    suspend fun loginUser(email: String, password: String): Resource<LoginDomain>
    suspend fun registerUser(email: String, password: String): Resource<ResponseRegister>
}
