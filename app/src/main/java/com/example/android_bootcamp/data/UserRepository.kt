package com.example.android_bootcamp.data


import com.example.android_bootcamp.api.ServiceApi
import com.example.android_bootcamp.extensions.toUser
import kotlinx.coroutines.flow.Flow

class UserRepository(private val userDao: UserDao, private val apiService: ServiceApi) {

    fun getUsers(): Flow<List<User>> = userDao.getAllUsers()

    suspend fun fetchUsersFromServer() {
        try {
            val usersApiList = apiService.getUsers()
            if (usersApiList.isSuccessful){
                val usersEntityList = usersApiList.body()?.users?.map { it.toUser() }
                if (usersEntityList != null) {
                    userDao.insertUsers(usersEntityList)
                }
            }else{
                throw Exception("Failed to fetch users from server.")
            }

        } catch (e: Exception) {
            throw Exception("Failed to fetch users from server.")
        }
    }

    suspend fun addUsers(users: List<User>) {
        userDao.insertUsers(users)
    }
}

