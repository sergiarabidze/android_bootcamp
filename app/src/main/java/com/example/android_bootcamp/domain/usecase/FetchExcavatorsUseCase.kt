package com.example.android_bootcamp.domain.usecase

import com.example.android_bootcamp.data.request.Resource
import com.example.android_bootcamp.domain.model.ExcavatorDomain
import com.example.android_bootcamp.domain.repository.ExcavatorRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FetchExcavatorsUseCase @Inject constructor(
    private val repository: ExcavatorRepository
) {
    suspend operator fun invoke(searchQuery: String = ""): Flow<Resource<List<ExcavatorDomain>>> {
        return repository.fetchExcavators().map { resource ->
            when (resource) {
                is Resource.Success -> {
                    val filteredList = if (searchQuery.isNotBlank()) {
                        resource.data.filter {
                            it.name.contains(searchQuery, ignoreCase = true)
                        }
                    } else {
                        resource.data
                    }
                    Resource.Success(filteredList)
                }
                is Resource.Error -> resource
                is Resource.Loading -> resource
            }
        }
    }
}
