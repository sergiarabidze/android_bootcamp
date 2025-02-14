package com.example.android_bootcamp.common.base

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApplication :Application(){
    override fun onCreate() {
        super.onCreate()
    }
}