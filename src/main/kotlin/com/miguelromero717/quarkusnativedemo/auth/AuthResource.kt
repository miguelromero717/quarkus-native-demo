package com.miguelromero717.quarkusnativedemo.auth

import jakarta.annotation.security.PermitAll
import jakarta.inject.Inject
import jakarta.ws.rs.Consumes
import jakarta.ws.rs.POST
import jakarta.ws.rs.Path
import jakarta.ws.rs.Produces
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import org.jboss.logging.Logger

@Path("/auth")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
class AuthResource {

    private val log: Logger = Logger.getLogger(AuthResource::class.java)

    @Inject
    private lateinit var authService: AuthService

    @POST
    @Path("/signup")
    @PermitAll
    fun signUp(
        payload: SignUpUserDTO
    ): Response {
        return try {
            authService.signUpUser(payload)

            log.info("User ${payload.email} signed up")

            Response.status(Response.Status.CREATED).build()
        } catch (e: Exception) {
            log.error(e.message, e)
            Response.serverError().build()
        }
    }

    @POST
    @Path("/signin")
    @PermitAll
    fun signIn(
        payload: SignInUserDTO
    ): UserTokenDTO {
        return try {
            val userToken = authService.signIn(
                email = payload.email,
                password = payload.password
            )

            log.info("User ${payload.email} signed in")

            userToken
        } catch (e: Exception) {
            log.error(e.message, e)
            throw e
        }
    }
}