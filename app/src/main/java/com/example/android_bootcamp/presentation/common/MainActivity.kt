package com.example.android_bootcamp.presentation.common

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.android_bootcamp.R
import dagger.hilt.android.AndroidEntryPoint

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}