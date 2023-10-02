package com.miguelromero717.quarkusnativedemo.users

import com.miguelromero717.quarkusnativedemo.auth.AuthResource
import jakarta.annotation.security.RolesAllowed
import jakarta.inject.Inject
import jakarta.ws.rs.Consumes
import jakarta.ws.rs.DELETE
import jakarta.ws.rs.GET
import jakarta.ws.rs.PUT
import jakarta.ws.rs.Path
import jakarta.ws.rs.PathParam
import jakarta.ws.rs.Produces
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import org.jboss.logging.Logger

@Path("/users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
class UsersResource {

    private val log: Logger = Logger.getLogger(UsersResource::class.java)

    @Inject
    private lateinit var usersService: UsersService

    @GET
    @RolesAllowed("ADMIN", "USER")
    fun getUsers(): List<UserDTO> {
        log.info("Getting all users")
        return usersService.getAllUsers().map { user ->
            UserDTO(
                user.firstName,
                user.lastName,
                user.email
            )
        }
    }

    @GET
    @Path("/{id}")
    @RolesAllowed("ADMIN", "USER")
    fun getUser(
        @PathParam("id") id: Long
    ): UserDTO {
        log.info("Getting user with id $id")
        val user = usersService.getUser(id)
        return UserDTO(
            user.firstName,
            user.lastName,
            user.email,
        )
    }

    @PUT
    @Path("/{id}")
    @RolesAllowed("ADMIN", "USER")
    fun updateUser(
        @PathParam("id") id: Long,
        payload: UpdateUserDTO
    ): Response {
        return try {
            usersService.updateUser(id, payload)

            log.info("User with id $id updated")

            Response.ok().build()
        } catch (e: Exception) {
            log.error(e.message, e)
            Response.serverError().build()
        }
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed("ADMIN")
    fun deleteUser(
        @PathParam("id") id: Long
    ): Response {
        usersService.deleteUser(id)

        log.info("User with id $id deleted")

        return Response.ok().build()
    }
}