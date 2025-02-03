package com.example.android_bootcamp.data

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "users")
data class User(
    @PrimaryKey val id: Int,
    val avatar: String?,
    val firstName: String,
    val lastName: String,
    val about: String? = null,
    val activationStatus : Double
)