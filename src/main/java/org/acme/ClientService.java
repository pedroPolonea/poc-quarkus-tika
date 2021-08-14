package org.acme;

import com.monitorjbl.xlsx.StreamingReader;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

@ApplicationScoped
public class ClientService {
    private Logger log = LoggerFactory.getLogger(ClientService.class);

    public void up(final MultipartFormDataInput file){
        log.info("M=up, I=Entrada");

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
                    log.info("line : {}", row.getRowNum());

                    if (row.getRowNum()!=0){
                        for (Cell cell : row) {
                            log.info("value : {} ", cell.getStringCellValue());
                        }

                    }
                }
            } catch (IOException e) {
                log.error("M=up, E=Vixxi");
                e.printStackTrace();

            }

        });

    }
}
