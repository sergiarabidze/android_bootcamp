package com.example.android_bootcamp

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

 fun Long.formatTimestamp(): String {
    return if (this > 0) {
        val date = Date(this)
        val formatter = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
        formatter.format(date)
    } else {
        "Invalid Date"
    }
}//the function gives us string representation of the time in seconds