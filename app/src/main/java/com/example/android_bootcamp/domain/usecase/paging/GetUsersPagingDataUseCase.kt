package com.example.android_bootcamp.domain.usecase.paging

import androidx.paging.PagingData
import androidx.paging.map
import com.example.android_bootcamp.domain.model.UserDomain
import com.example.android_bootcamp.domain.repository.UserRepository
import com.example.android_bootcamp.data.mapper.toDomainModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetUsersPagingDataUseCase @Inject constructor(
    private val repository: UserRepository
) {

    operator fun invoke(): Flow<PagingData<UserDomain>> {
        return repository.getUsersPagingDataFlow().map { pagingData ->
            pagingData.map { userEntity ->
                userEntity.toDomainModel()
            }
        }
    }

}
