package com.example.android_bootcamp.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.example.android_bootcamp.local.room.DataBase
import com.example.android_bootcamp.local.room.UserDao
import com.example.android_bootcamp.remote.api.ServiceApi
import com.example.android_bootcamp.remote.httpRequest.ApiHelper
import com.example.android_bootcamp.repository.AuthRepository
import com.example.android_bootcamp.repository.DataStoreRepository
import com.example.android_bootcamp.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {

    @Provides
    fun provideADataStoreRepository(dataStore: DataStore<Preferences>): DataStoreRepository {
        return DataStoreRepository(dataStore)
    }
    @Provides
    fun provideAuthRepository(apiHelper: ApiHelper, serviceApi: ServiceApi): AuthRepository {
        return AuthRepository(apiHelper, serviceApi)
    }
    @Provides
    fun provideUserRepository(serviceApi: ServiceApi, userDao: UserDao, dataBase: DataBase): UserRepository {
        return UserRepository(serviceApi, userDao, dataBase)
    }
}
