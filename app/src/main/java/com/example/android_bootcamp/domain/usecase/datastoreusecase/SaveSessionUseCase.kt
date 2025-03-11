package com.example.android_bootcamp.domain.usecase.datastoreusecase

import com.example.android_bootcamp.domain.repository.DataStoreRepository
import javax.inject.Inject

class SaveSessionUseCase @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) {
    suspend operator fun invoke(token: String, email: String) {
        dataStoreRepository.saveSession(token, email)
    }
}
