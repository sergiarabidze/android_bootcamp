package com.example.android_bootcamp.view_models

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.android_bootcamp.api.RetrofitInstance
import com.example.android_bootcamp.api.Request
import com.example.android_bootcamp.api.ResponseRegister
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegisterViewModel(application: Application) : AndroidViewModel(application) {

    val registerResponse = MutableLiveData<ResponseRegister>()
    val errorMessage = MutableLiveData<String>()

    fun registerUser(email: String, password: String) {
        val registerRequest = Request(email, password)

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = RetrofitInstance.api.register(registerRequest)
                if (response.isSuccessful){
                    registerResponse.postValue(response.body())
                }else{
                    errorMessage.postValue("Registration failed: ${response.code()}")
                }
            } catch (e: Exception) {
                errorMessage.postValue("Registration failed: ${e.message}")
            }
        }
    }
}
