package com.example.android_bootcamp.presentation.register

sealed interface RegisterUiEvent {
    data class ShowError(val message: String) : RegisterUiEvent
    data object NavigateToHome : RegisterUiEvent
}