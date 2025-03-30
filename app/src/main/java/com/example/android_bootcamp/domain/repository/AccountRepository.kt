package com.example.android_bootcamp.domain.repository


import com.example.android_bootcamp.domain.account.AccountDomain
import com.example.android_bootcamp.domain.resource.Resource
import kotlinx.coroutines.flow.Flow

interface AccountRepository {
     suspend fun fetchAccounts(): Flow<Resource<List<AccountDomain>>>
}
