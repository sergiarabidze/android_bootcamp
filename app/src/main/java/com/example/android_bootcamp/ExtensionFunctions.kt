package com.example.android_bootcamp

import android.util.Patterns

fun String.isValidEmail(): Boolean {
    return this.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()//email validity android built-in function
}