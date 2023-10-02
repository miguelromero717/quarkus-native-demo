package com.miguelromero717.quarkusnativedemo.auth

import com.miguelromero717.quarkusnativedemo.users.UserRepository
import com.miguelromero717.quarkusnativedemo.utils.EncryptionService
import com.miguelromero717.quarkusnativedemo.utils.TokenService
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import jakarta.transaction.Transactional

@ApplicationScoped
@Transactional
class AuthService {

    @Inject
    private lateinit var userRepository: UserRepository

    @Inject
    private lateinit var encryptionService: EncryptionService

    @Inject
    private lateinit var tokenService: TokenService

    fun signUpUser(payload: SignUpUserDTO) {
        val salt = encryptionService.genSalt()
        val password = encryptionService.generateHash(payload.password, salt)

        val user = User(
            firstName = payload.firstName,
            lastName = payload.lastName,
            email = payload.email,
            password = password,
            salt = salt,
            role = payload.role
        )
        userRepository.persistAndFlush(user)
    }

    fun signIn(email: String, password: String): UserTokenDTO {
        val user = userRepository.findByEmail(email) ?: throw Exception("User not found")

        if (encryptionService.checkHash(password = password, hash = user.password).not())
            throw Exception("Invalid password")

        val token = tokenService.generateToken(
            username = user.email,
            roles = setOf(user.role)
        ) ?: throw Exception("Invalid Token Generated")

        return UserTokenDTO(
            accessToken = token
        )
    }
}