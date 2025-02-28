package com.example.android_bootcamp.data.repository


import com.example.android_bootcamp.data.remote.ServiceApi
import com.example.android_bootcamp.data.remote.dto.LocationDto
import com.example.android_bootcamp.data.remote.http.Resource
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val serviceApi: ServiceApi
) : UserRepository {
    override suspend fun fetchLocations(): Resource<List<LocationDto>> {
        return try{
            Resource.Loading
            val response = serviceApi.getUsers()
            if (response.isSuccessful) {
                if (response.body()!=null){
                    Resource.Success(response.body()!!)
                }else{
                    Resource.Error("Empty body")
                }

            } else {
                Resource.Error(response.message())
            }

        }catch (e: Exception) {
            Resource.Error(e.message ?: "Unknown error")
        }
    }
}
