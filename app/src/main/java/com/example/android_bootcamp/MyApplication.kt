package com.example.android_bootcamp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApplication :Application() {
    override fun onCreate() {
        super.onCreate()
    }
}