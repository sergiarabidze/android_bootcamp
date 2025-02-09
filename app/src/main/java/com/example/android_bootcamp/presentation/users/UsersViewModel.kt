package com.example.android_bootcamp.presentation.users

import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.android_bootcamp.remote.api.RetrofitInstance
import com.example.android_bootcamp.remote.api.serializable_classes.User
import com.example.android_bootcamp.remote.paging.UserPagingSource
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
