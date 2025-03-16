package com.example.android_bootcamp.presentation.users


import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import androidx.paging.map
import com.example.android_bootcamp.domain.model.UserDomain
import com.example.android_bootcamp.domain.usecase.paging.GetUsersPagingDataUseCase
import com.example.android_bootcamp.presentation.users.model.user.UserPresentation
import com.example.android_bootcamp.presentation.users.model.user.toPresentationModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class UsersViewModel @Inject constructor(
    getUsersPagingDataUseCase: GetUsersPagingDataUseCase
) : ViewModel() {

    val userPagingDataFlow: Flow<PagingData<UserPresentation>> = getUsersPagingDataUseCase()
        .map { pagingData ->
            pagingData.map { userDomain ->
                userDomain.toPresentationModel()
            }
        }
}

