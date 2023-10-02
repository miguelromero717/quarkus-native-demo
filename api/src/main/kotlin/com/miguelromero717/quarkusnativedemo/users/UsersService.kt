package com.miguelromero717.quarkusnativedemo.users

import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import jakarta.transaction.Transactional

@ApplicationScoped
@Transactional
class UsersService {

    @Inject
    private lateinit var userRepository: UserRepository

    fun getAllUsers() = userRepository.listAll()

    fun getUser(id: Long) = userRepository.findById(id) ?: throw Exception("User not found")

    fun deleteUser(id: Long) {
        userRepository.deleteById(id)
    }

    fun updateUser(id: Long, payload: UpdateUserDTO) {
        val user = userRepository.findById(id) ?: throw Exception("User not found")
        user.firstName = payload.firstName
        user.lastName = payload.lastName
        userRepository.persistAndFlush(user)
    }
}