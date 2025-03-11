package com.example.android_bootcamp.data.mapper

import com.example.android_bootcamp.data.local.room.UserEntity
import com.example.android_bootcamp.domain.model.UserDomain

fun UserEntity.toDomainModel(): UserDomain {
    return UserDomain(
        id = this.id,
        email = this.email,
        firstName = this.firstName,
        lastName = this.lastName,
        avatar = this.avatar
    )
}