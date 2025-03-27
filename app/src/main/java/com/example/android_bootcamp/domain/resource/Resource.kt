package com.example.android_bootcamp.domain.resource

sealed class Resource<out T> {
    data class Success<out T>(val data: T) : Resource<T>()
    data class Failure(val exception: String) : Resource<String>()
}