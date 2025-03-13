package com.example.android_bootcamp.presentation.register

data class RegisterState(
    val isLoading: Boolean = false,
    val validationError: String? = null,
    val registrationError: String? = null,
    val isRegistered: Boolean = false
)
