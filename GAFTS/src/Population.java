package FuzzySet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


import FuzzySet.Utility;

public class Population {
	
	public static List<AnnualRecord>  ar = new ArrayList<AnnualRecord>();
	public HashMap<String, String> arMap = new HashMap<String, String>();
	
	public Population(){
		
	}
	public Population(List<AnnualRecord> ar){
		Population.ar = ar;
		new Population().initializeFLRGMap();
	}
	
		
	public void initializeFLRGMap(){
		
		String rHVal = null;
		
		//Test Data
		/*AnnualRecord ar1= new AnnualRecord("#,A1,A2", "A1");
		ar.add(ar1);
		ar1= new AnnualRecord("A1,A1,A2", "A2");
		ar.add(ar1);
		ar1= new AnnualRecord("A1,A1,A2", "A3");
		ar.add(ar1);
		ar1= new AnnualRecord("A1,A2,A2", "A4");
		ar.add(ar1);
		ar1= new AnnualRecord("A1,A2,A2", "A5");
		ar.add(ar1);
		ar1= new AnnualRecord("A1,A2,A2", "A5");
		ar.add(ar1);
		ar1= new AnnualRecord("A1,A1,A2", "A6");
		ar.add(ar1);
		ar1= new AnnualRecord("", "A6");
		ar.add(ar1);*/
		
		for(AnnualRecord rec : ar){
			
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
		
		 Iterator it = arMap.entrySet().iterator();
		    while (it.hasNext()) {
		        Map.Entry pairs = (Map.Entry)it.next();
		        System.out.println(pairs.getKey() + " = " + pairs.getValue());
		        it.remove(); // avoids a ConcurrentModificationException
		    }
		
	}

}
