package com.example.android_bootcamp.presentation.features.main


import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.Constraints
import androidx.work.Data
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.android_bootcamp.data.worker.UploadWorker
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val workManager: WorkManager,
    @ApplicationContext private val  context: Context
) : ViewModel() {

    private val _state = MutableStateFlow(MainState())
    val state: StateFlow<MainState> = _state

    fun handleEvent(event: MainEvent) {
        when (event) {
            is MainEvent.ImageSelected -> {
                compressImage(context = context, uri = event.uri)
                _state.value = _state.value.copy(uri = event.uri, isButtonEnabled = true)
            }
            is MainEvent.CameraUriCreated -> {
                _state.value = _state.value.copy(tempCameraUri = event.uri)
            }
            is MainEvent.UploadImage -> {
                _state.value.uri?.let { uri ->
                    scheduleUpload(uri)
                }
            }
        }
    }

    private fun scheduleUpload(imageUri: Uri) {
        val inputData = Data.Builder()
            .putString(UploadWorker.URI_KEY, imageUri.toString())
            .build()
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresCharging(false)
            .setRequiresBatteryNotLow(true)
            .build()

        val uploadWorkRequest = OneTimeWorkRequestBuilder<UploadWorker>()
            .setInputData(inputData)
            .setConstraints(constraints)
            .build()

        workManager.enqueue(uploadWorkRequest)
    }

    fun compressImage(context: Context, uri: Uri) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                context.contentResolver.openInputStream(uri)?.use { inputStream ->
                    val options = BitmapFactory.Options().apply {
                        inSampleSize = 2
                        inJustDecodeBounds = false
                    }

                    val originalBitmap = BitmapFactory.decodeStream(inputStream, null, options)
                        ?: throw IOException("Failed to decode bitmap")

                    val outputStream = ByteArrayOutputStream().apply {
                        if (!originalBitmap.compress(Bitmap.CompressFormat.JPEG, 80, this)) {
                            throw IOException("Failed to compress bitmap")
                        }
                    }

                    val compressedBitmap = BitmapFactory.decodeByteArray(
                        outputStream.toByteArray(),
                        0,
                        outputStream.size()
                    )

                    _state.value = _state.value.copy(
                        bitmap = compressedBitmap,
                        isButtonEnabled = true
                    )
                }
            } catch (e: Exception) {
                _state.value = _state.value.copy(isButtonEnabled = false, error = e.localizedMessage)
            }
        }
    }
    fun clearErrorState(){
        _state.value = _state.value.copy(error = null)
    }
}
