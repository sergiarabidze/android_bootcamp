package com.example.android_bootcamp.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.android_bootcamp.data.local.room.DataBase
import com.example.android_bootcamp.data.local.room.UserDao
import com.example.android_bootcamp.data.local.room.UserEntity
import com.example.android_bootcamp.data.local.room.UserRemoteMediator
import com.example.android_bootcamp.data.mapper.toDomainModel
import com.example.android_bootcamp.data.remote.api.ServiceApi
import com.example.android_bootcamp.domain.model.UserDomain
import com.example.android_bootcamp.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val serviceApi: ServiceApi,
    private val userDao: UserDao,
    private val database: DataBase
) : UserRepository {

    @OptIn(ExperimentalPagingApi::class)
    override fun getUsersPagingDataFlow(): Flow<PagingData<UserDomain>> {
        return Pager(
            config = PagingConfig(
                pageSize = 6,
                initialLoadSize = 10,
                enablePlaceholders = false
            ),
            remoteMediator = UserRemoteMediator(serviceApi, database),
            pagingSourceFactory = { userDao.getAllUsers() }
        ).flow
            .map { pagingData ->
                pagingData.map { userEntity ->
                    userEntity.toDomainModel()
                }
            }
    }
}
