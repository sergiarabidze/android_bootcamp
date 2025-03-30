package com.example.android_bootcamp.di

import com.example.android_bootcamp.domain.useCase.GetCourseUseCase
import com.example.android_bootcamp.domain.useCase.ValidateInputUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule{
    @Provides
    @Singleton
    fun provideValidateInputUseCase(): ValidateInputUseCase {
        return ValidateInputUseCase()
    }

    @Provides
    @Singleton
    fun provideCourseUseCase(): GetCourseUseCase {
        return GetCourseUseCase()
    }
}