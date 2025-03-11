package com.example.android_bootcamp.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.example.android_bootcamp.data.local.datastore.PreferenceKeys
import com.example.android_bootcamp.domain.repository.DataStoreRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DataStoreRepositoryImpl@Inject constructor(private val dataStore : DataStore<Preferences>) : DataStoreRepository{

   override suspend fun saveSession(token: String, email: String) {
            dataStore.edit { preferences ->
                preferences[PreferenceKeys.TOKEN] = token
                preferences[PreferenceKeys.EMAIL] = email
            }
    }
    override fun readSession(): Flow<Pair<String?, String?>> {
        return dataStore.data
            .map { preferences ->
                val token = preferences[PreferenceKeys.TOKEN]
                val email = preferences[PreferenceKeys.EMAIL]
                Pair(token, email)
            }
    }

    override suspend fun clearSession (){
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }
}