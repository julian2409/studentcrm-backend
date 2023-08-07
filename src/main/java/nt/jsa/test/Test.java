package nt.jsa.test;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/v1/test")
@Produces(MediaType.TEXT_PLAIN)
public class Test {
    @GET
    public String test() {
        return "Hello from Java";
    }
}
