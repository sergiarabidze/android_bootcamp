package com.example.android_bootcamp.data.remote.httpRequest

sealed class Resource<out T> {
    data object Loading : Resource<Nothing>()
    data class Success<out T>(val data: T) : Resource<T>()
    data class Error(val message: String) : Resource<Nothing>()
    data object Idle : Resource<Nothing>()
}
fun <T, R> Resource<T>.map(transform: (T) -> R): Resource<R> {
    return when (this) {
        is Resource.Loading -> Resource.Loading
        is Resource.Success -> Resource.Success(transform(this.data))
        is Resource.Error -> Resource.Error(this.message)
        is Resource.Idle -> Resource.Idle
    }
}
