package com.example.android_bootcamp.local.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [UserEntity::class], version = 1, exportSchema = false)
abstract class DataBase : RoomDatabase() {
    abstract fun userDao():UserDao
}