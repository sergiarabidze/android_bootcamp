package com.example.android_bootcamp.presentation.main

import android.util.Log
import android.util.Log.d
import androidx.lifecycle.ViewModel
import com.example.android_bootcamp.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: UserRepository) : ViewModel() {


    init {
        d("viewmodel","viewmodellll")
        d("MainViewModel", "ViewModel Created! Repository: $repository")
    }

}