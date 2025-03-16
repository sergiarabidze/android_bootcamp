package com.example.android_bootcamp.domain.usecase.auth

import com.example.android_bootcamp.data.remote.httpRequest.Resource
import com.example.android_bootcamp.domain.model.LoginDomain
import com.example.android_bootcamp.domain.repository.AuthRepository
import javax.inject.Inject

class LoginUserUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(email: String, password: String): Resource<LoginDomain> {
        return  authRepository.loginUser(email, password)

    }
}
