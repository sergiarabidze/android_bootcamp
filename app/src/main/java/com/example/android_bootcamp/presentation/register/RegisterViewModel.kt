package com.example.android_bootcamp.presentation.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android_bootcamp.data.remote.httpRequest.Resource
import com.example.android_bootcamp.domain.model.ValidationResult
import com.example.android_bootcamp.domain.usecase.auth.RegisterUserUseCase
import com.example.android_bootcamp.domain.usecase.validation.ValidateEmailAndPasswordUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val validateEmailAndPasswordUseCase: ValidateEmailAndPasswordUseCase,
    private val registerUserUseCase: RegisterUserUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(RegisterState())
    val state: StateFlow<RegisterState> get() = _state

    fun onEvent(event: RegisterEvent) {
        when (event) {
            is RegisterEvent.Submit -> {
                handleRegistration(event.email, event.password, event.confirmPassword)
            }
            is RegisterEvent.ClearErrors -> {
                _state.value = _state.value.copy(
                    validationError = null,
                    registrationError = null
                )
            }
        }
    }

    private fun handleRegistration(email: String, password: String, confirmPassword: String?) {
        viewModelScope.launch {
            val validationResult = validateEmailAndPasswordUseCase(email, password, confirmPassword)
            if (validationResult is ValidationResult.Error) {
                _state.value = _state.value.copy(
                    validationError = validationResult.message,
                    isLoading = false
                )
                return@launch
            }

            _state.value = _state.value.copy(isLoading = true, validationError = null)
            val result = registerUserUseCase(email, password)
            _state.value = when (result) {
                is Resource.Success -> _state.value.copy(
                    isLoading = false,
                    isRegistered = true,
                    registrationError = null
                )
                is Resource.Error -> _state.value.copy(
                    isLoading = false,
                    registrationError = result.message
                )
                else -> _state.value.copy(isLoading = false)
            }
        }
    }
}
