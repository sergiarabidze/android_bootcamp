package com.example.android_bootcamp.DI


import com.example.android_bootcamp.remote.ServiceApi
import com.example.android_bootcamp.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
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
         val BASE_URL= "https://run.mocky.io/"
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ServiceApi::class.java)
    }

    @Provides
    @Singleton
    fun provideRepository(serviceApi : ServiceApi):UserRepository {
        return UserRepository(serviceApi = serviceApi)
    }
}