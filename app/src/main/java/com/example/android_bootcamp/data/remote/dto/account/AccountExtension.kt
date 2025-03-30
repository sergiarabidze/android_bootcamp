package com.example.android_bootcamp.data.remote.dto.account

import android.util.Log.d
import com.example.android_bootcamp.data.remote.dto.mapper.toCardType
import com.example.android_bootcamp.data.remote.dto.mapper.toValute
import com.example.android_bootcamp.domain.account.AccountDomain

fun  AccountDto.toDomain():AccountDomain{
    d("AccountDto","$this")
    return AccountDomain(
        id = id,
        accountName = accountName,
        accountNumber = accountNumber,
        valuteType = valuteType.toValute(),
        cardType = cardType.toCardType(),
        balance = balance
    )
}
