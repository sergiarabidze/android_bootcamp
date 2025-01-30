package com.example.android_bootcamp.extension_function

import android.content.Context
import android.util.Patterns
import android.widget.Toast

fun String.isValidEmail(): Boolean {
    return Patterns.EMAIL_ADDRESS.matcher(this).matches()
}
fun Context.showToast(text :String){
    Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
}
