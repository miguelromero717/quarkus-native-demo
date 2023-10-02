package com.miguelromero717.quarkusnativedemo.auth

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class SignUpUserDTO(
    val firstName: String,
    val lastName: String,
    val email: String,
    val password: String,
    val role: UserRole
)
