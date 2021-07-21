package org.acme;

import org.eclipse.microprofile.openapi.annotations.Operation;

import javax.annotation.Resource;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static io.smallrye.config.ConfigLogging.log;

@Path("/products")

public class ClientResource {
    /*
    @POST
    @Consumes(MediaType.WILDCARD)
    @Operation(description = "Up xlsx", summary = "uploadMultipartFile")
    public String uploadMultipartFile(@RequestParam("uploadfile") MultipartFile file) {

        return "multipartfile/uploadform.html";
    }
*/
    @POST
    @Path("file")
    @Consumes(MediaType.APPLICATION_OCTET_STREAM)
    @Produces(MediaType.APPLICATION_JSON)
    public Response uploadFile(byte[] fileData) {
        System.out.println("Received file of size = " + fileData.length);
        String s = new String(fileData);
        return Response.ok().build();
    }

}
