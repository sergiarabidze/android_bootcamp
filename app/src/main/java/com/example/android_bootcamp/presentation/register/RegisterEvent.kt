package com.example.android_bootcamp.presentation.register

sealed class RegisterEvent {
    data class Submit(
        val email: String,
        val password: String,
        val confirmPassword: String?
    ) : RegisterEvent()
}
