import java.io.BufferedReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

import weka.clusterers.SimpleKMeans;
import weka.core.EuclideanDistance;
import weka.core.Instances;

public class KMeans {
	public static BufferedReader readDataFile(String filename) {
		BufferedReader inputReader = null;
		try {
			inputReader = new BufferedReader(new FileReader(filename));
		} catch(FileNotFoundException e) {
			System.err.println("File not found: " + filename);
		}
		return inputReader;
	}
	
	/**
	 * Returns score of individual content i.e. distance of content from
	 * it's cluster centroid
	 */
	public static ArrayList<Double> getScore(SimpleKMeans kmeans, Instances data, int assignments[]){
		ArrayList<Double> score = new ArrayList<Double>();
		EuclideanDistance Dist = (EuclideanDistance)kmeans.getDistanceFunction();
		Instances centroid = kmeans.getClusterCentroids();
		System.out.println();
		for(int i = 0; i < centroid.size(); i++){
			System.out.println(centroid.instance(i));
		}
		for(int i = 0; i < assignments.length; i++){
			score.add(100 - (Dist.distance(centroid.instance(assignments[i]), data.instance(i)) * 100) * 0.5);
		}
		return score;
	}
	
	/**
	 *Utility function to compute Davies Bouldin index 
	 */
	public static double sFunction(int index, int num_cluster, SimpleKMeans kmeans, Instances data, int assignments[]){
		double s = 0;
		Instances centroid = kmeans.getClusterCentroids();
		EuclideanDistance Dist = (EuclideanDistance)kmeans.getDistanceFunction();
		double c = 0;
		double distance = 0;
		for(int i = 0; i < assignments.length; i++){
			if(assignments[i] == index){
				c++;
				distance += (Dist.distance(centroid.get(index), data.get(i)));
			}
		}
		s = (distance / c);
		return s;
	}
	
	/**
	 *Utility function to compute Davies Bouldin index 
	 */
	public static double dFunction(int i, int j, SimpleKMeans kmeans){
		double d = 0;
		Instances centroid = kmeans.getClusterCentroids();
		EuclideanDistance Dist = (EuclideanDistance)kmeans.getDistanceFunction();
		d = Dist.distance(centroid.get(i), centroid.get(j));
		return d;
	}
		
	/**
	 *Function to compute Davie Bouldin Index which is a performance metric
	 *to evaluate clustering algorithm. 
	 */
	public static double davieBouldinIndex(int num_cluster, SimpleKMeans kmeans, Instances data, int assignments[]){
		double dbIndex = 0;
		double R_i[] = new double[num_cluster];
		double max , R_ij, s_i, s_j, d_ij;
		for(int i = 0; i < num_cluster; i++){
			max = -20000;
			s_i = sFunction(i, num_cluster, kmeans, data, assignments);
			for(int j = 0; j < num_cluster; j++){
				if(i != j){
					s_j =  sFunction(j, num_cluster, kmeans, data, assignments);
					d_ij = dFunction(i, j, kmeans);
					R_ij = (s_i + s_j) / d_ij;
					if(max < R_ij)
						max = R_ij;
				}
			}
			R_i[i] = max;
		}
		for(int i = 0; i < num_cluster; i++){
			dbIndex += R_i[i];
		}
		dbIndex /= num_cluster;
		return dbIndex;
	}
	
	/**
	 *Cluster are formed and computed here and evaluated 
	 */	
	public static void main(String args[]) throws Exception {
		long start = System.currentTimeMillis();
		int num_cluster = 6;
		SimpleKMeans kmeans = new SimpleKMeans();
		kmeans.setSeed(10);
		kmeans.setPreserveInstancesOrder(true);
		kmeans.setNumClusters(num_cluster);
		BufferedReader datafile = readDataFile("G:/Academics/Semester 7/Data mining and Data Warehousing/Practicals/Clustering/NewData_ARFF.arff"); 
		Instances data = new Instances(datafile);
		
		kmeans.buildClusterer(data);
		int assignments[] = kmeans.getAssignments();
		int arr[] = new int[num_cluster];
		int i=0;
		for(int clusterNum : assignments) {
		    System.out.print((int)(data.instance(i).value(0)) + " ");
			System.out.printf("Instance %d -> Cluster %d \n", i, clusterNum);
		    i++;
		    arr[clusterNum]++;
		}
	    long end = System.currentTimeMillis();
	    System.out.println("Time Taken: " + (end - start));
	    System.out.println(davieBouldinIndex(num_cluster, kmeans, data, assignments));
	}
}
