package com.example.android_bootcamp.view_models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android_bootcamp.api.ApiResponse

import com.example.android_bootcamp.api.RetrofitInstance
import com.example.android_bootcamp.resource.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class UsersViewModel : ViewModel() {
    private val _usersResponse = MutableStateFlow<Resource<ApiResponse>>(Resource.Idle)
    val usersResponse get() = _usersResponse
    fun fetchUsers(page: Int) {
        _usersResponse.value = Resource.Loading
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = RetrofitInstance.api.getUsers(page)
                if (response.isSuccessful){
                    _usersResponse.value = Resource.Success(response.body()!!)
                }else{
                    _usersResponse.value = Resource.Error("Error: ${response.code()}")
                }

            } catch (e: Exception) {
                _usersResponse.value = Resource.Error(e.message ?: "Unknown error")
            }
        }
    }
}
