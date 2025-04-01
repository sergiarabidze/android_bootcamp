package com.example.android_bootcamp.presentation.login

sealed class LoginEvent {
    data class Submit(
        val email: String,
        val password: String,
        val rememberMe: Boolean
    ) : LoginEvent()
    data class UpdateRememberMe(val rememberMe: Boolean) : LoginEvent()
    data class EmailChanged(val email: String) : LoginEvent()
    data class PasswordChanged(val password: String) : LoginEvent()


}