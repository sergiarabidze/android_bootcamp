package com.example.android_bootcamp.domain.repository

import com.example.android_bootcamp.data.request.Resource
import com.example.android_bootcamp.domain.model.ExcavatorDomain
import kotlinx.coroutines.flow.Flow

interface ExcavatorRepository {
     suspend fun fetchExcavators(): Flow<Resource<List<ExcavatorDomain>>>
}
