package com.example.android_bootcamp.DI

import android.content.Context
import androidx.room.Room
import com.example.android_bootcamp.local.UserDao
import com.example.android_bootcamp.local.UserDataBase
import com.example.android_bootcamp.remote.ServiceApi
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
object AppModule {

    @Provides
    @Singleton
    fun provideMyApi() : ServiceApi {
         val BASE_URL : String = "https://run.mocky.io/"
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ServiceApi::class.java)
    }

    private const val DATABASE_NAME = "user_database"

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): UserDataBase {
        return Room.databaseBuilder(
            context.applicationContext,
            UserDataBase::class.java,
            DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideUserDao(database: UserDataBase): UserDao {
        return database.userDao()
    }

    @Provides
    @Singleton
    fun provideRepository(serviceApi : ServiceApi,userDao :UserDao):UserRepository {
        return UserRepository(serviceApi = serviceApi, userDao = userDao)
    }
}