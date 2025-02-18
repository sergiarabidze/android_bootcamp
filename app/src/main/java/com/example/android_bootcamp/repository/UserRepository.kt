package com.example.android_bootcamp.repository

import com.example.android_bootcamp.local.UserEntity
import retrofit2.Response

interface UserRepository {
     suspend fun getUsers(): List<UserEntity>
     suspend fun fetchUsers(): Response<Int>
}
