package com.example.android_bootcamp.presentation.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android_bootcamp.remote.api.serializable_classes.ResponseRegister
import com.example.android_bootcamp.remote.httpRequest.Resource
import com.example.android_bootcamp.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel@Inject constructor(private val authRepository: AuthRepository) : ViewModel() {

    private val _registerState = MutableStateFlow<Resource<ResponseRegister>>(Resource.Idle)
    val registerState: StateFlow<Resource<ResponseRegister>> get() = _registerState

    fun registerUser(email: String, password: String) {
        _registerState.value = Resource.Loading

        viewModelScope.launch {
            val result = authRepository.registerUser(email, password)
            _registerState.value = result
        }
    }

}
