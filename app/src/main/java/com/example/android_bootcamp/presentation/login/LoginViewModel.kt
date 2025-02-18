package com.example.android_bootcamp.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android_bootcamp.remote.api.serializable_classes.ResponseLogin
import com.example.android_bootcamp.remote.httpRequest.Resource
import com.example.android_bootcamp.repository.AuthRepository
import com.example.android_bootcamp.repository.DataStoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val dataStoreRepository: DataStoreRepository
) : ViewModel() {

    private val _loginState = MutableStateFlow<Resource<ResponseLogin>>(Resource.Idle)
    val loginState: StateFlow<Resource<ResponseLogin>> get() = _loginState

    fun loginUser(email: String, password: String,rememberMe : Boolean) {
        _loginState.value = Resource.Loading

        viewModelScope.launch {
            val result = authRepository.loginUser(email, password)
            _loginState.value = result

            if (result is Resource.Success && rememberMe) {
                result.data.token.let { saveSession(it, email) }
            }
        }
    }

    fun saveSession(token: String, email: String) {
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepository.saveSession(token,email)
        }
    }

    fun readSession(): Flow<Pair<String?, String?>> {
        return dataStoreRepository.readSession()
    }
}


