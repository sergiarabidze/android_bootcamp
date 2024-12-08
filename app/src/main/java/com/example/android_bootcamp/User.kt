package com.example.android_bootcamp

data class User(val email :String, val fullName : String){
    override fun toString(): String {
        return buildString {
            append("user's fullName: ")
            append(fullName)
            append("\nuser's email: ")
            append(email)
        }
    }
}//data class for each user and we override toString function with custom String builder we could just use String concatenation but as i know its faster and takes less space.
