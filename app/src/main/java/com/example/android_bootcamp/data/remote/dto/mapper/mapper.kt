package com.example.android_bootcamp.data.remote.dto.mapper

import com.example.android_bootcamp.domain.helper.CardType
import com.example.android_bootcamp.domain.helper.Valute

fun String.toValute(): Valute{
    return when(this) {
        "GEL" -> Valute.GEL
        "USD" -> Valute.USD
        else -> Valute.EUR
    }
}

fun String.toCardType(): CardType {
    return when (this) {
        "VISA" -> CardType.VISA
        else -> CardType.MASTERCARD
    }
}