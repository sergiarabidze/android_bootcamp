package com.example.android_bootcamp.view_models

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.android_bootcamp.api.Request
import com.example.android_bootcamp.api.ResponseLogin
import com.example.android_bootcamp.api.RetrofitInstance
import com.example.android_bootcamp.resource.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    private val _loginState = MutableStateFlow<Resource<ResponseLogin>>(Resource.Idle) // Mutable state flow for login state (initially loading)>)
    val loginState: StateFlow<Resource<ResponseLogin>> get() = _loginState

    fun loginUser(email: String, password: String) {
        val requestLogin = Request(email, password)

        _loginState.value = Resource.Loading // Emit loading state

        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.login(requestLogin)
                if (response.isSuccessful && response.body() != null) {
                    _loginState.value = Resource.Success(response.body()!!) // Emit success state
                } else {
                    _loginState.value = Resource.Error("Login failed: ${response.code()}") // Emit error state
                }
            } catch (e: Exception) {
                _loginState.value = Resource.Error("Login failed: ${e.message}") // Emit error state
            }
        }
    }
}
