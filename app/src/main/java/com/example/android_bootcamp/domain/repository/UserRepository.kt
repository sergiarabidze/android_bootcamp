package com.example.android_bootcamp.domain.repository

import androidx.paging.PagingData
import com.example.android_bootcamp.data.local.room.UserEntity
import kotlinx.coroutines.flow.Flow

interface UserRepository{

    fun getUsersPagingDataFlow(): Flow<PagingData<UserEntity>>

}