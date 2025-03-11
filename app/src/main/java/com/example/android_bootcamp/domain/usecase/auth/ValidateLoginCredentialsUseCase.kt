package com.example.android_bootcamp.domain.usecase.auth

import android.util.Patterns
import com.example.android_bootcamp.domain.model.ValidationResult
import javax.inject.Inject

class ValidateLoginCredentialsUseCase @Inject constructor() {
    operator fun invoke(email: String, password: String): ValidationResult {
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return ValidationResult.Error("Invalid email address")
        }
        if (password.isEmpty()) {
            return ValidationResult.Error("Password cannot be empty")
        }
        return ValidationResult.Success
    }
}
