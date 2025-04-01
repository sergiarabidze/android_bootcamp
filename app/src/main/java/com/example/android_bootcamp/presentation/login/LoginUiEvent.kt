package com.example.android_bootcamp.presentation.login


sealed class LoginUiEvent {
    data class NavigateToHome(val email: String) : LoginUiEvent()
}