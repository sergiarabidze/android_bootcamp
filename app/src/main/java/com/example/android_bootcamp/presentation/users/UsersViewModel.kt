package com.example.android_bootcamp.presentation.users


import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.example.android_bootcamp.domain.model.UserDomain
import com.example.android_bootcamp.domain.usecase.paging.GetUsersPagingDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class UsersViewModel @Inject constructor(
    getUsersPagingDataUseCase: GetUsersPagingDataUseCase
) : ViewModel() {

    val userPagingDataFlow: Flow<PagingData<UserDomain>> = getUsersPagingDataUseCase()

}

