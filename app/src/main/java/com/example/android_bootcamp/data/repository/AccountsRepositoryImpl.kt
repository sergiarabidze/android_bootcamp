package com.example.android_bootcamp.data.repository

import com.example.android_bootcamp.data.remote.service.AccountServiceApi
import com.example.android_bootcamp.data.remote.dto.account.toDomain
import com.example.android_bootcamp.data.remote.request.ApiHelper
import com.example.android_bootcamp.domain.account.AccountDomain
import com.example.android_bootcamp.domain.repository.AccountRepository
import com.example.android_bootcamp.domain.resource.Resource
import com.example.android_bootcamp.domain.resource.transform
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

import javax.inject.Inject

class AccountsRepositoryImpl @Inject constructor(
    private val serviceApi: AccountServiceApi,
    private val apiHelper: ApiHelper
) : AccountRepository {
    override suspend fun fetchAccounts(): Flow<Resource<List<AccountDomain>>> {
        return apiHelper.handleHttpRequest { serviceApi.getAccounts() }.map { it.transform { it.map { it.toDomain() } } }
    }
}

