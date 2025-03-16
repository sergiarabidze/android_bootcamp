package com.example.android_bootcamp.domain.repository


import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.flow.Flow

interface DataStoreRepository {
    suspend fun <T> save(key: Preferences.Key<T>, value: T)
    fun <T> get(key: Preferences.Key<T>): Flow<T?>
    suspend fun clear()
}