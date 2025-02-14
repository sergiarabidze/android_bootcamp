package com.example.android_bootcamp.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android_bootcamp.helper.Resource
import com.example.android_bootcamp.remote.Place
import com.example.android_bootcamp.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: UserRepository) : ViewModel() {

    private val _places = MutableStateFlow<Resource<List<Place>?>>(Resource.Loading())
    val places: StateFlow<Resource<List<Place>?>> get() = _places

    fun getPlaces() {
        viewModelScope.launch {
            _places.value = Resource.Loading()
            try {
                val response = repository.fetchPlaces()
                if (response.isSuccessful) {
                    _places.value = Resource.Success(response.body())
                }else{
                    _places.value = Resource.Error(response.message())
                }
            } catch (e: Exception) {
                _places.value = Resource.Error(e.message ?: "An error occurred")
            }
        }
    }
}