package com.example.android_bootcamp.presentation.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android_bootcamp.data.remote.api.serializable_classes.ResponseRegister
import com.example.android_bootcamp.data.remote.httpRequest.Resource
import com.example.android_bootcamp.domain.model.ValidationResult
import com.example.android_bootcamp.domain.usecase.auth.RegisterUserUseCase
import com.example.android_bootcamp.domain.usecase.validation.ValidateEmailAndPasswordUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel@Inject constructor(
    private val validateEmailAndPasswordUseCase: ValidateEmailAndPasswordUseCase,
    private val registerUserUseCase: RegisterUserUseCase
) : ViewModel() {

    private val _validationState = MutableSharedFlow<ValidationResult>()
    val validationState = _validationState.asSharedFlow()

    private val _registerState = MutableStateFlow<Resource<ResponseRegister>>(Resource.Idle)
    val registerState: StateFlow<Resource<ResponseRegister>> get() = _registerState

    fun validateCredentials(email: String, password: String, confirmPassword: String?) {
        viewModelScope.launch {
            val result = validateEmailAndPasswordUseCase(email, password, confirmPassword)
            _validationState.emit(result)
            if (result is ValidationResult.Success) {
                registerUser(email, password)
            }
        }
    }

    private fun registerUser(email: String, password: String) {
        _registerState.value = Resource.Loading
        viewModelScope.launch {
            val result = registerUserUseCase(email, password)
            _registerState.value = result
        }
    }
}
