package com.example.android_bootcamp.presentation.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android_bootcamp.data.remote.httpRequest.Resource
import com.example.android_bootcamp.domain.model.ValidationResult
import com.example.android_bootcamp.domain.usecase.auth.RegisterUserUseCase
import com.example.android_bootcamp.domain.usecase.validation.ValidateEmailAndPasswordUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val validateEmailAndPasswordUseCase: ValidateEmailAndPasswordUseCase,
    private val registerUserUseCase: RegisterUserUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(RegisterState())
    val uiState: StateFlow<RegisterState> = _uiState

    private val _eventFlow = MutableSharedFlow<RegisterUiEvent>()
    val eventFlow: SharedFlow<RegisterUiEvent> = _eventFlow

    fun updateEmail(email: String) {
        _uiState.value = _uiState.value.copy(email = email)
    }

    fun updatePassword(password: String) {
        _uiState.value = _uiState.value.copy(password = password)
    }

    fun updateConfirmPassword(confirmPassword: String) {
        _uiState.value = _uiState.value.copy(confirmPassword = confirmPassword)
    }

    fun onSubmit() {
        val currentState = _uiState.value
        registerUser(currentState.email, currentState.password, currentState.confirmPassword)
    }

    private fun registerUser(email: String, password: String, confirmPassword: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)

            val validationResult = validateEmailAndPasswordUseCase(email, password, confirmPassword)
            if (validationResult is ValidationResult.Error) {
                _uiState.value = _uiState.value.copy(
                    validationError = validationResult.message,
                    isLoading = false
                )
                return@launch
            }

            when (val result = registerUserUseCase(email, password)) {
                is Resource.Success -> {
                    _eventFlow.emit(RegisterUiEvent.NavigateToHome(email, password))
                }

                is Resource.Error -> {
                    _uiState.value = _uiState.value.copy(
                        registrationError = result.message,
                        isLoading = false
                    )
                }

                Resource.Idle, Resource.Loading -> {
                }
            }
        }
    }
}
