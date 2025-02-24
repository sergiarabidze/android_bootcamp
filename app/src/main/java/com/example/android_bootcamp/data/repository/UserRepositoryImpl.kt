package com.example.android_bootcamp.data.repository

import com.example.android_bootcamp.data.local.UserDao
import com.example.android_bootcamp.data.local.UserEntity
import com.example.android_bootcamp.data.remote.ServiceApi
import retrofit2.Response
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userDao: UserDao,
    private val serviceApi: ServiceApi
) : UserRepository {
    override suspend fun getUsers(): List<UserEntity> = userDao.getAll()
    override suspend fun fetchUsers(): Response<Int> = serviceApi.getUsers()
}
