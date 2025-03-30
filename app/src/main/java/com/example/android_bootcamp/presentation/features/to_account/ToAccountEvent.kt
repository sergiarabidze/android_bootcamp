package com.example.android_bootcamp.presentation.features.to_account

import com.example.android_bootcamp.domain.helper.AccountType

sealed class ToAccountEvent {
    data class ConfirmToAccount(val accountNumber: String, val type: AccountType) : ToAccountEvent()
}