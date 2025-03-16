package com.example.android_bootcamp.domain.usecase.datastoreusecase

import com.example.android_bootcamp.data.local.datastore.PreferenceKeys
import com.example.android_bootcamp.domain.repository.DataStoreRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class ReadSessionUseCase @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) {
    operator fun invoke(): Flow<Pair<String?, String?>> {
        val emailFlow = dataStoreRepository.get(PreferenceKeys.EMAIL)
        val tokenFlow = dataStoreRepository.get(PreferenceKeys.TOKEN)

        return combine(emailFlow, tokenFlow) { email, token ->
            Pair(email, token)
        }
    }
}