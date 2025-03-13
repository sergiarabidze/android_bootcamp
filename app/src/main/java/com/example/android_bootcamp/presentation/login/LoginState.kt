package com.example.android_bootcamp.presentation.login

data class LoginState(
    val isLoading: Boolean = false,
    val validationError: String? = null,
    val loginError: String? = null,
    val isLoggedIn: Boolean = false,
    val successFullLogIn: Boolean = false,
    val token: String? = null,
    val email: String? = null
)