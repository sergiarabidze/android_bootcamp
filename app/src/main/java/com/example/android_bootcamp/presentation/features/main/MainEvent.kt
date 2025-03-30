package com.example.android_bootcamp.presentation.features.main

import com.example.android_bootcamp.presentation.features.model.AccountPresentation

sealed class MainEvent {
    data class AddFromAccount(val account: AccountPresentation) : MainEvent()
    data class AddToAccount(val account: AccountPresentation) : MainEvent()
    data class UpdateSellFirst(val amount: String) : MainEvent()
    data class UpdateSellSecond(val amount: String) : MainEvent()
}
