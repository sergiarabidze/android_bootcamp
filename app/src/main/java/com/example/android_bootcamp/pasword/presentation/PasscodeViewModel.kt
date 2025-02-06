package com.example.android_bootcamp.pasword.presentation

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class PasscodeViewModel : ViewModel() {

    private val _passcode = MutableStateFlow("")
    val passcode: StateFlow<String> get() = _passcode.asStateFlow()

    private val _passcodeResult = MutableStateFlow<String?>(null)
    val passcodeResult: StateFlow<String?> get() = _passcodeResult.asStateFlow()

    fun addDigit(digit: String) {
        if (_passcode.value.length < 4) {
            _passcode.value += digit
        }
        if (_passcode.value.length == 4) {
            checkPasscode()
        }
    }
    fun removeDigit(){
        if (_passcode.value.isNotEmpty()) {
            _passcode.value = _passcode.value.dropLast(1)
        }
    }

    fun clearPasscode() {
        _passcode.value = ""
        _passcodeResult.value = null
    }

    private fun checkPasscode() {
        val correctPasscode = "0934"
        _passcodeResult.value = if (_passcode.value == correctPasscode) {
            "Success"
        } else {
            "Failure"
        }
    }
}
