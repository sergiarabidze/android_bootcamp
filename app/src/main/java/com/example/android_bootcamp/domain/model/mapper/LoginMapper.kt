package com.example.android_bootcamp.domain.model.mapper

import com.example.android_bootcamp.data.remote.api.serializable_classes.ResponseLogin
import com.example.android_bootcamp.domain.model.LoginDomain

fun ResponseLogin.toDomainModel(): LoginDomain {
    return LoginDomain(token = this.token)
}