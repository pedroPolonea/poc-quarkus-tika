package org.acme;

import com.monitorjbl.xlsx.StreamingReader;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class MSExcelParse {
    public static void main(final String[] args) throws IOException, SAXException {

        //detecting the file type
        //BodyContentHandler handler = new BodyContentHandler();
        //Metadata metadata = new Metadata();
        FileInputStream inputstream = new FileInputStream(new File("customers.xlsx"));
       /* ParseContext pcontext = new ParseContext();


        //OOXml parser
        OOXMLParser  msofficeparser = new OOXMLParser ();
        msofficeparser.parse(inputstream, handler, metadata,pcontext);



        System.out.println("Contents of the document:" + handler.toString());
        System.out.println("Metadata of the document:");
        String[] metadataNames = metadata.names();

        for(String name : metadataNames) {
            System.out.println(name + ": " + metadata.get(name));
        }
*/

        StreamingReader reader=null;
        try {
            reader = StreamingReader.builder()
                    .rowCacheSize(100)
                    .bufferSize(4096)
                    .sheetIndex(0)
                    .read(inputstream);
        } catch (Exception e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }finally{
            inputstream.close();
        }
        //pass here to reader and itrate it
        for (Row row : reader) {
            System.out.println("line : " + row.getRowNum());
            if (row.getRowNum()!=0){
                for (Cell cell : row) {
                    System.out.println("value : " + cell.getStringCellValue());
                }

            }
        }

    }
}
