package com.example.android_bootcamp.presentation.exstension

import android.widget.EditText

fun EditText.setTextIfDifferent(newText: String) {
    if (this.text.toString() != newText) {
        this.setText(newText)
    }
}