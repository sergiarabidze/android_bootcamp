package com.example.android_bootcamp.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android_bootcamp.domain.usecase.datastoreusecase.ClearSessionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val clearSessionUseCase: ClearSessionUseCase
) : ViewModel() {

    fun clearSession(onComplete: () -> Unit) {
        viewModelScope.launch {
            clearSessionUseCase
            onComplete()
        }
    }

}
