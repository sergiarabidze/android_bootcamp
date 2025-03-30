package com.example.android_bootcamp.presentation.features.accounts

import android.util.Log.d
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android_bootcamp.domain.resource.Resource
import com.example.android_bootcamp.domain.useCase.FetchAccountsUseCase
import com.example.android_bootcamp.presentation.mapper.toPresentation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountsViewModel @Inject constructor(
    private val fetchAccountsUseCase: FetchAccountsUseCase
) : ViewModel() {

    private val _accounts = MutableStateFlow(AccountState())
    val accounts: StateFlow<AccountState> get() = _accounts

    init {
        getAccounts()
    }

    private fun getAccounts() {
        viewModelScope.launch {
            fetchAccountsUseCase().collect { resource ->
                when (resource){
                    is Resource.Error ->{
                        _accounts.value = _accounts.value.copy(
                            error = resource.message,
                            isLoading = false
                        )
                    }
                    Resource.Idle ->{}
                    Resource.Loading ->{
                        _accounts.value = _accounts.value.copy(
                            isLoading = true
                        )
                    }
                    is Resource.Success ->{
                        _accounts.value = _accounts.value.copy(
                            accounts = resource.data.map { it.toPresentation() }
                            , isLoading = false
                        )
                        d("AccountState","${_accounts.value}")
                    }
                }
            }
        }
    }

    fun clearError(){
        _accounts.value = _accounts.value.copy(
            error = null
        )
    }
}
