package com.miguelromero717.quarkusnativedemo.utils

import com.miguelromero717.quarkusnativedemo.auth.UserRole
import io.smallrye.jwt.build.Jwt
import jakarta.enterprise.context.ApplicationScoped
import java.nio.charset.Charset
import java.security.KeyFactory
import java.security.PrivateKey
import java.security.spec.PKCS8EncodedKeySpec
import java.util.Base64
import org.eclipse.microprofile.config.inject.ConfigProperty


@ApplicationScoped
class TokenService {

    @ConfigProperty(name = "mp.jwt.verify.issuer")
    private lateinit var issuer: String

    @ConfigProperty(name = "demo.jwt.duration")
    private lateinit var duration: String

    @Throws(Exception::class)
    fun generateToken(username: String?, roles: Set<UserRole?>): String? {
        val privateKeyLocation = "privateKey.pem"
        val privateKey = readPrivateKey(privateKeyLocation)
        val claimsBuilder = Jwt.claims()
        val currentTimeInSecs: Long = currentTimeInSecs()
        val groups: MutableSet<String> = HashSet()

        for (role in roles) groups.add(role.toString())

        claimsBuilder.issuer(issuer)
        claimsBuilder.subject(username)
        claimsBuilder.issuedAt(currentTimeInSecs)
        claimsBuilder.expiresAt(currentTimeInSecs + duration.toLong())
        claimsBuilder.groups(groups)

        return claimsBuilder.jws().keyId(privateKeyLocation).sign(privateKey)
    }

    @Throws(java.lang.Exception::class)
    fun readPrivateKey(pemResName: String?): PrivateKey {
        val contentIS = TokenService::class.java.classLoader.getResourceAsStream(pemResName)
        val tmp = ByteArray(4096)
        val length = contentIS.read(tmp)
        return decodePrivateKey(String(tmp, 0, length, Charset.forName("UTF-8")))
    }

    @Throws(java.lang.Exception::class)
    fun decodePrivateKey(pemEncoded: String): PrivateKey {
        val encodedBytes = toEncodedBytes(pemEncoded)
        val keySpec = PKCS8EncodedKeySpec(encodedBytes)
        val kf = KeyFactory.getInstance("RSA")
        return kf.generatePrivate(keySpec)
    }

    fun toEncodedBytes(pemEncoded: String): ByteArray {
        val normalizedPem = removeBeginEnd(pemEncoded)
        return Base64.getDecoder().decode(normalizedPem)
    }

    fun removeBeginEnd(pem: String): String {
        var pem = pem
        pem = pem.replace("-----BEGIN (.*)-----".toRegex(), "")
        pem = pem.replace("-----END (.*)----".toRegex(), "")
        pem = pem.replace("\r\n".toRegex(), "")
        pem = pem.replace("\n".toRegex(), "")
        return pem.trim { it <= ' ' }
    }

    fun currentTimeInSecs(): Long {
        val currentTimeMS = System.currentTimeMillis()
        return (currentTimeMS / 1000)
    }
}