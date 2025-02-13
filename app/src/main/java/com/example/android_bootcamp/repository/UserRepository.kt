package com.example.android_bootcamp.repository

import com.example.android_bootcamp.local.UserDao
import com.example.android_bootcamp.local.UserEntity
import com.example.android_bootcamp.remote.ServiceApi
import retrofit2.Response
import javax.inject.Inject

class UserRepository@Inject constructor(private val userDao : UserDao , private val serviceApi : ServiceApi) {
     suspend fun getUsers()  : List<UserEntity>  = userDao.getAll()

     suspend fun fetchUsers() : Response<Int> = serviceApi.getUsers()

}