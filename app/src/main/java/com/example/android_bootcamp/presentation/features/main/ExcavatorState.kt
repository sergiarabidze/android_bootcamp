package com.example.android_bootcamp.presentation.features.main

import com.example.android_bootcamp.presentation.model.ExcavatorPresentation

data class ExcavatorState(
    val isLoading: Boolean = false,
    val excavators: List<ExcavatorPresentation> = emptyList(),
    val errorMessage: String? = null,
    val searchQuery: String = ""
)
