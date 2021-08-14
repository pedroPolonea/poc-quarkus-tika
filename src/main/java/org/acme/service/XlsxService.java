package org.acme.service;

import com.monitorjbl.xlsx.StreamingReader;
import io.smallrye.openapi.runtime.util.StringUtil;
import org.acme.domain.ClientDTO;
import org.acme.domain.CustomersEnum;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@ApplicationScoped
public class XlsxService {
    private Logger log = LoggerFactory.getLogger(XlsxService.class);

    public ByteArrayInputStream buildXlsxCustomersTemplate() {
        log.info("M=buildXlsxCustomersTemplate, I=Entrada");

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Customers");

        Font font = workbook.createFont();
        font.setColor(IndexedColors.BLUE.index);
        font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);

        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setFont(font);

        Row row = sheet.createRow(0);

        for (CustomersEnum anEnum : CustomersEnum.values()) {
            Cell cell = row.createCell(anEnum.getPosition());
            cell.setCellValue(anEnum.getName());
            cell.setCellStyle(cellStyle);
        }

        ByteArrayOutputStream baOutS = new ByteArrayOutputStream();
        try {
            workbook.write(baOutS);
        } catch (IOException e) {
            log.error("M=buildXlsxCustomersTemplate, E=Vixxi foi nao");
            e.printStackTrace();
        }

        return new ByteArrayInputStream(baOutS.toByteArray());
    }

    public List<ClientDTO> up(final MultipartFormDataInput file){
        log.info("M=up, I=Entrada");

        final Map<String, List<InputPart>> formDataMap = file.getFormDataMap();
        final List<InputPart> uploadfile = formDataMap.get("uploadfile");
        List<ClientDTO> clientList = new ArrayList<>();
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
                        ClientDTO clientDTO = new ClientDTO();
                        clientDTO.setLine(row.getRowNum());
                        clientDTO.setName(row.getCell(CustomersEnum.NAME.getPosition()).getStringCellValue());
                        clientDTO.setHash(row.getCell(CustomersEnum.HASH.getPosition()).getStringCellValue());
                        clientDTO.setAge(Integer.parseInt(row.getCell(CustomersEnum.AGE.getPosition()).getStringCellValue()));

                        clientList.add(clientDTO);
                    }
                }
            } catch (IOException e) {
                log.error("M=up, E=Vixxi");
                e.printStackTrace();

            }

        });

        log.info("M=up, I=Carga realizada, clientList={}", clientList);

        return clientList;
    }


    public ByteArrayInputStream buildXlsxCustomersError(
            final Map<Integer, String> clientMapError,
            final List<ClientDTO> clientList
        ) {
        log.info("M=buildXlsxCustomersError, I=Entrada");

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Customers");

        createHeader(sheet, workbook);
        CellStyle backgroundStyle = createBackgroundStyle(workbook);

        for (ClientDTO clientDTO : clientList) {
            Row line = sheet.createRow(clientDTO.getLine());
            final String msgError = clientMapError.get(clientDTO.getLine());
            CellStyle style = StringUtil.isNotEmpty(msgError)?backgroundStyle:null;

            Cell cellName = line.createCell(CustomersEnum.NAME.getPosition());
            cellName.setCellValue(clientDTO.getName());
            cellName.setCellStyle(style);
            sheet.autoSizeColumn(CustomersEnum.NAME.getPosition());

            Cell cellHash = line.createCell(CustomersEnum.HASH.getPosition());
            cellHash.setCellValue(clientDTO.getHash());
            cellHash.setCellStyle(style);

            Cell cellAge = line.createCell(CustomersEnum.AGE.getPosition());
            cellAge.setCellValue(clientDTO.getAge());
            cellAge.setCellStyle(style);

            if (StringUtil.isNotEmpty(msgError)) {
                Cell cellError = line.createCell(3);
                cellError.setCellValue(msgError);
                cellError.setCellStyle(backgroundStyle);
                sheet.autoSizeColumn(3);
            }
        }

        ByteArrayOutputStream baOutS = new ByteArrayOutputStream();
        try {
            workbook.write(baOutS);
        } catch (IOException e) {
            log.error("M=buildXlsxCustomersTemplate, E=Vixxi foi nao");
            e.printStackTrace();
        }

        return new ByteArrayInputStream(baOutS.toByteArray());
    }

    private void createHeader(final Sheet sheet, final Workbook workbook) {
        log.info("M=createHeader, I=Entrada");
        final CellStyle headerStyle = createHeaderStyle(workbook);
        Row row = sheet.createRow(0);

        for (CustomersEnum anEnum : CustomersEnum.values()) {
            Cell cell = row.createCell(anEnum.getPosition());
            cell.setCellValue(anEnum.getName());
            cell.setCellStyle(headerStyle);
        }

        Cell cell = row.createCell(3);
        cell.setCellValue("Erro");
        cell.setCellStyle(headerStyle);

    }

    private CellStyle createHeaderStyle(final Workbook workbook){
        log.info("M=createHeaderStyle, I=Entrada");

        Font font = workbook.createFont();
        font.setColor(IndexedColors.BLUE.index);
        font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);

        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setFont(font);

        return cellStyle;
    }

    private CellStyle createBackgroundStyle(final Workbook workbook) {
        log.info("M=createBackgroundStyle, I=Entrada");

        CellStyle backgroundStyle = workbook.createCellStyle();
        backgroundStyle.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
        backgroundStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);

        backgroundStyle.setBorderBottom(CellStyle.BORDER_THIN);
        backgroundStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        backgroundStyle.setBorderLeft(CellStyle.BORDER_THIN);
        backgroundStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        backgroundStyle.setBorderRight(CellStyle.BORDER_THIN);
        backgroundStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
        backgroundStyle.setBorderTop(CellStyle.BORDER_THIN);
        backgroundStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());

        return backgroundStyle;
    }
}
