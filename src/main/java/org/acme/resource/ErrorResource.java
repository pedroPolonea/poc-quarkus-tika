package org.acme.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/error")
public class ErrorResource {
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Response buildXlsxCustomersTemplate() {
        return Response.ok("Vixii!").build();
    }
}
