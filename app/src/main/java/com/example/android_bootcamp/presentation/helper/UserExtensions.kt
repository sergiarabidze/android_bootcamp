package com.example.android_bootcamp.presentation.helper

import com.example.android_bootcamp.data.local.room.UserEntity
import com.example.android_bootcamp.data.remote.api.serializable_classes.User

fun User.toEntity(): UserEntity {
    return UserEntity(
        id = this.id,
        email = this.email,
        firstName = this.firstName,
        lastName = this.lastName,
        avatar = this.avatar
    )
}
