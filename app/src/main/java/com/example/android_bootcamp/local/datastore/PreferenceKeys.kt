package com.example.android_bootcamp.local.datastore

import androidx.datastore.preferences.core.stringPreferencesKey

object PreferenceKeys {
    val EMAIL = stringPreferencesKey("email")
    val TOKEN = stringPreferencesKey("token")
}