package com.miguelromero717.quarkusnativedemo.utils

import jakarta.enterprise.context.ApplicationScoped
import java.security.SecureRandom
import java.util.HexFormat
import org.mindrot.jbcrypt.BCrypt

@ApplicationScoped
class EncryptionService {

    fun generateHash(password: String, salt: String): String {
        return BCrypt.hashpw(password, salt + generateRandomPepper())
    }

    fun genSalt(): String {
        return BCrypt.gensalt(12)
    }

    fun generateRandomPepper(): String {
        val random = SecureRandom()
        val pepper = ByteArray(8)
        random.nextBytes(pepper)
        return pepper.toHexString()
    }

    fun checkHash(password: String, hash: String): Boolean {
        return BCrypt.checkpw(password, hash)
    }

    fun ByteArray.toHexString(): String = HexFormat.of().formatHex(this)
}