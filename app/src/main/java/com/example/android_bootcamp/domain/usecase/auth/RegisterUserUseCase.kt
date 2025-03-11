package com.example.android_bootcamp.domain.usecase.auth

import com.example.android_bootcamp.data.remote.httpRequest.Resource
import com.example.android_bootcamp.data.remote.api.serializable_classes.ResponseRegister
import com.example.android_bootcamp.domain.repository.AuthRepository
import javax.inject.Inject

class RegisterUserUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(email: String, password: String): Resource<ResponseRegister> {

        return authRepository.registerUser(email, password)

    }
}
