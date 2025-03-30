package com.example.android_bootcamp.data.repository

import com.example.android_bootcamp.data.remote.service.ToAccountConfirmationServiceApi
import com.example.android_bootcamp.data.remote.dto.success.SuccessResponseDto
import com.example.android_bootcamp.data.remote.request.ApiHelper
import com.example.android_bootcamp.domain.repository.ToAccountConfirmationRepository
import com.example.android_bootcamp.domain.resource.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ToAccountConfirmationRepositoryImpl @Inject constructor(
    private val serviceApi: ToAccountConfirmationServiceApi,
    private val apiHelper: ApiHelper
) : ToAccountConfirmationRepository {
    override suspend fun confirmAccount(accountNumber: String): Flow<Resource<SuccessResponseDto>> {
        return apiHelper.handleHttpRequest { serviceApi.confirmAccount(accountNumber) }
    }
}
