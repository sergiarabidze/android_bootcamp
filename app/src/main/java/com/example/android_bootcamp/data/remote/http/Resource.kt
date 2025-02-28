package com.example.android_bootcamp.data.remote.http

sealed class Resource<out T> {
    data object Loading : Resource<Nothing>()
    data class Success<T>(val data: T) : Resource<T>()
    data class Error(val message: String) : Resource<Nothing>()
    data object Idle : Resource<Nothing>()
}
