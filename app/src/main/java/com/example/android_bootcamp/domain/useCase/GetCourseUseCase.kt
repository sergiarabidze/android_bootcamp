package com.example.android_bootcamp.domain.useCase

import com.example.android_bootcamp.domain.helper.Valute

class GetCourseUseCase {
    operator fun invoke(from: Valute, to: Valute): Double {
        return 2.9
    }
}