package com.example.android_bootcamp.presentation.features.main

import android.util.Log.d
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android_bootcamp.data.remote.dto.LocationDto
import com.example.android_bootcamp.data.remote.http.Resource
import com.example.android_bootcamp.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: UserRepository
) : ViewModel() {
    private val _locations = MutableStateFlow<Resource<List<LocationDto>>>(Resource.Idle)
    val locations: StateFlow<Resource<List<LocationDto>>> = _locations
    init {
    fetchLocations()
    }
    private fun fetchLocations(){
        viewModelScope.launch {
            _locations.value = repository.fetchLocations()
            d("fetchLocations", "fetchLocations: ${_locations.value}")
        }
    }
}