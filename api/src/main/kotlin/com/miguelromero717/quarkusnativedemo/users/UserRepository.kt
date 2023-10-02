package com.miguelromero717.quarkusnativedemo.users

import com.miguelromero717.quarkusnativedemo.auth.User
import io.quarkus.hibernate.orm.panache.kotlin.PanacheRepository
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class UserRepository : PanacheRepository<User> {

    fun findByEmail(email: String) = find("email", email).firstResult()
}