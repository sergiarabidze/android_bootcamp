package com.example.android_bootcamp.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDao{

    @Query("SELECT * from user")
    suspend fun getAll() : List<UserEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(users : List<UserEntity>)

    @Query("DELETE FROM user WHERE id = :userId")
    suspend fun deleteUser(userId: Int)

}