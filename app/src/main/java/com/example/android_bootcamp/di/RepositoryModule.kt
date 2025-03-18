package com.example.android_bootcamp.di

import com.example.android_bootcamp.data.repository.ExcavatorRepositoryImpl
import com.example.android_bootcamp.domain.repository.ExcavatorRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindUserRepository(
        userRepositoryImpl: ExcavatorRepositoryImpl
    ): ExcavatorRepository
}