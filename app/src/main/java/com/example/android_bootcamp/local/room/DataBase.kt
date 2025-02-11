package com.example.android_bootcamp.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [UserEntity::class], version = 1, exportSchema = false)
abstract class DataBase : RoomDatabase() {
    abstract fun userDao():UserDao

    companion object{
    @Volatile
    private var INSTANCE : DataBase? = null

        fun getInstance(context : Context) : DataBase{
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DataBase::class.java,
                    "user_database"
                ).build()

                INSTANCE = instance
                instance
            }
        }
    }
}