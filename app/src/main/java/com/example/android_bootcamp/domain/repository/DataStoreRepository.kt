package com.example.android_bootcamp.domain.repository


import kotlinx.coroutines.flow.Flow

interface DataStoreRepository{
    suspend fun saveSession(token: String, email: String)
    fun readSession(): Flow<Pair<String?, String?>>
    suspend fun clearSession ()
}