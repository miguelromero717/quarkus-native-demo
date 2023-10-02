package com.miguelromero717.quarkusnativedemo.auth

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class SignInUserDTO(
    val email: String,
    val password: String
)
