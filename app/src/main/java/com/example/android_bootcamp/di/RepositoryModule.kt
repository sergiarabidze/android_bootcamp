package com.example.android_bootcamp.di

import android.content.Context
import androidx.work.WorkManager
import com.example.android_bootcamp.data.remote.UploadRepositoryImpl
import com.example.android_bootcamp.domain.repository.UploadRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun provideUploadRepository(@ApplicationContext context: Context): UploadRepository {
        return UploadRepositoryImpl(context)
    }

    @Provides
    fun provideWorkManager(@ApplicationContext context: Context): WorkManager = WorkManager.getInstance(context)
}
