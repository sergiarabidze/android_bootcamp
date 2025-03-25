package com.example.android_bootcamp.presentation.features.main

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream

class MainViewModel : ViewModel() {
    private val _compressedBitmap = MutableLiveData<Bitmap>()
    val compressedBitmap: LiveData<Bitmap> = _compressedBitmap

    fun compressImage(context: Context, uri: Uri) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val inputStream = context.contentResolver.openInputStream(uri)
                val options = BitmapFactory.Options().apply { inSampleSize = 2 }
                val originalBitmap = BitmapFactory.decodeStream(inputStream, null, options)

                val outputStream = ByteArrayOutputStream()
                originalBitmap?.compress(Bitmap.CompressFormat.JPEG, 80, outputStream)

                _compressedBitmap.postValue(
                    BitmapFactory.decodeByteArray(
                        outputStream.toByteArray(),
                        0,
                        outputStream.size()
                    )
                )
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}