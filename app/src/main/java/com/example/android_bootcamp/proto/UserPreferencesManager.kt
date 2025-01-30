package com.example.android_bootcamp.proto

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.dataStore
import com.example.android_bootcamp.UserPreferences

private val Context.userPreferencesDataStore: DataStore<UserPreferences> by dataStore(
    fileName = "user_prefs.pb",
    serializer = UserPreferencesSerializer,
    corruptionHandler = ReplaceFileCorruptionHandler(
        produceNewData = { UserPreferences.getDefaultInstance() }
    )
)

class UserPreferencesManager(private val context: Context) {

    private val dataStore = context.userPreferencesDataStore

    suspend fun saveUserPreferences(firstName: String, lastName: String, email: String) {
        dataStore.updateData { preferences ->
            preferences.toBuilder()
                .setFirstName(firstName)
                .setLastName(lastName)
                .setEmail(email)
                .build()
        }
    }

    val userPreferencesFlow = dataStore.data
}
