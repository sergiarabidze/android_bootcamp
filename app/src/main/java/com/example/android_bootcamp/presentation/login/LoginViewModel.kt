package com.example.android_bootcamp.presentation.login

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android_bootcamp.local.datastore.PreferenceKeys
import com.example.android_bootcamp.local.datastore.dataStore
import com.example.android_bootcamp.remote.api.serializable_classes.ResponseLogin
import com.example.android_bootcamp.remote.httpRequest.Resource
import com.example.android_bootcamp.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val dataStore : DataStore<Preferences>
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
            dataStore.edit { preferences ->
                preferences[PreferenceKeys.TOKEN] = token
                preferences[PreferenceKeys.EMAIL] = email
            }
        }
    }

    fun readSession(): Flow<Pair<String?, String?>> {
        return dataStore.data
            .map { preferences ->
                val token = preferences[PreferenceKeys.TOKEN]
                val email = preferences[PreferenceKeys.EMAIL]
                Pair(token, email)
            }
    }
}


