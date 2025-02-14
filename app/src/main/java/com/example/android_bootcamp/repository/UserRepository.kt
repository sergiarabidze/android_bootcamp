package com.example.android_bootcamp.repository


import com.example.android_bootcamp.remote.Place
import com.example.android_bootcamp.remote.ServiceApi
import retrofit2.Response
import javax.inject.Inject

class UserRepository@Inject constructor(private val serviceApi : ServiceApi) {
     suspend fun fetchPlaces() : Response<List<Place>> = serviceApi.getPlaces()

}