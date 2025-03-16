package com.example.android_bootcamp.presentation.login.model

import com.example.android_bootcamp.domain.model.LoginDomain

fun LoginDomain.toPresentation(): LoginPresentation {
    return LoginPresentation(
        token = this.token
    )
}