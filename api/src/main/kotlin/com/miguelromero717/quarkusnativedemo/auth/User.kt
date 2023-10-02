package com.miguelromero717.quarkusnativedemo.auth

import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "users")
open class User(
    @Id
    @GeneratedValue
    open var id: Int = 0,
    open var firstName: String,
    open var lastName: String,
    open var email: String,
    open var password: String,
    open var salt: String,
    @Enumerated(EnumType.STRING)
    open var role: UserRole
)