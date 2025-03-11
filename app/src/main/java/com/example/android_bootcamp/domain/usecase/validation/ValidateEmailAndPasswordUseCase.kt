package com.example.android_bootcamp.domain.usecase.validation

import android.util.Patterns
import com.example.android_bootcamp.domain.model.ValidationResult
import javax.inject.Inject

class ValidateEmailAndPasswordUseCase @Inject constructor() {

    operator fun invoke(email: String, password: String, confirmPassword: String? = null): ValidationResult {
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return ValidationResult.Error("Invalid email address")
        }
        if (password.length < 6) {
            return ValidationResult.Error("Password must be at least 6 characters")
        }
        if (confirmPassword != null && password != confirmPassword) {
            return ValidationResult.Error("Passwords do not match")
        }
        return ValidationResult.Success
    }
}
