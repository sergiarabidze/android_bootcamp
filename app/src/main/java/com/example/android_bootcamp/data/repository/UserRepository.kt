package com.example.android_bootcamp.data.repository

import com.example.android_bootcamp.data.remote.dto.LocationDto
import com.example.android_bootcamp.data.remote.http.Resource

interface UserRepository {
     suspend fun fetchLocations(): Resource<List<LocationDto>>
}
