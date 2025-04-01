package com.example.android_bootcamp.presentation.login


data class LoginState(
    val email: String = "",
    val password: String = "",
    val rememberMe: Boolean = false,
    val isButtonEnabled: Boolean = false,
    val isLoading: Boolean = false,
    val token:String  = "",
    val error: String? = null
)