package com.example.android_bootcamp.presentation.login

sealed class LoginEvent {
    data class Submit(
        val email: String,
        val password: String,
        val rememberMe: Boolean
    ) : LoginEvent()
}