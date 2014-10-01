package FuzzySet;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;


public class Utility {

	public static String hash = "#";
	public static String comma = ",";
	public static String EMPTY_STRING = "";
	
	public static List<String> commaSeperatedToList(String str){
		
		return Arrays.asList(str.split("\\s*,\\s*"));
	}
	
	/**
     * This checks any String whether its emtpty.
     * 
     * @param stringObject
     * @return boolean
     */

    protected static boolean isEmpty(String stringObject) {
        boolean returnCode = true;

        if ((stringObject != null) && (stringObject.trim().length() > 0)) {
            returnCode = false;
        }

        return returnCode;
    }
    
    /**
     * This method is used to get the value from a workbook sheet cell as a
     * string.
     * 
     * @param row
     * @param index
     * 
     * @deprecated This method should not be used now since we have started
     *             using poi v3.7. We should use the generic type Row instead of
     *             HSSHRow
     * @deprecated
     * @return
     */
    @Deprecated
    public static String getValueFromCell(HSSFRow row, int index) {
        String cellValue = EMPTY_STRING;
        HSSFCell cell = null;
        if (null != row) {
            cell = row.getCell(index);
            if (null == cell || cell.getCellType() == HSSFCell.CELL_TYPE_BLANK) {
                cellValue = EMPTY_STRING;
            } else {
                switch (cell.getCellType()) {
                    case HSSFCell.CELL_TYPE_STRING :
                        cellValue = cell.getRichStringCellValue().getString();
                        break;
                    case HSSFCell.CELL_TYPE_NUMERIC :
                                           
                        cellValue =
                                String.valueOf((long) cell
                                    .getNumericCellValue());
                        break;
                    case HSSFCell.CELL_TYPE_BOOLEAN :
                        cellValue = String.valueOf(cell.getBooleanCellValue());
                        break;
                    case HSSFCell.CELL_TYPE_FORMULA :
                        cellValue = cell.getCellFormula();
                        break;
                    default :
                        cellValue = cell.getRichStringCellValue().getString();
                }
            }
        }
        // Trim the cell contents
        if (!isEmpty(cellValue)) {
            cellValue = cellValue.trim();
        }
        return cellValue;
    }
	
}
