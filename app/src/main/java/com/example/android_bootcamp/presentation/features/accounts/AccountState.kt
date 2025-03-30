package com.example.android_bootcamp.presentation.features.accounts

import com.example.android_bootcamp.presentation.features.model.AccountPresentation

data class AccountState(
    val accounts: List<AccountPresentation> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)