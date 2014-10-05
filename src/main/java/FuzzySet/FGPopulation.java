package FuzzySet;

import java.util.Arrays;

public class FGPopulation implements Cloneable {

	FGIndividual individuals[];
	int order;
	int noOfGenes;	
	int ll;
	int ul;
	public  final static  double oldPopulationRatio = 0.7;
	
	//Cross Over related constants
	public  final static  double crossOverPopulationRatio = 0.1;
	public  final static  int crossOverIndex = 3;
	
	//Mutation related constants.
	public  final static  double mutationPopulationRatio = 0.05;
	public   static  int mutationPoint = 3;
	
	public FGPopulation(int pop,int order, int noOfGenes , int ll , int ul){
		individuals = new FGIndividual[pop];
		this.order = order;
		this.noOfGenes = noOfGenes;
		this.ll = ll;
		this.ul = ul; 
	}
	
	public FGPopulation(){
		
	}
	
	
	public FGIndividual[] getIndividuals() {
		return individuals;
	}




	public void setIndividuals(FGIndividual[] individuals) {
		this.individuals = individuals;
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
		
		for(FGIndividual fi : this.getIndividuals()){
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
	
	public void evolvePopulation(){
		
		generateNewIndividuals();
	    performCrossOver();
	    performMutation();
	    //nullifyMSE();
	}
	
	public void generateNewIndividuals(){
		//displayPopulation();
		/*int startIndex= (int) Math.round(this.individuals.length * oldPopulationRatio);
		for(int i=startIndex +1 ; i<individuals.length ; i++){
			individuals[i] = new FGIndividual(ll, ul, noOfGenes + 1);
			
		}*/
		int startIndex= (int) Math.round(this.individuals.length * oldPopulationRatio);
		for(int i=startIndex +1 ; i<individuals.length ; i++){
			individuals[i] = getRandomBestIndiv();
			
		}
		
	}
	
	private FGIndividual getRandomBestIndiv(){
		int lowerL = 0;
		int upperL = individuals.length -1;
		int index1 = lowerL + (int) (Math.random() * ((upperL - lowerL) + 1));
		int index2 = lowerL + (int) (Math.random() * ((upperL - lowerL) + 1));
		/*System.out.println("index selected 1: "+ index1+  " => "+individuals[index1].getMse());
		System.out.println("index selected 2: "+ index2+  " => "+individuals[index2].getMse());*/
		if(individuals[index1].getMse()<individuals[index2].getMse())
			return individuals[index1];
		else
			return individuals[index2];
	}
	
	
	
	public void performCrossOver(){
		int lowerL = 0;
		int upperL = individuals.length -1;
		int crossOverCount = (int) Math.round(this.individuals.length * crossOverPopulationRatio);
		int index1,index2,t;
		while(crossOverCount >=1){
			index1 = lowerL + (int) (Math.random() * ((upperL - lowerL) + 1));
			index2 = lowerL + (int) (Math.random() * ((upperL - lowerL) + 1));
			//Performing Linear Cross Over
			for(int i=crossOverIndex ; i< individuals[index1].getGenes().length ; i++){
				t=individuals[index1].getGenes()[i];
				individuals[index1].getGenes()[i]=individuals[index2].getGenes()[i];
				individuals[index2].getGenes()[i] = t;
			}
			Arrays.sort(individuals[index1].getGenes());
			Arrays.sort(individuals[index2].getGenes());
			crossOverCount --;
		}
	}
	
	public void nullifyMSE(){
		
		for(FGIndividual fi : individuals){
			fi.setMse(0.0);
		}
	}
	
	public void performMutation(){
		int lowerL = individuals.length/2;
		int upperL = individuals.length -1;
		int mutationCount = (int) Math.round(this.individuals.length * mutationPopulationRatio);
		int index1, lowerVal,upperVal;
		
		while(mutationCount >=1){
			index1 = lowerL + (int) (Math.random() * ((upperL - lowerL) + 1));
			//System.out.println("Index Mutated:"+index1);
			lowerVal = individuals[index1].getGenes()[mutationPoint-1];
			upperVal = individuals[index1].getGenes()[mutationPoint+1];
			individuals[index1].getGenes()[mutationPoint] = (lowerVal + upperVal)/2;
			mutationCount --;
		}
	}
	
	public Object clone() {
		// deep copy

		FGPopulation fg = new FGPopulation(this.getIndividuals());
		return fg;

	}
	
	public FGPopulation(FGIndividual []inds){
		
		individuals=new FGIndividual[inds.length];
		int i =0;
		for(FGIndividual fi : inds){
			individuals[i] =(FGIndividual) fi.clone();
			i++;
		}
		
	}
	

	
}
