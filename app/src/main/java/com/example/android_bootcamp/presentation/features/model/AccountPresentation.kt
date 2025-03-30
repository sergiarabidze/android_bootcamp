package com.example.android_bootcamp.presentation.features.model

import android.os.Parcelable
import com.example.android_bootcamp.domain.helper.CardType
import com.example.android_bootcamp.domain.helper.Valute
import kotlinx.parcelize.Parcelize

@Parcelize
data class AccountPresentation(
val id: Int = 1,
val accountName: String = "main",
val accountNumber: String = "GE67JG7744036080800003",
val valuteType: Valute = Valute.GEL,
val cardType: CardType = CardType.VISA,
val balance: Int = 678,
): Parcelable

