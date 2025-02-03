package com.example.android_bootcamp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [User::class], version = 2 , exportSchema = false)
abstract class UserDataBase : RoomDatabase(){

    abstract  fun userDao() : UserDao

    companion object {
        @Volatile
        private var Instance: UserDataBase? = null

        fun getDatabase(context: Context): UserDataBase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, UserDataBase::class.java, "user_database")
                    .build()
                    .also { Instance = it }
            }
        }
    }

}