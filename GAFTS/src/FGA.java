package FuzzySet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class FGA {

	private static int lowerLimit = 13000;
	private static int upperLimit = 20000;
	static	List<FGPopulation>  popList  = new ArrayList<FGPopulation>();
	
	
	
	public static List<AnnualRecord> ar = new ArrayList<AnnualRecord>();
	
	public static void main(String[] args) {

		int gen = 1;
		//The aregument comprises of the population, order, number of Genes
		FGPopulation fgp = new FGPopulation(30,3,7,lowerLimit, upperLimit);
		fgp.generatePopulation();
		Map<String, Double> genMap = new LinkedHashMap<String, Double>();
		FGPopulation tfgp  = new FGPopulation(30,3,7,lowerLimit, upperLimit);
		FGIndividual []individuals;
		while (gen <= 100) {
			fgp.computePopulation();
			fgp.sortPopulation();
			System.out.println("Generation : "+gen + " ==> "+fgp.getFittest());
			genMap.put("Generation : "+gen, fgp.getFittest());
			//tfgp = (FGPopulation) fgp.clone();
			popList.add(tfgp);
			fgp.evolvePopulation();
			gen++;
		}
	/*	//Generation Results
		  Iterator it = genMap.entrySet().iterator();
		    while (it.hasNext()) {
		        Map.Entry pairs = (Map.Entry)it.next();
		        System.out.println(pairs.getKey() + " = > " + pairs.getValue());
		        it.remove(); // avoids a ConcurrentModificationException
		    }
		    */
		 String uploadPath = "/home/ankit/studies/uf/first_sem/cloud_computing/Main_Project/eel-fall2014-project-pfga/results";
		 String time =
		            new SimpleDateFormat("yyyyMMdd_hhmmss").format(new Date());
		 String fileName = "GeneticAlgorithm"+time+".txt";
		 PrintWriter writer = null;
		 try{
			// Create directory
	         new File(uploadPath).mkdir();
	         File GenerationResults = new File(uploadPath, fileName);
	         writer = new PrintWriter(GenerationResults); 
	         
	         
	       //Generation Results
			  Iterator it = genMap.entrySet().iterator();
			    while (it.hasNext()) {
			        Map.Entry pairs = (Map.Entry)it.next();
			        //System.out.println(pairs.getKey() + " = > " + pairs.getValue());
			        writer.println(pairs.getKey() + " = > " + pairs.getValue());
			        it.remove(); // avoids a ConcurrentModificationException
			    }
			    
			   // generateExcelForPopulation(); 
	         
		 }catch(Exception ex){
			 ex.printStackTrace();
			 
		 }finally{
			 writer.flush();
		 }
		
		
	}
	
	public static void generateExcelForPopulation() throws IOException{
		
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet();
    	int columnIndex=0;    	
    	int rowIndex=0;
    	
    	HSSFCellStyle headerStyle = Utility.getHeaderStyle(wb);
        HSSFCellStyle textStyle = Utility.getTextStyle(wb);
        HSSFCell cell = null;
        for(FGPopulation pop : popList){
        for(FGIndividual fi : pop.individuals){
        	HSSFRow row = sheet.createRow(rowIndex++);
    		columnIndex=0;	
    		for(int i : fi.getGenes()){
    			cell = row.createCell(columnIndex++);
    			cell.setCellValue(i);
    			sheet.autoSizeColumn(columnIndex-1);    
    			if (rowIndex-1==0) { 
        			cell.setCellStyle(headerStyle);
        		} else {
        			cell.setCellStyle(textStyle);
        		} 
    		}
    		cell = row.createCell(columnIndex);
        	cell.setCellValue(fi.getMse());
        }
        }
        
        String uploadPath = "/home/ankitsirmorya/Genetic Algorithm/excels";
		 String time =
		            new SimpleDateFormat("yyyyMMdd_hhmmss").format(new Date());
		 String fileName = "GeneticAlgorithm"+time+".xls";
			// Create directory
         new File(uploadPath).mkdir();
         if (!fileName.equals("")) {
             File uploadFileObject =
                 new File(uploadPath, fileName.toString());
             FileOutputStream fileOutStream =
                 new FileOutputStream(uploadFileObject);
             wb.write(fileOutStream);
             fileOutStream.flush();
             fileOutStream.close();
         }
    	
	}

}
