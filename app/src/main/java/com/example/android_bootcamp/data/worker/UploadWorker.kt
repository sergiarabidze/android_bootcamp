package com.example.android_bootcamp.data.worker

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.net.Uri
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.android_bootcamp.R
import com.example.android_bootcamp.domain.resource.Resource
import com.example.android_bootcamp.domain.usecase.UploadImageUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class UploadWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted params: WorkerParameters,
    private val uploadImageUseCase: UploadImageUseCase
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        return try {
            createNotificationChannel(context)
            val uriString = inputData.getString(URI_KEY) ?: return failureWithNotification("Invalid URI")
            val imageUri = Uri.parse(uriString)

            uploadImageUseCase(imageUri)
                .collect { resource ->
                    when (resource) {
                        is Resource.Success -> {
                            showNotification("Upload Successful", "File uploaded successfully")
                             Result.success()
                        }
                        is Resource.Failure -> {
                            failureWithNotification(resource.exception)
                        }
                    }
                }
            Result.failure()
        } catch (e: Exception) {
            failureWithNotification(e.message ?: "Unknown error")
        }
    }

    private fun showNotification(title: String, message: String) {
        val notification = NotificationCompat.Builder(context, "work_status_channel")
            .setContentTitle(title)
            .setContentText(message)
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setAutoCancel(true)
            .build()

        context.getSystemService(NotificationManager::class.java)
            ?.notify(NOTIFICATION_ID, notification)
    }

    private fun failureWithNotification(message: String): Result {
        showNotification("Upload Failed", message)
        return Result.failure()
    }

    private fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "work_status_channel",
                "Task Status",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "Shows task completion status"
            }

            context.getSystemService(NotificationManager::class.java)
                ?.createNotificationChannel(channel)
        }
    }

    companion object {
        const val NOTIFICATION_ID = 1001
        const val URI_KEY = "upload_uri"
    }
}
