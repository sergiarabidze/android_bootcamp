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

    private val _uiState = MutableStateFlow(RegisterState())
    val uiState: StateFlow<RegisterState> get() = _uiState

    private val _eventFlow = MutableStateFlow<RegisterUiEvent?>(null)
    val eventFlow: StateFlow<RegisterUiEvent?> get() = _eventFlow

    fun onEvent(event: RegisterEvent) {
        when (event) {
            is RegisterEvent.Submit -> registerUser(event.email, event.password, event.confirmPassword)

        }
    }

    private fun registerUser(email: String, password: String, confirmPassword: String?) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)

            val validationResult = validateEmailAndPasswordUseCase(email, password, confirmPassword)
            if (validationResult is ValidationResult.Error) {
                _uiState.value = _uiState.value.copy(validationError = validationResult.message , isLoading = false)
                _eventFlow.value = RegisterUiEvent.ShowError(validationResult.message)
                return@launch
            }

            when (val result = registerUserUseCase(email, password)) {
                is Resource.Success -> {
                    _uiState.value = _uiState.value.copy(isRegistered = true)
                    _eventFlow.value = RegisterUiEvent.NavigateToHome
                }
                is Resource.Error -> {
                    _uiState.value = _uiState.value.copy(registrationError = result.message , isLoading = false)
                    _eventFlow.value = RegisterUiEvent.ShowError(result.message)
                }

                Resource.Idle ->{

                }

                Resource.Loading ->{
                    _uiState.value = _uiState.value.copy(isLoading = true)
                }
            }
        }
    }
}

