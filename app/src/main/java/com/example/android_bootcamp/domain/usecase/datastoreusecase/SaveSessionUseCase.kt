package com.example.android_bootcamp.domain.usecase.datastoreusecase

import com.example.android_bootcamp.data.local.datastore.PreferenceKeys
import com.example.android_bootcamp.domain.repository.DataStoreRepository
import javax.inject.Inject

class SaveSessionUseCase @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) {
    suspend operator fun invoke(token: String, email: String) {
        dataStoreRepository.save(PreferenceKeys.TOKEN, token)
        dataStoreRepository.save(PreferenceKeys.EMAIL, email)
    }
}