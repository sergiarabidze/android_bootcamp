package com.example.android_bootcamp.data.remote

import android.content.Context
import android.net.Uri
import com.example.android_bootcamp.domain.repository.UploadRepository
import com.example.android_bootcamp.domain.resource.Resource
import com.google.firebase.Firebase
import com.google.firebase.storage.storage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.util.UUID

class UploadRepositoryImpl(private val context: Context) : UploadRepository {
    private val storage = Firebase.storage.reference

    override suspend fun uploadImage(uri: Uri): Flow<Resource<String>> = flow{
        try {
            val inputStream = context.contentResolver.openInputStream(uri)
            inputStream.use { stream ->
                val fileName = UUID.randomUUID().toString() + ".jpg"
                val photoRef = storage.child("photos/$fileName")

                if (stream != null) {
                    photoRef.putStream(stream).await()
                }else{
                    emit(Resource.Failure("Error uploading image"))
                }
                val downloadUrl = photoRef.downloadUrl.await()

                emit(Resource.Success(downloadUrl.toString()))
            }
        } catch (e: Exception) {
            emit(Resource.Failure(e.message ?: "Unknown error"))

        }

    }

}