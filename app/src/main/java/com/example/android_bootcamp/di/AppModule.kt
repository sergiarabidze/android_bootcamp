package com.example.android_bootcamp.di

import com.example.android_bootcamp.data.remote.service.AccountServiceApi
import com.example.android_bootcamp.data.remote.request.ApiHelper
import com.example.android_bootcamp.data.remote.service.ToAccountConfirmationServiceApi
import com.example.android_bootcamp.domain.useCase.GetCourseUseCase
import com.example.android_bootcamp.domain.useCase.ValidateInputUseCase
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
    fun provideAccountApi() : AccountServiceApi {
         val baseUrl : String = "https://run.mocky.io/"
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AccountServiceApi::class.java)
    }
    @Provides
    @Singleton
    fun provideConfirmationApi() : ToAccountConfirmationServiceApi {
        val baseUrl : String = "https://run.mocky.io/"
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ToAccountConfirmationServiceApi::class.java)
    }


    @Provides
    @Singleton
    fun provideApiHelper(): ApiHelper {
        return ApiHelper()
    }



}