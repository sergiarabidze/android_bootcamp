package com.example.android_bootcamp.presentation.main

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android_bootcamp.local.datastore.dataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val dataStore: DataStore<Preferences>) : ViewModel() {

    fun clearSession(onComplete: () -> Unit) {
        viewModelScope.launch {
           dataStore.edit { preferences ->
                preferences.clear()
            }
            onComplete()
        }
    }
}
