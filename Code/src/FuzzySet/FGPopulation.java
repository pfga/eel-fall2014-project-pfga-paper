package FuzzySet;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collections;

public class FGPopulation {

	FGIndividual individuals[];
	int order;
	int noOfGenes;	
	int ll;
	int ul;
	//public  final static  double oldPopulationRatio = 0.8;
	
	//Cross Over related constants
	public  final static  double crossOverPopulationRatio = 0.9;
	public   static  int crossOverIndex = 0;
	
	//Mutation related constants.
	public  final static  double mutationPopulationRatio = 0.03;
	public  static  int mutationPoint = 3;
	
	/*public static void main(String[] args) {
		FGPopulation fgp = new FGPopulation(13000, 20000, 30, 7,3);
		fgp.displayPopulation();
	}*/
	public FGPopulation(int pop,int order, int noOfGenes , int ll , int ul){
		individuals = new FGIndividual[pop];
		this.order = order;
		this.noOfGenes = noOfGenes;
		this.ll = ll;
		this.ul = ul; 
	}
	
	
	public void generatePopulation() {

		for (int i = 0; i < size(); i++) {
			FGIndividual fi = new FGIndividual(ll, ul, noOfGenes + 1);
			saveIndividual(i, fi);
		}
	}
	
	public void computePopulation(){
		
		for(FGIndividual fi : individuals){
			FuzzySet fs = new FuzzySet(fi.getGenes(), order);
			fi.setMse(fs.computeForcastedValue());
		}
	}
	
	
	public int size() {
		return individuals.length;
	}

	// Save individual
	public void saveIndividual(int index, FGIndividual indiv) {
		individuals[index] = indiv;
	}
	
	public void displayPopulation(){
		
		for(FGIndividual fi : individuals){
			for(int i: fi.getGenes()){
				System.out.print(i+"\t");
			}
			System.out.println("==>"+fi.getMse());
			
		}
	}

	public void sortPopulation() {

		Arrays.sort(this.individuals, new FGIndividualComparator());
	}

	public double getFittest(){
		
		double d=individuals[0].getMse();
		for(int i =1; i< individuals.length;i++){
			if(individuals[i].getMse()<d)
				d= individuals[i].getMse();
		}
		return d;
	}
	
	/*public void evolvePopulation(){
		//nullifyMSE();
		System.out.println("Before Tournament");
		displayPopulation();
		generateNewIndividuals();
		computePopulation();
		sortPopulation();
		System.out.println("After tournament");
		displayPopulation();
	   performCrossOver();
	   computePopulation();
		sortPopulation();
	   System.out.println("After Cross-over");
	   displayPopulation();
	   System.out.println("After Mutation");
	   performMutation();
	   computePopulation();
		sortPopulation();
	   displayPopulation();
	}*/
	
	public void evolvePopulation(){
		nullifyMSE();
		

		generateNewIndividuals();
		
	     performCrossOver();
	   
	  
	  //  performMutation();
	   
	}
	
	public void generateNewIndividuals(){
		//displayPopulation();
		/*int startIndex= (int) Math.round(this.individuals.length * oldPopulationRatio);
		for(int i=startIndex +1 ; i<individuals.length ; i++){
			individuals[i] = new FGIndividual(ll, ul, noOfGenes + 1);
			
		}*/
		FGIndividual newIndividuals[] = new FGIndividual[individuals.length];
		for(int i=0;i<individuals.length;i++){
			
			newIndividuals[i] = getRandomBestIndiv(); 
		}
		
		individuals = newIndividuals;
		
	}
	
	public void performCrossOver(){
		//System.out.println("Display Population before");
		//this.displayPopulation();
		int lowerL = 0;
		int upperL = individuals.length -1;
		int crossOverCount = (int) Math.round(this.individuals.length * crossOverPopulationRatio);
		int index1,index2,t;
		//Randomizing cross over point
		
		while(crossOverCount >=1){
			index1 = lowerL + (int) (Math.random() * ((upperL - lowerL) + 1));
			index2 = lowerL + (int) (Math.random() * ((upperL - lowerL) + 1));
			
			//Performing Linear Cross Over
			crossOverIndex = (int) (Math.random() * ((individuals[0].getGenes().length-1)  + 1));
			//System.out.println("Index : "+ index1 + " replaced by :"+index2+"at index"+crossOverIndex);
			for(int i=crossOverIndex ; i< individuals[index1].getGenes().length ; i++){
				t=individuals[index1].getGenes()[i];
				individuals[index1].getGenes()[i]=individuals[index2].getGenes()[i];
				individuals[index2].getGenes()[i] = t;
			}
			//Sorting after cross over
			Arrays.sort(individuals[index1].getGenes());
			Arrays.sort(individuals[index2].getGenes());
			crossOverCount --;
		}
		//System.out.println("Display Population after");
		//this.displayPopulation();
	}
	
	public void nullifyMSE(){
		
		for(FGIndividual fi : individuals){
			fi.setMse(0.0);
		}
	}
	
	public void performMutation(){
		//System.out.println("Mutation Begins:");
		//computePopulation();
		//displayPopulation();
		int lowerL = (int) (individuals.length*0.75);
		int upperL = individuals.length -1;
		int mutationCount = (int) Math.round(this.individuals.length * mutationPopulationRatio);
		int index1, lowerVal,upperVal;
		//Randomizing mutation point
		/*mutationPoint =  (int) (Math.random() * ((individuals[0].getGenes().length-1)  + 1));
		while(mutationPoint ==0 && mutationPoint == (individuals[0].getGenes().length-1) ){
			mutationPoint =  (int) (Math.random() * ((individuals[0].getGenes().length-1)  + 1));
		}
		
		if(mutationPoint == 0){
			mutationPoint ++;
		}
		
		if(mutationPoint == individuals[0].getGenes().length){
			mutationPoint = individuals[0].getGenes().length -1;
		}
		*/
		
		
		while(mutationCount >=1){
			index1 = lowerL + (int) (Math.random() * ((upperL - lowerL) + 1));
			mutationPoint = 1+ (int) (Math.random() * ((individuals[0].getGenes().length-3)  + 1));
			lowerVal = individuals[index1].getGenes()[mutationPoint-1];
			upperVal = individuals[index1].getGenes()[mutationPoint+1];
			//System.out.println("Index Mutated:"+index1+"by"+mutationPoint);
			individuals[index1].getGenes()[mutationPoint] = lowerVal + (int) (Math.random() * ((upperVal - lowerVal) + 1));
			mutationCount --;
		}
		//System.out.println("Mutation Ends:");
		//computePopulation();
		//displayPopulation();
	}
	
	private FGIndividual getRandomBestIndiv(){
		int lowerL = 0;
		int upperL = (int) (individuals.length * 0.75) ;
		int index1 = lowerL + (int) (Math.random() * ((upperL - lowerL) + 1));
		int index2 = lowerL + (int) (Math.random() * ((upperL - lowerL) + 1));
		/*System.out.println("index selected 1: "+ index1+  " => "+individuals[index1].getMse());
		System.out.println("index selected 2: "+ index2+  " => "+individuals[index2].getMse());*/
		if(individuals[index1].getMse()<individuals[index2].getMse())
			return individuals[index1];
		else
			return individuals[index2];
	}
	
}
