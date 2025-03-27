package com.example.android_bootcamp.domain.repository

import android.net.Uri
import com.example.android_bootcamp.domain.resource.Resource
import kotlinx.coroutines.flow.Flow

interface UploadRepository {
    suspend fun uploadImage(uri: Uri): Flow<Resource<String>>
}