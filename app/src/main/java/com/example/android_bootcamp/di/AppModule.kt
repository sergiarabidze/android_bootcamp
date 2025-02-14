package com.example.android_bootcamp.di

import android.content.Context
import android.preference.Preference
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.room.Room
import com.example.android_bootcamp.local.datastore.PreferenceKeys
import com.example.android_bootcamp.local.datastore.dataStore
import com.example.android_bootcamp.local.room.DataBase
import com.example.android_bootcamp.local.room.UserDao
import com.example.android_bootcamp.remote.api.ServiceApi
import com.example.android_bootcamp.remote.httpRequest.ApiHelper
import com.example.android_bootcamp.repository.AuthRepository
import com.example.android_bootcamp.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule{
    @Provides
    @Singleton
    fun provideMyApi() : ServiceApi {
        val BASE_URL = "https://reqres.in/api/"
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ServiceApi::class.java)
    }
    @Provides
    @Singleton
    fun provideApiHelper() : ApiHelper {
        return ApiHelper()
    }


    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): DataBase {
        return Room.databaseBuilder(
            context.applicationContext,
            DataBase::class.java,
            "user_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideUserDao(database: DataBase): UserDao {
        return database.userDao()
    }

    @Provides
    @Singleton
    fun provideAuthRepository(apiHelper: ApiHelper,serviceApi: ServiceApi): AuthRepository{
        return AuthRepository(apiHelper,serviceApi)
    }

    @Provides
    @Singleton
    fun provideUserRepository(serviceApi: ServiceApi,userDao: UserDao,dataBase: DataBase):UserRepository{
        return UserRepository(serviceApi,userDao,dataBase)
    }
    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext context: Context) : DataStore<Preferences>{
        return context.dataStore
    }

}