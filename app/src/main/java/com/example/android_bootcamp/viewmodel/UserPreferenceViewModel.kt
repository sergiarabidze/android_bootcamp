package com.example.android_bootcamp.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.android_bootcamp.UserPreferences
import com.example.android_bootcamp.proto.UserPreferencesManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class UserPreferencesViewModel(context: Context) : ViewModel() {
    private val userPreferencesManager = UserPreferencesManager(context)
    val userPreferencesFlow: Flow<UserPreferences> = userPreferencesManager.userPreferencesFlow
    fun saveUserPreferences(firstName: String, lastName: String, email: String) {
        viewModelScope.launch(Dispatchers.IO){
            userPreferencesManager.saveUserPreferences(firstName, lastName, email)
        }
    }
}

class UserPreferencesViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserPreferencesViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return UserPreferencesViewModel(context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}