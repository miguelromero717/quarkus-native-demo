package com.miguelromero717.quarkusnativedemo.users

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class UserDTO(
    val firstName: String,
    val lastName: String,
    val email: String
)
