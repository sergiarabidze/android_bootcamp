package com.example.android_bootcamp.domain.usecase

import android.net.Uri
import com.example.android_bootcamp.domain.repository.UploadRepository
import com.example.android_bootcamp.domain.resource.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UploadImageUseCase @Inject constructor(
    private val repository: UploadRepository
) {
    suspend operator fun invoke(uri: Uri): Flow<Resource<String>> {
        return repository.uploadImage(uri)
    }
}