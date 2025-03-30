package com.example.android_bootcamp.domain.useCase

import com.example.android_bootcamp.data.repository.AccountsRepositoryImpl
import com.example.android_bootcamp.domain.account.AccountDomain
import com.example.android_bootcamp.domain.resource.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FetchAccountsUseCase @Inject constructor(
    private val repository: AccountsRepositoryImpl
) {
    suspend operator fun invoke(): Flow<Resource<List<AccountDomain>>> {
        return repository.fetchAccounts()
    }
}