package com.example.android_bootcamp

data class ButtonModel(var id:Int,var type:Type = Type.UNCHECKED)

enum class Type{
    UNCHECKED,
    FIRST,
    SECOND
}