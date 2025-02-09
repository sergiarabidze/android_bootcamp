package com.example.android_bootcamp.presentation.login

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.android_bootcamp.remote.api.RetrofitInstance
import com.example.android_bootcamp.remote.api.serializable_classes.Request
import com.example.android_bootcamp.remote.api.serializable_classes.ResponseLogin
import com.example.android_bootcamp.remote.httpRequest.ApiHelper
import com.example.android_bootcamp.remote.httpRequest.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    private val _loginState = MutableStateFlow<Resource<ResponseLogin>>(Resource.Idle)
    val loginState: StateFlow<Resource<ResponseLogin>> get() = _loginState

    fun loginUser(email: String, password: String) {
        val requestLogin = Request(email, password)

        _loginState.value = Resource.Loading

        viewModelScope.launch {
            val result = ApiHelper.handleHttpRequest {
                RetrofitInstance.api.login(requestLogin)
            }

            _loginState.value = result
        }
    }
}
