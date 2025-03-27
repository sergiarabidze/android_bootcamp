package com.example.android_bootcamp.presentation.features.main

import android.net.Uri

sealed class MainEvent {
 data class ImageSelected(val uri: Uri) : MainEvent()
 data class CameraUriCreated(val uri: Uri) : MainEvent()
 data object UploadImage : MainEvent()
}