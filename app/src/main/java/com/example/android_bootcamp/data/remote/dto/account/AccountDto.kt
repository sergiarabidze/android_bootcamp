package com.example.android_bootcamp.data.remote.dto.account

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class AccountDto(
    val id: Int,
    @SerializedName("account_name")
    val accountName: String,
    @SerializedName("account_number")
    val accountNumber: String,
    @SerializedName("valute_type")
    val valuteType: String,
    @SerializedName("card_type")
    val cardType: String,
    val balance: Int,
    @SerializedName("card_logo")
    val cardLogo: String?
)

