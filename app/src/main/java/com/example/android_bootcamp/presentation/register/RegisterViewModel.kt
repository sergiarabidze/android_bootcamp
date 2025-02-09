package com.example.android_bootcamp.presentation.register

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.android_bootcamp.remote.api.RetrofitInstance
import com.example.android_bootcamp.remote.api.serializable_classes.Request
import com.example.android_bootcamp.remote.api.serializable_classes.ResponseRegister
import com.example.android_bootcamp.remote.httpRequest.ApiHelper
import com.example.android_bootcamp.remote.httpRequest.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RegisterViewModel(application: Application) : AndroidViewModel(application) {

    private val _registerState = MutableStateFlow<Resource<ResponseRegister>>(Resource.Idle)
    val registerState: StateFlow<Resource<ResponseRegister>> get() = _registerState

    fun registerUser(email: String, password: String) {
        val requestRegister = Request(email, password)

        _registerState.value = Resource.Loading

        viewModelScope.launch {
            val result = ApiHelper.handleHttpRequest {
                RetrofitInstance.api.register(requestRegister)
            }
            _registerState.value = result
        }
    }
}
