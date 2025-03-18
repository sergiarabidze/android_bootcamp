package com.example.android_bootcamp.presentation.features.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android_bootcamp.data.request.Resource
import com.example.android_bootcamp.domain.model.ExcavatorDomain
import com.example.android_bootcamp.domain.usecase.FetchExcavatorsUseCase
import com.example.android_bootcamp.presentation.mapper.toPresentationList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private val fetchExcavatorsUseCase: FetchExcavatorsUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(ExcavatorState())
    val state: StateFlow<ExcavatorState> = _state.asStateFlow()

    private var searchJob: Job? = null

    init {
        onEvent(ExcavatorEvent.FetchExcavators)
    }

    fun onEvent(event: ExcavatorEvent) {
        when (event) {
            is ExcavatorEvent.FetchExcavators -> search("")
            is ExcavatorEvent.Search -> search(event.query)
        }
    }


    private fun search(query: String) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(500)
            fetchExcavatorsUseCase(query)
                .collectLatest { result ->
                    handleSearchResult(result)
                }
        }
    }


    private fun handleSearchResult(result: Resource<List<ExcavatorDomain>>) {
        _state.update { currentState ->
            when (result) {
                is Resource.Loading -> currentState.copy(isLoading = true)
                is Resource.Success -> currentState.copy(
                    isLoading = false,
                    excavators = result.data.map { it.toPresentationList() },
                    errorMessage = null
                )

                is Resource.Error -> currentState.copy(
                    isLoading = false,
                    errorMessage = result.message
                )

            }
        }
    }

    fun clearErrorMessage() {
        _state.update { it.copy(errorMessage = null) }
    }
}

