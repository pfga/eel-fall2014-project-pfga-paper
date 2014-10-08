package FuzzySet;

import java.io.File;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.*;

public class FGA {

    //    private static int lowerLimit = 13000;
//    private static int upperLimit = 20000;
//    static List<FGPopulation> popList = new ArrayList<FGPopulation>();


    public static void run(int popCnt, List<AnnualRecord> annualRecords,
                           int lowerLimit, int upperLimit, int intervals) {
        int gen = 1;
        Long startTime = System.nanoTime();
        //The argument comprises of the population, order, number of Genes
        FGPopulation fgp = new FGPopulation(popCnt, 3, intervals, lowerLimit, upperLimit);
        fgp.generatePopulation();
//        fgp.displayPopulation();
//        FGPopulation tfgp = new FGPopulation(30, 3, 7, lowerLimit, upperLimit);
        while (gen <= 100) {
            fgp.computePopulation(annualRecords);
            fgp.sortPopulation();
            System.out.println("Generation : " + gen + " ==> " + fgp.getFittest().getMse());
            //tfgp = (FGPopulation) fgp.clone();
//            popList.add(tfgp);
            fgp.evolvePopulation();
            gen++;
        }
        Long stopTime = System.nanoTime();
        System.out.println((stopTime - startTime) / 1000000);
    /*
        //Generation Results
          Iterator it = genMap.entrySet().iterator();
		    while (it.hasNext()) {
		        Map.Entry pairs = (Map.Entry)it.next();
		        System.out.println(pairs.getKey() + " = > " + pairs.getValue());
		        it.remove(); // avoids a ConcurrentModificationException
		    }
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

		    */

    }
}