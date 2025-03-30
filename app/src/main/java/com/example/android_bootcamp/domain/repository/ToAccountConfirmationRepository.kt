package com.example.android_bootcamp.domain.repository


import com.example.android_bootcamp.data.remote.dto.success.SuccessResponseDto
import com.example.android_bootcamp.domain.resource.Resource
import kotlinx.coroutines.flow.Flow

interface ToAccountConfirmationRepository {
    suspend fun confirmAccount(accountNumber: String): Flow<Resource<SuccessResponseDto>>
}
