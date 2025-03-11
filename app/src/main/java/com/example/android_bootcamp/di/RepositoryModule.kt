package com.example.android_bootcamp.di
import com.example.android_bootcamp.data.repository.AuthRepositoryImpl
import com.example.android_bootcamp.data.repository.DataStoreRepositoryImpl
import com.example.android_bootcamp.data.repository.UserRepositoryImpl
import com.example.android_bootcamp.domain.repository.AuthRepository
import com.example.android_bootcamp.domain.repository.DataStoreRepository
import com.example.android_bootcamp.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindAuthRepository(authRepositoryImpl: AuthRepositoryImpl): AuthRepository

    @Binds
    abstract fun bindUserRepository(userRepositoryImpl: UserRepositoryImpl): UserRepository

    @Binds
    abstract fun bindDatabaseRepository(databaseRepositoryImpl: DataStoreRepositoryImpl): DataStoreRepository
}

