package com.example.android_bootcamp.data.remote.request

import com.example.android_bootcamp.domain.resource.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

class ApiHelper {
    suspend fun <T> handleHttpRequest(apiCall: suspend () -> Response<T>): Flow<Resource<T>> =
        flow {
            try {
                emit(Resource.Loading)
                val response = apiCall.invoke()
                if (response.isSuccessful) {
                    response.body()?.let {
                        emit(Resource.Success(data = it))
                    } ?: emit(Resource.Error("Something went wrong"))
                } else {
                    emit(Resource.Error(response.code().toString()))
                }
            } catch (io: IOException) {
                emit(Resource.Error("Network Failure"))
            } catch (http: HttpException) {
                emit(Resource.Error(http.code().toString()))
            } catch (e: Throwable) {
                emit(Resource.Error("Something went wrong"))
            }
        }
}