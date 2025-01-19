package com.example.android_bootcamp.view_models

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.android_bootcamp.api.Request
import com.example.android_bootcamp.api.ResponseLogin
import com.example.android_bootcamp.api.RetrofitInstance
import kotlinx.coroutines.launch

class LoginViewModel(application: Application) : AndroidViewModel(application) {
    val loginResponse = MutableLiveData<ResponseLogin>()//variable for response token
    val errorMessage = MutableLiveData<String>()//variable for error message

    fun loginUser(email: String, password: String) {
        val requestLogin = Request(email, password)

        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.login(requestLogin)
                loginResponse.postValue(response)
            } catch (e: Exception) {
                errorMessage.postValue("Login failed: ${e.message}")
            }
        }
    }
}