package com.example.android_bootcamp.presentation.features.main

import com.example.android_bootcamp.presentation.features.model.AccountPresentation

data class MainState(
    val fromAccount: AccountPresentation? = null,
    val toAccount: AccountPresentation? = null,
    val course : Int? = null,
    val firstSell : Int? = null,
    val secondSell : Int? = null
)