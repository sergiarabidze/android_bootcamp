package com.example.android_bootcamp.presentation.features.main

sealed class ExcavatorEvent {
    data object FetchExcavators : ExcavatorEvent()
    data class Search(val query: String) : ExcavatorEvent()
}
