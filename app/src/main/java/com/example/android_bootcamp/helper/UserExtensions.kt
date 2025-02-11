package com.example.android_bootcamp.helper

import com.example.android_bootcamp.local.room.UserEntity
import com.example.android_bootcamp.remote.api.serializable_classes.User

fun User.toEntity(): UserEntity {
    return UserEntity(
        id = this.id,
        email = this.email,
        firstName = this.firstName,
        lastName = this.lastName,
        avatar = this.avatar
    )
}
