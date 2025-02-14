package com.example.android_bootcamp.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.android_bootcamp.local.room.DataBase
import com.example.android_bootcamp.local.room.UserDao
import com.example.android_bootcamp.local.room.UserEntity
import com.example.android_bootcamp.local.room.UserRemoteMediator
import com.example.android_bootcamp.remote.api.ServiceApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val serviceApi: ServiceApi,
    private val userDao: UserDao,
    private val database: DataBase
) {

    @OptIn(ExperimentalPagingApi::class)
    fun getUsersPagingDataFlow(): Flow<PagingData<UserEntity>> {
        return Pager(
            config = PagingConfig(pageSize = 5, enablePlaceholders = false),
            remoteMediator = UserRemoteMediator(serviceApi, database),
            pagingSourceFactory = { userDao.getAllUsers() }
        ).flow
    }
}