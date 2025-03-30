package com.example.android_bootcamp.domain.useCase


import com.example.android_bootcamp.data.remote.dto.success.SuccessResponseDto
import com.example.android_bootcamp.domain.repository.ToAccountConfirmationRepository
import com.example.android_bootcamp.domain.resource.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ConfirmToAccountUseCase @Inject constructor(
    private val repository: ToAccountConfirmationRepository
) {
    suspend operator fun invoke(accountNumber: String): Flow<Resource<SuccessResponseDto>> {
        return repository.confirmAccount(accountNumber)
    }
}
