package org.acme;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

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
}
