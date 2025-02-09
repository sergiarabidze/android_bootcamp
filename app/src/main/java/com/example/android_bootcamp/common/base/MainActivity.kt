package com.example.android_bootcamp.common.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.android_bootcamp.R


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }
}