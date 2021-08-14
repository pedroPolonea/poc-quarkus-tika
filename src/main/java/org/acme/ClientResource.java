package org.acme;

import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.ByteArrayInputStream;

@Path("/client")

public class ClientResource {
    private Logger log = LoggerFactory.getLogger(ClientResource.class);

    private final static String HEADER_VALUE = "attachment; filename=Customers.xlsx";

    private final ClientService clientService;

    private final XlsxService xlsxService;

    public ClientResource(
            final ClientService clientService,
            final XlsxService xlsxService
    ) {
        this.clientService = clientService;
        this.xlsxService = xlsxService;
    }


    @POST
    @Path("/file")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response fileUp(@MultipartForm MultipartFormDataInput file){
        log.info("M=fileUp");
        clientService.up(file);


        return Response.ok().build();
    }

    @GET
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response buildXlsxCustomersTemplate() {
        log.info("M=buildXlsxCustomersTemplate, I=Entrada");
        final ByteArrayInputStream baInputS = xlsxService.buildXlsxCustomersTemplate();
        log.info("M=buildXlsxCustomersTemplate, I=Arquivo gerado");

        return Response.ok(baInputS)
                .header(HttpHeaders.CONTENT_DISPOSITION, HEADER_VALUE)
                .build();
    }
}
