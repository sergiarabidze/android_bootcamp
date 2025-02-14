package com.example.android_bootcamp.presentation.users


import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.example.android_bootcamp.local.room.UserEntity
import com.example.android_bootcamp.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


@HiltViewModel
class UsersViewModel@Inject constructor(
    userRepository: UserRepository
) : ViewModel() {



    val userPagingDataFlow: Flow<PagingData<UserEntity>> =userRepository.getUsersPagingDataFlow()

}

