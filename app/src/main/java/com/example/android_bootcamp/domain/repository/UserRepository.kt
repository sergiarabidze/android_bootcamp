package com.example.android_bootcamp.domain.repository

import androidx.paging.PagingData
import com.example.android_bootcamp.data.local.room.UserEntity
import com.example.android_bootcamp.domain.model.UserDomain
import kotlinx.coroutines.flow.Flow

interface UserRepository{

    fun getUsersPagingDataFlow(): Flow<PagingData<UserDomain>>

}