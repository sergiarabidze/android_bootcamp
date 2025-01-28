package com.example.android_bootcamp.view_models

import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.android_bootcamp.api.RetrofitInstance
import com.example.android_bootcamp.api.User
import com.example.android_bootcamp.paging.UserPagingSource
import kotlinx.coroutines.flow.Flow


class UsersViewModel : ViewModel() {

    val usersFlow: Flow<PagingData<User>> = Pager(
        config = PagingConfig(
            pageSize = 6,
            enablePlaceholders = false,
            prefetchDistance = 1
        ),
        pagingSourceFactory = { UserPagingSource(RetrofitInstance.api) }
    ).flow
}
