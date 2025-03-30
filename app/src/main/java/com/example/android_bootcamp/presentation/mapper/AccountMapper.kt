package com.example.android_bootcamp.presentation.mapper

import com.example.android_bootcamp.domain.account.AccountDomain
import com.example.android_bootcamp.presentation.features.model.AccountPresentation

fun AccountDomain.toPresentation() = AccountPresentation(
    id = id,
    accountName = accountName,
    accountNumber = accountNumber,
    valuteType = valuteType,
    cardType = cardType,
    balance = balance,
)
