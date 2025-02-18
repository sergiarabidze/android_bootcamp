package com.example.android_bootcamp.presentation.main


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android_bootcamp.repository.DataStoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val dataStoreRepository: DataStoreRepository) : ViewModel() {

    fun clearSession(onComplete: () -> Unit) {
        viewModelScope.launch {
            dataStoreRepository.clearSession()
            onComplete()
        }
    }
}
