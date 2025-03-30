package com.example.android_bootcamp.presentation.features.to_account

import android.util.Log.d
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android_bootcamp.domain.resource.Resource
import com.example.android_bootcamp.domain.useCase.ConfirmToAccountUseCase
import com.example.android_bootcamp.domain.useCase.ValidateInputUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ToAccountViewModel @Inject constructor(
    private val confirmToAccountUseCase: ConfirmToAccountUseCase,
    private val  validateInputUseCase: ValidateInputUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(ToAccountState())
    val state = _state.asStateFlow()

    fun onEvent(event: ToAccountEvent) {
        when (event) {
            is ToAccountEvent.ConfirmToAccount -> {
                if (validateInputUseCase(event.accountNumber, event.type)){
                    viewModelScope.launch {
                        confirmToAccountUseCase(event.accountNumber).collect {
                            when (it) {
                                is Resource.Error ->{
                                    _state.value = _state.value.copy(
                                        errorMessage = it.message,
                                        isLoading = false

                                    )
                                }
                                Resource.Idle -> {
                                }
                                Resource.Loading ->{
                                    _state.value = _state.value.copy(
                                        isLoading = true
                                    )
                                }
                                is Resource.Success ->{
                                    d("success", it.data.status)
                                    _state.value = _state.value.copy(
                                        isLoading = false,
                                        isSuccessful = true
                                    )
                                }
                            }
                        }
                    }
                }else{
                    _state.value = _state.value.copy(
                        errorMessage = "Invalid input"
                    )
                }

            }
        }
    }


    fun clearError(){
        _state.value = _state.value.copy(
            errorMessage = null
        )
    }
}