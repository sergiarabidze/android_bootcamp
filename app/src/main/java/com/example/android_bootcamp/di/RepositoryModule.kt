package com.example.android_bootcamp.di

import com.example.android_bootcamp.data.repository.AccountsRepositoryImpl
import com.example.android_bootcamp.data.repository.ToAccountConfirmationRepositoryImpl

import com.example.android_bootcamp.domain.repository.AccountRepository
import com.example.android_bootcamp.domain.repository.ToAccountConfirmationRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindAccountRepository(
        accountRepositoryImpl: AccountsRepositoryImpl
    ): AccountRepository

    @Binds
    abstract fun bindToAccountConfirmationRepository(
        toAccountConfirmationRepositoryImpl: ToAccountConfirmationRepositoryImpl
        ): ToAccountConfirmationRepository

}