package com.example.android_bootcamp

fun String.emailValidation():Boolean{
    return android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()
    //in the real life problems we dont have to use loops and regex if we have predefined functions for the same purpose
}