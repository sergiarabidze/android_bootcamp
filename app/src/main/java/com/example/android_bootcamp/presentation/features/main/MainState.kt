package com.example.android_bootcamp.presentation.features.main

import android.graphics.Bitmap
import android.net.Uri

data class MainState(
    val uri: Uri? = null,
    val bitmap: Bitmap? = null,
    val isButtonEnabled: Boolean = false,
    val tempCameraUri: Uri? = null,
    val error: String? = null
)
