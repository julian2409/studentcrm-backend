package nt.jsa

import jakarta.inject.Inject
import jakarta.ws.rs.GET
import jakarta.ws.rs.Path
import jakarta.ws.rs.Produces
import jakarta.ws.rs.core.MediaType

@Path("/v1/hello")
@Produces(MediaType.TEXT_PLAIN)
class GreetingResource {

    @Inject
    lateinit var greetingService: GreetingService

    @GET
    fun hello() = "Hello from RESTEasy Reactive"

    @GET
    @Path("/greeting/{name}")
    fun greeting(name: String) = greetingService.greeting(name)
}
