package com.example.android_bootcamp.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android_bootcamp.data.remote.httpRequest.Resource
import com.example.android_bootcamp.domain.model.ValidationResult
import com.example.android_bootcamp.domain.usecase.auth.LoginUserUseCase
import com.example.android_bootcamp.domain.usecase.auth.ValidateLoginCredentialsUseCase
import com.example.android_bootcamp.domain.usecase.datastoreusecase.ReadSessionUseCase
import com.example.android_bootcamp.domain.usecase.datastoreusecase.SaveSessionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
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
    val state: StateFlow<LoginState> = _state.asStateFlow()

    private val _eventFlow = MutableSharedFlow<LoginUiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        loadSavedSession()
    }

    private fun loadSavedSession() {
        viewModelScope.launch {
            readSessionUseCase().collect { (token, email) ->
                if (!token.isNullOrEmpty() && !email.isNullOrEmpty()) {
                    updateState {
                        it.copy(
                            token = token,
                            email = email,
                            rememberMe = true
                        )
                    }
                    _eventFlow.emit(LoginUiEvent.NavigateToHome(email))
                }
            }
        }
    }

    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.Submit -> handleLogin(event.email, event.password)
            is LoginEvent.UpdateRememberMe -> updateRememberMe(event.rememberMe)
            is LoginEvent.EmailChanged -> onEmailChanged(event.email)
            is LoginEvent.PasswordChanged -> onPasswordChanged(event.password)
        }
    }

    private fun validateFields(email: String, password: String) {
        viewModelScope.launch {
            when (val result = validateLoginCredentialsUseCase(email, password)) {
                is ValidationResult.Success -> updateState {
                    it.copy(isButtonEnabled = true, error = null)
                }
                is ValidationResult.Error -> updateState {
                    it.copy(isButtonEnabled = false, error = result.message)
                }
            }
        }
    }

    private fun handleLogin(email: String, password: String) {
        viewModelScope.launch {
            updateState { it.copy(isLoading = true) }

            when (val result = loginUserUseCase(email, password)) {
                is Resource.Success -> {
                    updateState {
                        it.copy(
                            isLoading = false,
                            token = result.data.token,
                            email = email,
                            rememberMe = _state.value.rememberMe
                        )
                    }

                    _eventFlow.emit(LoginUiEvent.NavigateToHome(email))
                    if (_state.value.rememberMe) {
                        saveSessionUseCase(result.data.token, email)
                    }
                }

                is Resource.Error -> {
                    updateState { it.copy(isLoading = false, error = it.error) }
                }

                Resource.Loading -> updateState { it.copy(isLoading = true) }

                Resource.Idle -> {}

            }
        }
    }

    private fun updateRememberMe(rememberMe: Boolean) {
        updateState { it.copy(rememberMe = rememberMe) }
    }


    private fun onEmailChanged(newEmail: String) {
        updateState { it.copy(email = newEmail) }
        validateFields(newEmail, state.value.password)
    }

    private fun onPasswordChanged(newPassword: String) {
        updateState { it.copy(password = newPassword) }
        validateFields(state.value.email, newPassword)
    }

    private fun updateState(transform: (LoginState) -> LoginState) {
        _state.value = transform(_state.value)
    }

    fun clearError(){
        updateState { it.copy(error = null) }
    }
}
