package com.example.android_bootcamp.domain.helper

enum class Valute {
    GEL{
        override fun toString(): String {
            return "₾"
        }
    }
    ,USD{
        override fun toString(): String {
            return "$"
        }
    },EUR{
        override fun toString(): String {
            return "€"
        }
    }
}