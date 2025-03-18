package com.example.android_bootcamp.data.repository


import com.example.android_bootcamp.data.mapper.toDomain
import com.example.android_bootcamp.data.remote.ServiceApi
import com.example.android_bootcamp.data.request.ApiHelper
import com.example.android_bootcamp.data.request.Resource
import com.example.android_bootcamp.domain.model.ExcavatorDomain
import com.example.android_bootcamp.domain.repository.ExcavatorRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ExcavatorRepositoryImpl @Inject constructor(
    private val serviceApi: ServiceApi,
    private val apiHelper: ApiHelper
) : ExcavatorRepository {
    override suspend fun fetchExcavators(): Flow<Resource<List<ExcavatorDomain>>> {
        return apiHelper.handleHttpRequest { serviceApi.getExcavators() }.map {
            when(it){
                is Resource.Error -> it
                is Resource.Loading -> it
                is Resource.Success -> Resource.Success(it.data.toDomain().toList())
            }
        }
    }
}