package com.example.android_bootcamp.domain.usecase.paging

import androidx.paging.PagingData
import com.example.android_bootcamp.domain.model.UserDomain
import com.example.android_bootcamp.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUsersPagingDataUseCase @Inject constructor(
    private val repository: UserRepository
) {
    operator fun invoke(): Flow<PagingData<UserDomain>> {
        return repository.getUsersPagingDataFlow()
    }
}
