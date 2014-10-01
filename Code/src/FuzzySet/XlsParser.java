package FuzzySet;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;


/**
 *
 *
 */
public class XlsParser {

    /**
     *
     */
    public XlsParser() {

    }

    /**
     * @param path
     * @param sheetNumber
     * @return
     */
    public static List<List<String>> tokenize(String path, int sheetNumber)
        throws IOException {
        List<List<String>> tokens = new ArrayList<List<String>>();
        List<String> rowTokens = new ArrayList<String>();
        FileInputStream fs = null;
        HSSFWorkbook wb = null;
        HSSFSheet sheet = null;
        HSSFRow row = null;
        int sheetLastRowNumber = 0;
        int sheetLastColumnNumber = 0;
        int totalNumberOfColumns = 0;
        int columnNum = 0;

        fs = new FileInputStream(path);
        wb = new HSSFWorkbook(fs);
        sheet = wb.getSheetAt(sheetNumber);
        sheetLastRowNumber = sheet.getLastRowNum();
        // Skip Header
        // Get the total number of actual columns from the header.
        if (sheetLastRowNumber > -1) {
            row = sheet.getRow(0);
            totalNumberOfColumns = row.getLastCellNum();
        }
        // Iterate over all the rows and add the data.
        for (int rowNum = 1; rowNum <= sheetLastRowNumber; rowNum++) {
            row = sheet.getRow(rowNum);
            sheetLastColumnNumber = row.getLastCellNum();
            rowTokens = new ArrayList<String>();
            for (columnNum = 0; columnNum <= sheetLastColumnNumber; columnNum++) {
                rowTokens.add(Utility.getValueFromCell(row, columnNum)
                    .trim());
            }
            // Add empty columns
            while (columnNum++ < totalNumberOfColumns) {
                rowTokens.add("");
            }
            tokens.add(rowTokens);
        }

        return tokens;
    }
    
    /**
     * @param path
     * @param sheetNumber
     * @return
     * @throws IOException
     */
    public static List<List<String>> tokenizeProgramDirectory(String path,
        int sheetNumber) throws IOException {
        List<List<String>> tokens = new ArrayList<List<String>>();
        List<String> rowTokens = new ArrayList<String>();
        FileInputStream fs = null;
        HSSFWorkbook wb = null;
        HSSFSheet sheet = null;
        HSSFRow row = null;
        int sheetLastRowNumber = 0;
        int sheetLastColumnNumber = 0;
        int totalNumberOfColumns = 0;
        int columnNum = 0;

        fs = new FileInputStream(path);
        wb = new HSSFWorkbook(fs);
        sheet = wb.getSheetAt(sheetNumber);
       sheetLastRowNumber = sheet.getLastRowNum();
        // Skip Header
        // Get the total number of actual columns from the header.
        if (sheetLastRowNumber > -1) {
            row = sheet.getRow(0);
            totalNumberOfColumns = row.getLastCellNum();
        }
        // Iterate over all the rows and add the data. Here we start from the 2nd row of the excel whose rownum =1
        for (int rowNum = 1; rowNum <= sheetLastRowNumber; rowNum++) {
            row = sheet.getRow(rowNum);
           // sheetLastColumnNumber = row.getLastCellNum();
            rowTokens = new ArrayList<String>();
            for (columnNum = 0; columnNum <= totalNumberOfColumns; columnNum++) {
                rowTokens.add(Utility.getValueFromCell(row, columnNum)
                    .trim());
            }
            
            tokens.add(rowTokens);
        }

        return tokens;
    }

}