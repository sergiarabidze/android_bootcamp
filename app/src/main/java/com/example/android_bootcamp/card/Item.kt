package com.example.android_bootcamp.card

import java.util.UUID

class ItemModel(val id:String = UUID.randomUUID().toString(), val type: Type = Type.MASTERCARD, val number: String, val holderName: String, val validThru: String, val cvv: String)


enum class Type{
    MASTERCARD,
    VISA
}//types for different cards