package com.example.android_bootcamp


import androidx.lifecycle.ViewModel

class VIewModel : ViewModel() {

    private val _fields: MutableMap<Int, Triple<String, Boolean, String>> = mutableMapOf()
    val fields: Map<Int, Triple<String, Boolean, String>> by ::_fields

    fun addField(id: Int, value: Triple<String, Boolean, String>) {
        _fields[id] = value
    }

}