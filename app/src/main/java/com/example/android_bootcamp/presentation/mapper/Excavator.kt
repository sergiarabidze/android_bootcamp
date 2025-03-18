package com.example.android_bootcamp.presentation.mapper

import com.example.android_bootcamp.domain.model.ExcavatorDomain
import com.example.android_bootcamp.presentation.model.ExcavatorPresentation

fun ExcavatorDomain.toPresentationList(): ExcavatorPresentation {

    return ExcavatorPresentation(this.id, this.name, this.depth)

}