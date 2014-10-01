package FuzzySet;

import java.io.File;
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

public class FGA {

	private static int lowerLimit = 13000;
	private static int upperLimit = 20000;
	
	public static List<AnnualRecord> ar = new ArrayList<AnnualRecord>();
	
	public static void main(String[] args) {

		int gen = 1;
		//The aregument comprises of the population, order, number of Genes
		FGPopulation fgp = new FGPopulation(50,3,6,lowerLimit, upperLimit);
		fgp.generatePopulation();
		Map<String, Double> genMap = new LinkedHashMap<String, Double>();
		fgp.computePopulation();
		fgp.sortPopulation();
		
		double lowest=fgp.individuals[0].getMse();
		
		while (gen <= 100) {
			System.out.print("Generation : "+gen+" =>");
			if(gen>1){
				fgp.computePopulation();
				fgp.sortPopulation();
				if(fgp.individuals[0].getMse()<lowest){
					lowest = fgp.individuals[0].getMse();
				}
				//	fgp.displayPopulation();
			}
			
			
			//fgp.displayPopulation();
			//System.out.println("\n\n\n\n\n\n\n\nGet Fittest: "+fgp.getFittest()+"\n\n\n\n\n\n");
			genMap.put("Generation : "+gen, fgp.getFittest());
			
			//fgp.displayPopulation();
			System.out.print(fgp.individuals[0].getMse()+"\t");
			
			/*for(int i : fgp.individuals[0].getGenes()){
				System.out.print(i+"\t");
			}
			*/
			System.out.println();
			fgp.evolvePopulation();
			
			//fgp.displayPopulation();
			gen++;
		}
		
				
		
	/*	//Generation Results
		  Iterator it = genMap.entrySet().iterator();
		    while (it.hasNext()) {
		        Map.Entry pairs = (Map.Entry)it.next();
		        //System.out.println(pairs.getKey() + " = > " + pairs.getValue());
		        it.remove(); // avoids a ConcurrentModificationException
		    }
		    */
		 String uploadPath = "/home/ankit/Genetic Algorithm";
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
			        ////System.out.println(pairs.getKey() + " = > " + pairs.getValue());
			        writer.println(pairs.getKey() + " = > " + pairs.getValue());
			        it.remove(); // avoids a ConcurrentModificationException
			    }
	         
		 }catch(Exception ex){
			 ex.printStackTrace();
			 
		 }finally{
			 writer.flush();
		 }
		
		
	}

}
