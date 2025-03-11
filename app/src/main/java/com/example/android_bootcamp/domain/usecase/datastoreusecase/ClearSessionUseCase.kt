package com.example.android_bootcamp.domain.usecase.datastoreusecase

import com.example.android_bootcamp.domain.repository.DataStoreRepository
import javax.inject.Inject

class ClearSessionUseCase @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) {
    suspend operator fun invoke() {
        dataStoreRepository.clearSession()
    }
}
