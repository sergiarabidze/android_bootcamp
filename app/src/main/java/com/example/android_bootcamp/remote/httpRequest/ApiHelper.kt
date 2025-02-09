package com.example.android_bootcamp.remote.httpRequest

import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

object ApiHelper{
    suspend fun <T> handleHttpRequest ( apiCall : suspend () -> Response<T>): Resource<T> {
        val response = apiCall.invoke()
        return try{
            if (response.isSuccessful){
                response.body()?.let {
                    Resource.Success(data = it)
                }?: Resource.Error("Something went wrong")
            }else{
                Resource.Error(response.code().toString())
            }
        }
        catch (throwable : Throwable){
            when(throwable){
                is IOException -> Resource.Error("Network Failure")
                is HttpException -> Resource.Error(throwable.code().toString())
                else -> Resource.Error("Something went wrong")
            }
        }
    }
}
