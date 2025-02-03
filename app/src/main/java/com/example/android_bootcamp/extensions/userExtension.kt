package com.example.android_bootcamp.extensions

import com.example.android_bootcamp.api.UserA
import com.example.android_bootcamp.data.User

fun UserA.toUser(): User {
    return User(
        id = this.id,
        avatar = this.avatar,
        firstName = this.firstName,
        lastName = this.lastName,
        about = this.about,
        activationStatus = this.activationStatus
    )
}

fun User.getActivationStatusText(): String {
    return when {
        activationStatus <= 0 -> "User is not activated"
        activationStatus == 1.0 -> "Online"
        activationStatus == 2.0 -> "Active a few minutes ago"
        activationStatus in 3.0..22.9 -> "Active a few hours ago"
        else -> "Inactive for a long time"
    }
}
