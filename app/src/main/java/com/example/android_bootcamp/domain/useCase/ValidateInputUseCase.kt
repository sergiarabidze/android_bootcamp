package com.example.android_bootcamp.domain.useCase

import android.util.Log.d
import com.example.android_bootcamp.domain.helper.AccountType


class ValidateInputUseCase{
     operator fun invoke(input: String, accountType : AccountType): Boolean {
        return when (accountType) {
            AccountType.ACCOUNT_NUMBER -> input.length == 23
            AccountType.PERSONAL_ID -> input.length == 11 && input.all { it.isDigit() }
            AccountType.PHONE_NUMBER -> input.length == 9 && input.all { it.isDigit() }
        }
    }
}