package com.example.android_bootcamp

fun String.isValidEmail():Boolean{
    return android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()
}//simple builtin function for checking email validity although we could done it with regex its easier