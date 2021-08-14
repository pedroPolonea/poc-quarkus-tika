package org.acme.service;

import org.acme.domain.ClientDTO;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@ApplicationScoped
public class ClientService {
    private Logger log = LoggerFactory.getLogger(ClientService.class);

    private final XlsxService xlsxService;

    private final Validator validator;

    @Inject
    public ClientService(
            final XlsxService xlsxService,
            final Validator validator
    ) {
        this.xlsxService = xlsxService;
        this.validator = validator;
    }


    public ByteArrayInputStream up(final MultipartFormDataInput file){
        log.info("M=up, I=Entrada");

        final List<ClientDTO> clientList = xlsxService.up(file);
        final Map<Integer, String> clientMapError = new HashMap<Integer, String>();

        for (ClientDTO clientDTO : clientList) {
            Set<ConstraintViolation<ClientDTO>> violations = validator.validate(clientDTO);
            if (!violations.isEmpty()) {
                clientMapError.put(clientDTO.getLine(), violations.stream().findFirst().get().getMessage());
            }
        }

        if (!clientMapError.isEmpty()) {
            return xlsxService.buildXlsxCustomersError(clientMapError, clientList);
        }

        return null;
    }
}
