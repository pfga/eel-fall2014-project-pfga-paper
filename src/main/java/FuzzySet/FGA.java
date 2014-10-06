package FuzzySet;

import java.io.File;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.*;

public class FGA {

    //    private static int lowerLimit = 13000;
//    private static int upperLimit = 20000;
    static List<FGPopulation> popList = new ArrayList<FGPopulation>();


    public static void run(List<AnnualRecord> annualRecords,
                           int lowerLimit, int upperLimit) {
        int gen = 1;
        //The aregument comprises of the population, order, number of Genes
        FGPopulation fgp = new FGPopulation(30, 3, 7, lowerLimit, upperLimit);
        fgp.generatePopulation();
        Map<String, Double> genMap = new LinkedHashMap<String, Double>();
        FGPopulation tfgp = new FGPopulation(30, 3, 7, lowerLimit, upperLimit);
        FGIndividual[] individuals;
        while (gen <= 100) {
            fgp.computePopulation(annualRecords);
            fgp.sortPopulation();
            System.out.println("Generation : " + gen + " ==> " + fgp.getFittest());
            genMap.put("Generation : " + gen, fgp.getFittest());
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
        String fileName = "GeneticAlgorithm" + time + ".txt";
        PrintWriter writer = null;
        try {
            // Create directory
            new File(uploadPath).mkdir();
            File GenerationResults = new File(uploadPath, fileName);
            writer = new PrintWriter(GenerationResults);


            //Generation Results
            Iterator it = genMap.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry pairs = (Map.Entry) it.next();
                //System.out.println(pairs.getKey() + " = > " + pairs.getValue());
                writer.println(pairs.getKey() + " = > " + pairs.getValue());
                it.remove(); // avoids a ConcurrentModificationException
            }

            // generateExcelForPopulation();

        } catch (Exception ex) {
            ex.printStackTrace();

        } finally {
            writer.flush();
        }


    }
}
