package com.example.android_bootcamp.view_models

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.android_bootcamp.api.Request
import com.example.android_bootcamp.api.ResponseLogin
import com.example.android_bootcamp.api.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel(application: Application) : AndroidViewModel(application) {
    val loginResponse = MutableLiveData<ResponseLogin>()//variable for response token
    val errorMessage = MutableLiveData<String>()//variable for error message

    fun loginUser(email: String, password: String) {
        val requestLogin = Request(email, password)

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = RetrofitInstance.api.login(requestLogin)
                if (response.isSuccessful) {
                    loginResponse.postValue(response.body())

                }else{
                    errorMessage.postValue("Login failed: ${response.code()}")
                }
            } catch (e: Exception) {
                errorMessage.postValue("Login failed: ${e.message}")
            }
        }
    }
}