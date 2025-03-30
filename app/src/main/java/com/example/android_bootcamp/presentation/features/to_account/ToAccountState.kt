package com.example.android_bootcamp.presentation.features.to_account

data class ToAccountState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isSuccessful: Boolean = false
)