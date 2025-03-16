package com.example.android_bootcamp.presentation.users.model.user

import com.example.android_bootcamp.domain.model.UserDomain


fun UserDomain.toPresentationModel(): UserPresentation {
    return UserPresentation(
        id = this.id,
        email = this.email,
        firstName = this.firstName,
        lastName = this.lastName,
        avatar = this.avatar
    )
}