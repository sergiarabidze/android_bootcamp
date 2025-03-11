package com.example.android_bootcamp.domain.usecase.datastoreusecase

import com.example.android_bootcamp.domain.repository.DataStoreRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ReadSessionUseCase @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) {
    operator fun  invoke() : Flow<Pair<String?, String?>> {
        return dataStoreRepository.readSession()
    }
}