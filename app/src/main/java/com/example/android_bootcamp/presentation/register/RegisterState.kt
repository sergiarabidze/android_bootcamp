package com.example.android_bootcamp.presentation.register

data class RegisterState(
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val isLoading: Boolean = false,
    val validationError: String? = null,
    val registrationError: String? = null
)