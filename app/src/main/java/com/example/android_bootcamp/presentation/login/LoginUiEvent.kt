package com.example.android_bootcamp.presentation.login


sealed interface LoginUiEvent {
    data class NavigateToHome(val email: String, val token: String?) : LoginUiEvent
    data class ShowError(val message: String) : LoginUiEvent

}