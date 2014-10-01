package FuzzySet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class FuzzySet_backup {

	public static List<AnnualRecord> ar = new ArrayList<AnnualRecord>();
 public static int chromosome[] = {13000,15282,16744,17382,17726,18053,20000};
	public static HashMap<String, String> arMap = new HashMap<String, String>();
	public static HashMap<String, Integer> discourseMap = new HashMap<String, Integer>();

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		initializeData();
		//iniatializeAnnualRecord();
		initializeFuzzySet();
		initializeFLRG(3);
		initializeFLRGMap();
		initializeDiscourseMap();
		System.out.println("\n\n\n\n\n MSE = " + computeForcastedValue());
	}

	public static void initializeData() {

		


		// Test Data
	/*	AnnualRecord ar1 = new AnnualRecord("1971", 1025);
		ar.add(ar1);
		ar1 = new AnnualRecord("1972", 512);
		ar.add(ar1);
		ar1 = new AnnualRecord("1973", 1005);
		ar.add(ar1);
		ar1 = new AnnualRecord("1974", 852);
		ar.add(ar1);
		ar1 = new AnnualRecord("1975", 440);
		ar.add(ar1);
		ar1 = new AnnualRecord("1976", 502);
		ar.add(ar1);
		ar1 = new AnnualRecord("1977", 775);
		ar.add(ar1);
		ar1 = new AnnualRecord("1978", 465);
		ar.add(ar1);
		ar1 = new AnnualRecord("1979", 795);
		ar.add(ar1);
		ar1 = new AnnualRecord("1980", 970);
		ar.add(ar1);
		ar1 = new AnnualRecord("1981", 742);
		ar.add(ar1);
		ar1 = new AnnualRecord("1982", 635);
		ar.add(ar1);
		ar1 = new AnnualRecord("1983", 994);
		ar.add(ar1);
		ar1 = new AnnualRecord("1984", 759);
		ar.add(ar1);
		ar1 = new AnnualRecord("1985", 883);
		ar.add(ar1);
		ar1 = new AnnualRecord("1986", 599);
		ar.add(ar1);
		ar1 = new AnnualRecord("1987", 499);
		ar.add(ar1);
		ar1 = new AnnualRecord("1988", 590);
		ar.add(ar1);
		ar1 = new AnnualRecord("1989", 911);
		ar.add(ar1);
		ar1 = new AnnualRecord("1990", 862);
		ar.add(ar1);
		ar1 = new AnnualRecord("1991", 801);
		ar.add(ar1);
		ar1 = new AnnualRecord("1992", 1067);
		ar.add(ar1);
		ar1 = new AnnualRecord("1993", 917);
		ar.add(ar1);
	*/
		
		AnnualRecord ar1 = new AnnualRecord("1971", 13055);
		ar.add(ar1);
		ar1 = new AnnualRecord("1972", 13563);
		ar.add(ar1);
		ar1 = new AnnualRecord("1973", 13867);
		ar.add(ar1);
		ar1 = new AnnualRecord("1974", 14696);
		ar.add(ar1);
		ar1 = new AnnualRecord("1975", 15460);
		ar.add(ar1);
		ar1 = new AnnualRecord("1976", 15311);
		ar.add(ar1);
		ar1 = new AnnualRecord("1977", 15603);
		ar.add(ar1);
		ar1 = new AnnualRecord("1978", 15861);
		ar.add(ar1);
		ar1 = new AnnualRecord("1979", 16807);
		ar.add(ar1);
		ar1 = new AnnualRecord("1980", 16919);
		ar.add(ar1);
		ar1 = new AnnualRecord("1981", 16388);
		ar.add(ar1);
		ar1 = new AnnualRecord("1982", 15433);
		ar.add(ar1);
		ar1 = new AnnualRecord("1983", 15497);
		ar.add(ar1);
		ar1 = new AnnualRecord("1984", 15145);
		ar.add(ar1);
		ar1 = new AnnualRecord("1985", 15163);
		ar.add(ar1);
		ar1 = new AnnualRecord("1986", 15984);
		ar.add(ar1);
		ar1 = new AnnualRecord("1987", 16859);
		ar.add(ar1);
		ar1 = new AnnualRecord("1988", 18150);
		ar.add(ar1);
		ar1 = new AnnualRecord("1989", 18970);
		ar.add(ar1);
		ar1 = new AnnualRecord("1990", 19328);
		ar.add(ar1);
		ar1 = new AnnualRecord("1991", 19337);
		ar.add(ar1);
		ar1 = new AnnualRecord("1992", 18876);
		ar.add(ar1);
	}

	 public static void iniatializeAnnualRecord(){
		 
		 String filePath = "/home/ankitsirmorya/fuzzy/FGA.xls";
		 try{
		
			 AnnualRecordExcelReader recReader = new AnnualRecordExcelReader(filePath, 0);
			 while(recReader.next()){
				 ar.add(recReader.getAnnualRecord());
			 }
		 }catch(Exception ex){
			 ex.printStackTrace();
		 }
		 
	 }
	
	
	public static void initializeFuzzySet() {

		for (AnnualRecord rec : ar) {
			rec.setFuzzySet("A"
					+ (ceilSearch(chromosome, 0, chromosome.length,
							rec.getVisitors()) + 1));
		}

		for (AnnualRecord rec : ar) {

			System.out.println(rec.getYear() + "=>" + rec.getFuzzySet());
		}

	}

	public static int ceilSearch(int arr[], int low, int high, int x) {

		int mid;

		/* get the index of middle element of arr[low..high] */
		mid = (low + high) / 2; /* low + (high - low)/2 */

		/* If x is same as middle element, then return mid */
		if (arr[mid] == x)
			return mid;

		/*
		 * If x is greater than arr[mid], then either arr[mid + 1] is ceiling of
		 * x or ceiling lies in arr[mid+1...high]
		 */
		else if (arr[mid] < x) {
			if (mid + 1 <= high && x <= arr[mid + 1])
				return mid;
			else
				return ceilSearch(arr, mid + 1, high, x);
		}

		/*
		 * If x is smaller than arr[mid], then either arr[mid] is ceiling of x
		 * or ceiling lies in arr[mid-1...high]
		 */
		else {
			if (mid - 1 >= low && x > arr[mid - 1])
				return mid - 1;
			else
				return ceilSearch(arr, low, mid - 1, x);
		}

	}

	public static void initializeFLRG(int order) {

		// Initialize the left hand relationship group of each Annual Record.

		for (int i = order - 1; i < ar.size(); i++) {

			String lfrg = Utility.EMPTY_STRING;
			// Use when the list of
			/*
			 * if (i == (order - 1)) { lfrg = "#" + Utility.comma + ar.get(i -
			 * 2).getFuzzySet() + Utility.comma + ar.get(i - 1).getFuzzySet(); }
			 * else { lfrg = ar.get(i - 3).getFuzzySet() + Utility.comma +
			 * ar.get(i - 2).getFuzzySet() + Utility.comma + ar.get(i -
			 * 1).getFuzzySet(); }
			 */
			for (int j = i - order; j < i; j++) {
				if (j == -1) {
					lfrg = Utility.hash + Utility.comma;
				} else
					lfrg += ar.get(j).getFuzzySet() + Utility.comma;
			}
			lfrg = lfrg.substring(0, lfrg.length() - 1);
			ar.get(i).setFlrgLH(lfrg);
		}

		for (AnnualRecord rec : ar) {
			System.out.println(rec.getYear() + "=>" + rec.getFlrgLH() + "->"
					+ rec.getFuzzySet());
		}
	}

	public static void initializeFLRGMap() {

		String rHVal = null;

		for (AnnualRecord rec : ar) {

			if (rec.getFlrgLH() != "" && rec.getFlrgLH() != null) {
				if (arMap.containsKey(rec.getFlrgLH())) {
					rHVal = arMap.get(rec.getFlrgLH());
					if (!Utility.commaSeperatedToList(rHVal).contains(
							rec.getFuzzySet())) {
						rHVal += Utility.comma + rec.getFuzzySet();
						arMap.put(rec.getFlrgLH(), rHVal);
					}

				} else {
					arMap.put(rec.getFlrgLH(), rec.getFuzzySet());
				}
			}

		}

		// Initialising the right hand flrg of each record
		for (AnnualRecord rec : ar) {

			rec.setFlrgRH(arMap.get(rec.getFlrgLH()));
		}

		/*
		 * Iterator it = arMap.entrySet().iterator(); while (it.hasNext()) {
		 * Map.Entry pairs = (Map.Entry)it.next();
		 * System.out.println(pairs.getKey() + " = " + pairs.getValue());
		 * it.remove(); // avoids a ConcurrentModificationException }
		 */

	}

	public static void initializeDiscourseMap() {
		System.out.println("Discourse Set");

		for (int i = 0; i < chromosome.length - 1; i++) {
			discourseMap.put("A" + (i + 1),
					(int) Math.ceil((chromosome[i] + chromosome[i + 1]) / 2));
		}
		
		Iterator it = discourseMap.entrySet().iterator(); 
		while (it.hasNext()) {
			 Map.Entry pairs = (Map.Entry)it.next();
			  System.out.println(pairs.getKey() + " = " + pairs.getValue());
		}
	}

	public static double computeForcastedValue() {
		System.out.println("Computing Forcasted Value starts");

		for (AnnualRecord rec : ar) {
			if (Utility.isEmpty(rec.getFlrgRH()))
				continue;
			int sum = 0, n = 0;
			for (String s : Utility.commaSeperatedToList(rec.getFlrgRH())) {
				sum += discourseMap.get(s);
				n++;
			}
			rec.setFcVisitors(sum / n);
		}

		double fcSum = 0.0;
		int n = 0;
		System.out.println("Counting Visitors....\n\n\n");
		for (AnnualRecord rec : ar) {
			System.out.println(rec.getYear() + "***"+rec.getFlrgLH() + "+++"
					+ rec.getFlrgRH() + "=>" + rec.getFcVisitors());
			if (rec.getFcVisitors() != 0) {
				fcSum += Math.pow(rec.getFcVisitors() - rec.getVisitors(), 2);
				n++;
			}

		}

		return fcSum / n;

	}

}
