package com.example.android_bootcamp.presentation.register

sealed class RegisterUiEvent {
    data class NavigateToHome(val email: String, val password: String) : RegisterUiEvent()
}