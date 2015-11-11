import java.util.ArrayList;
import java.util.Collections;
public class KNN {
	/*
	 * Buying(price): vhigh = 4, high = 3, med = 2, low = 1 
	 * Maint(price): vhigh= 4, high = 3, med = 2, low = 1 
	 * Doors: 2, 3, 4, 5more = 5 
	 * Persons: 2, 4, more = 5 
	 * Lub_boot: small = 1, med = 2, big = 3 
	 * Safety: low = 1, med = 2, high = 3 
	 * Decide: unacc = 0, acc = 1
	 */

	static int[][] trainingDataSet = { 
		{ 4, 4, 2, 2, 3, 1, 0 }, 
		{ 1, 2, 5, 5, 1, 2, 1 }, 
		{ 2, 3, 5, 5, 2, 3, 1 }, 
		{ 4, 2, 2, 4, 2, 3, 1 }, 
		{ 1, 1, 4, 2, 1, 1, 0 }, 
		{ 4, 1, 3, 2, 1, 1, 0 }, 
		{ 3, 4, 2, 4, 1, 3, 0 }, 
		{ 2, 1, 4, 4, 1, 2, 1 }, 
		{ 2, 1, 5, 4, 3, 1, 0 }, 
		{ 3, 2, 3, 4, 2, 3, 1 } };
	
	static int[][] testingDataSet = { 
		{ 3, 1, 5, 5, 1, 3, 1 }, 
		{ 2, 4, 2, 2, 3, 1, 0 }, 
		{ 3, 3, 3, 4, 2, 3, 1 }, 
		{ 2, 2, 3, 5, 3, 3, 1 }, 
		{ 4, 2, 2, 4, 2, 2, 0 }, 
		{ 3, 3, 3, 5, 1, 2, 0 }, 
		{ 1, 4, 2, 4, 1, 1, 0 }, 
		{ 4, 1, 2, 5, 3, 3, 1 }, 
		{ 1, 1, 2, 4, 3, 3, 1 }, 
		{ 4, 3, 3, 4, 2, 3, 0 } };

	public static void main(String[] args) {
		ArrayList<Double> distance = new ArrayList<Double>();
		ArrayList<Integer> resultOfK1 = new ArrayList<Integer>();
		ArrayList<Integer> resultOfK3 = new ArrayList<Integer>();
		
		for (int j = 0; j < 10; j++) {
			for (int i = 0; i < 10; i++) {
				//System.out.println("training " + getTrainingData(i));
				//System.out.println("testing " + getTestingData(i));
				distance.add(calculateDistance(getTrainingData(i), getTestingData(j)));
			}
			
			int k = 11+j;
			System.out.print("instance "+ k +": ");
			
			System.out.print("[ ");
			for (int index = 0; index < distance.size(); index++) {
				System.out.print(String.format("%.2f", distance.get(index)) + " ");
			}
			System.out.println("]");
			
			ArrayList<Double> sortList = (ArrayList<Double>) distance.clone();
			Collections.sort(sortList);
			
			System.out.print("k=1: ");
			if(getLastItemOfTrainingData(distance.indexOf(sortList.get(0)))==1){
				System.out.println("acc");
				resultOfK1.add(1);
			}else{
				System.out.println("unacc");
				resultOfK1.add(0);
			}
			
			System.out.print("k=3: ");
			int count = 0;
			for(int q =0; q<3; q++){
				if(getLastItemOfTrainingData(distance.indexOf(sortList.get(q)))==1){
					count++;
				}
			}
			if(count > 1){
				System.out.println("acc");
				resultOfK3.add(1);
			}else{
				System.out.println("unacc");
				resultOfK3.add(0);
			}
			distance.clear();
		}
		
		System.out.println("The error rate of K = 1 is: "+ errorReport(getTestingDataResult(),resultOfK1));
		System.out.println("The error rate of K = 3 is: "+ errorReport(getTestingDataResult(),resultOfK3));
	}

	public static double calculateDistance(ArrayList<Integer> train, ArrayList<Integer> test) {
		double k = 0;
		for (int i = 0; i < train.size(); i++) {
			k = k + (train.get(i) - test.get(i)) * (train.get(i) - test.get(i));
		}
		k = Math.sqrt(k);
		return k;
	}

	public static ArrayList<Integer> getTrainingData(int index) {
		ArrayList<Integer> x = new ArrayList<Integer>();
		for (int i = 0; i < 6; i++) {
			x.add(trainingDataSet[index][i]);
		}
		return x;
	}

	public static ArrayList<Integer> getTestingData(int index) {
		ArrayList<Integer> x = new ArrayList<Integer>();
		for (int i = 0; i < 6; i++) {
			x.add(testingDataSet[index][i]);
		}
		return x;
	}
	
	public static int getLastItemOfTrainingData(int indexOfInstance) {
		return trainingDataSet[indexOfInstance][6];
	}
	
	public static ArrayList<Integer> getTestingDataResult(){
		ArrayList<Integer> a = new ArrayList<Integer>();
		for(int i = 0; i < 10 ; i++){
			a.add(testingDataSet[i][6]);
		}
		return a;
	}
	
	public static float errorReport(ArrayList training, ArrayList testing){
		float total = training.size();
		float different = 0;
		for(int i = 0 ; i < training.size(); i++){
			if(training.get(i)!=testing.get(i)){
				different++;
			}
		}
		return different/total;
	}
}
