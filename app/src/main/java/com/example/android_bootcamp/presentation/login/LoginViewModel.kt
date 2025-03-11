package com.example.android_bootcamp.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android_bootcamp.data.remote.api.serializable_classes.ResponseLogin
import com.example.android_bootcamp.data.remote.httpRequest.Resource
import com.example.android_bootcamp.domain.model.ValidationResult
import com.example.android_bootcamp.domain.usecase.auth.LoginUserUseCase
import com.example.android_bootcamp.domain.usecase.auth.ValidateLoginCredentialsUseCase
import com.example.android_bootcamp.domain.usecase.datastoreusecase.ReadSessionUseCase
import com.example.android_bootcamp.domain.usecase.datastoreusecase.SaveSessionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUserUseCase: LoginUserUseCase,
    private val saveSessionUseCase: SaveSessionUseCase,
    private val readSessionUseCase: ReadSessionUseCase,
    private val validateLoginCredentialsUseCase: ValidateLoginCredentialsUseCase
) : ViewModel() {

    private val _validationState = MutableSharedFlow<ValidationResult>()
    val validationState = _validationState.asSharedFlow()

    private val _loginState = MutableStateFlow<Resource<ResponseLogin>>(Resource.Idle)
    val loginState: StateFlow<Resource<ResponseLogin>> get() = _loginState

    fun loginUser(email: String, password: String, rememberMe: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            val validationResult = validateLoginCredentialsUseCase(email, password)
            _validationState.emit(validationResult)
            if (validationResult is ValidationResult.Success) {
                _loginState.value = Resource.Loading

                val result = loginUserUseCase(email, password)
                _loginState.value = result

                if (result is Resource.Success && rememberMe) {
                    result.data.token.let { saveSession(it, email) }
                }
            }
        }
    }

    fun saveSession(token: String, email: String) {
        viewModelScope.launch(Dispatchers.IO) {
            saveSessionUseCase(token,email)
        }
    }

    fun readSession(): Flow<Pair<String?, String?>> {
        return readSessionUseCase()
    }

}


