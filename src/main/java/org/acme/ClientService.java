package org.acme;

import com.monitorjbl.xlsx.StreamingReader;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.jboss.logging.Logger;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

@ApplicationScoped
public class ClientService {
    private static final Logger LOG = Logger.getLogger(ClientService.class);

    public void up(final MultipartFormDataInput file){
        final Map<String, List<InputPart>> formDataMap = file.getFormDataMap();
        final List<InputPart> uploadfile = formDataMap.get("uploadfile");

        uploadfile.forEach(inputPart -> {
            try {
                final InputStream body = inputPart.getBody(InputStream.class, null);

                final StreamingReader read = StreamingReader.builder()
                        .rowCacheSize(100)
                        .bufferSize(4096)
                        .sheetIndex(0)
                        .read(body);

                for (Row row : read) {
                    LOG.info("line : "+ row.getRowNum());

                    if (row.getRowNum()!=0){
                        for (Cell cell : row) {
                            LOG.info("value : "+ cell.getStringCellValue());
                        }

                    }
                }


            } catch (IOException e) {
                e.printStackTrace();
            }

        });

    }
}
