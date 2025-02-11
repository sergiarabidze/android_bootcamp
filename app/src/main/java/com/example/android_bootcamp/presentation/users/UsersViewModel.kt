package com.example.android_bootcamp.presentation.users

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.android_bootcamp.local.room.DataBase
import com.example.android_bootcamp.local.room.UserEntity
import com.example.android_bootcamp.local.room.UserRemoteMediator
import com.example.android_bootcamp.remote.api.RetrofitInstance
import kotlinx.coroutines.flow.Flow

class UsersViewModel(
    private val app: Application
) : AndroidViewModel(app) {

    @OptIn(ExperimentalPagingApi::class)
    val userPagingDataFlow: Flow<PagingData<UserEntity>> = Pager(
        config = PagingConfig(pageSize = 5, enablePlaceholders = false),
        remoteMediator = UserRemoteMediator(RetrofitInstance.api, DataBase.getInstance(app.applicationContext)),
        pagingSourceFactory = { DataBase.getInstance(app.applicationContext).userDao().getAllUsers() }
    ).flow.cachedIn(viewModelScope)

}

