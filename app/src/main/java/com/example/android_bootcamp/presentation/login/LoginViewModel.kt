package com.example.android_bootcamp.presentation.login

import android.util.Log.d
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android_bootcamp.data.remote.httpRequest.Resource
import com.example.android_bootcamp.domain.model.ValidationResult
import com.example.android_bootcamp.domain.usecase.auth.LoginUserUseCase
import com.example.android_bootcamp.domain.usecase.auth.ValidateLoginCredentialsUseCase
import com.example.android_bootcamp.domain.usecase.datastoreusecase.ReadSessionUseCase
import com.example.android_bootcamp.domain.usecase.datastoreusecase.SaveSessionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUserUseCase: LoginUserUseCase,
    private val saveSessionUseCase: SaveSessionUseCase,
    private val readSessionUseCase: ReadSessionUseCase,
    private val validateLoginCredentialsUseCase: ValidateLoginCredentialsUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(LoginState())
    val state: StateFlow<LoginState> get() = _state

    private val _eventFlow = MutableSharedFlow<LoginUiEvent>()
    val eventFlow get() = _eventFlow

    init {
        viewModelScope.launch {
            readSessionUseCase().collectLatest { (savedToken, savedEmail) ->
                if (!savedToken.isNullOrEmpty() && !savedEmail.isNullOrEmpty()) {
                    _state.value = _state.value.copy(
                        isLoggedIn = true,
                        token = savedToken,
                        email = savedEmail
                    )
                    readSessionUseCase()
                    d("raghac error","${readSessionUseCase.invoke().first()}")
                    _eventFlow.emit(LoginUiEvent.NavigateToHome(savedToken,savedEmail))
                }
            }
        }
    }

    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.Submit -> {
                handleLogin(event.email, event.password, event.rememberMe)
            }
        }
    }

    fun validateFields(email: String, password: String) {
        _state.value = _state.value.copy(
            isButtonEnabled = validateLoginCredentialsUseCase(email, password) is ValidationResult.Success
        )
    }

    private fun handleLogin(email: String, password: String, rememberMe: Boolean) {
        viewModelScope.launch {
            val validationResult = validateLoginCredentialsUseCase(email, password)
            if (validationResult is ValidationResult.Error) {
                _state.value = _state.value.copy(
                    validationError = validationResult.message,
                    isLoading = false
                )
                _eventFlow.emit(LoginUiEvent.ShowError(validationResult.message))
                return@launch
            }

            _state.value = _state.value.copy(isLoading = true, validationError = null, loginError = null)
            when (val result = loginUserUseCase(email, password)) {
                is Resource.Success -> {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        isLoggedIn = true,
                        successFullLogIn = true,
                        token = result.data.token,
                        email = email
                    )
                    if (rememberMe) {
                        saveSessionUseCase(result.data.token, email)
                    }
                    _eventFlow.emit(LoginUiEvent.NavigateToHome(result.data.token,email))

                }

                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        loginError = result.message
                    )
                    _eventFlow.emit(LoginUiEvent.ShowError(result.message))
                }
                else -> {
                    _state.value = _state.value.copy(isLoading = false)
                }
            }
        }
    }
    override fun onCleared() {
        super.onCleared()
        _state.value = LoginState()
    }

}

