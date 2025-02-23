package com.example.android_bootcamp.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android_bootcamp.helper.resource.Resource
import com.example.android_bootcamp.helper.data_classes.Post
import com.example.android_bootcamp.helper.data_classes.Story
import com.example.android_bootcamp.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
   private val repository: UserRepository
) : ViewModel() {
    private val _storiesState = MutableStateFlow<Resource<List<Story>>>(Resource.Idle)
    val storiesState: StateFlow<Resource<List<Story>>> = _storiesState.asStateFlow()

    private val _postsState = MutableStateFlow<Resource<List<Post>>>(Resource.Idle)
    val postsState: StateFlow<Resource<List<Post>>> = _postsState.asStateFlow()

    init {
        fetchStories()
        fetchPosts()
    }

    private fun fetchStories() {
        viewModelScope.launch {
            _storiesState.value = Resource.Loading
            _storiesState.value = repository.fetchStories()
        }
    }

    private fun fetchPosts() {
        viewModelScope.launch {
            _postsState.value = Resource.Loading
            _postsState.value = repository.fetchPosts()
        }
    }

}