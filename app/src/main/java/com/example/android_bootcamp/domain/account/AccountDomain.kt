package com.example.android_bootcamp.domain.account

import com.example.android_bootcamp.domain.helper.CardType
import com.example.android_bootcamp.domain.helper.Valute


data class AccountDomain(
    val id: Int,
    val accountName: String,
    val accountNumber: String,
    val valuteType: Valute,
    val cardType: CardType,
    val balance: Int,
)