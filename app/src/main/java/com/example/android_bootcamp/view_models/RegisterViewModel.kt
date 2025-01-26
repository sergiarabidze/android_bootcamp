package com.example.android_bootcamp.view_models

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.android_bootcamp.api.RetrofitInstance
import com.example.android_bootcamp.api.Request
import com.example.android_bootcamp.api.ResponseRegister
import com.example.android_bootcamp.resource.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RegisterViewModel(application: Application) : AndroidViewModel(application) {

    private val _registerState = MutableStateFlow<Resource<ResponseRegister>>(Resource.Idle)
    val registerState: StateFlow<Resource<ResponseRegister>> get() = _registerState

    fun registerUser(email: String, password: String) {
        val registerRequest = Request(email, password)
        _registerState.value = Resource.Loading

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = RetrofitInstance.api.register(registerRequest)
                if (response.isSuccessful){
                    _registerState.value = Resource.Success(response.body()!!)
                }else{
                    _registerState.value = Resource.Error("Registration failed: ${response.code()}")
                }
            } catch (e: Exception) {
                _registerState.value = Resource.Error("Registration failed: ${e.message}")
            }
        }
    }
}
