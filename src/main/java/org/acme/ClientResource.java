package org.acme;

import com.monitorjbl.xlsx.StreamingReader;
import io.vertx.ext.web.multipart.FormDataPart;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.jboss.logging.Logger;
import org.jboss.logging.annotations.Pos;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import static io.smallrye.config.ConfigLogging.log;

@Path("/products")

public class ClientResource {
    private static final Logger LOG = Logger.getLogger(ClientResource.class);

    @Inject
    private ClientService clientService;
    /*
    @POST
    @Consumes(MediaType.WILDCARD)
    @Operation(description = "Up xlsx", summary = "uploadMultipartFile")
    public String uploadMultipartFile(@RequestParam("uploadfile") MultipartFile file) {

        return "multipartfile/uploadform.html";
    }

    @POST
    @Path("file")
    @Consumes(MediaType.APPLICATION_OCTET_STREAM)
    @Produces(MediaType.APPLICATION_JSON)
    public Response uploadFile(byte[] fileData) {
        System.out.println("Received file of size = " + fileData.length);
        String s = new String(fileData);
        return Response.ok().build();
    }
*/
    @POST
    @Path("/file")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
public Response fileUp(@MultipartForm MultipartFormDataInput file){
        LOG.info("Opa");
        clientService.up(file);


        return Response.ok().build();
    }
}
