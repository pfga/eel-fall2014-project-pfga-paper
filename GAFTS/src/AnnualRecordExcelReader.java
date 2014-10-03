package FuzzySet;

import java.io.IOException;
import java.util.List;


import FuzzySet.XlsParser;

public class AnnualRecordExcelReader {

	private List<List<String>> tokens;
	private AnnualRecord ar;
	private int currentIndex = 0;
	
	public AnnualRecordExcelReader(String filePath,int sheetNo) throws IOException{
	
		 this.tokens = XlsParser.tokenizeProgramDirectory(filePath, sheetNo);
		 this.ar= new AnnualRecord();
	}
	
	public boolean next() {
		 if (this.tokens.size() > this.currentIndex) {
			 this.ar= new AnnualRecord();
	            this.fetchNextRow();
	            this.currentIndex++;
	            return true;
	        }
	        return false;
	}
	
	private void fetchNextRow() {
		 List<String> currentRow = this.tokens.get(this.currentIndex);
		 this.ar.setYear(currentRow.get(0));
		 this.ar.setVisitors(Integer.parseInt(currentRow.get(1)));
		 
	}
	
	public AnnualRecord getAnnualRecord(){
		return this.ar;
	}
}
