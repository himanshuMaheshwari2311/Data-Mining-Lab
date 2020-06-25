import java.awt.Container;
import java.awt.GridLayout;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

import javax.swing.JFrame;

import weka.core.Instances;
import weka.clusterers.HierarchicalClusterer;
import weka.core.EuclideanDistance;
import weka.gui.hierarchyvisualizer.HierarchyVisualizer;

public class HierarchialClustering {
	public static BufferedReader readDataFile(String filename) {
		BufferedReader inputReader = null;
		try {
			inputReader = new BufferedReader(new FileReader(filename));
		} catch(FileNotFoundException e) {
			System.err.println("File not found: " + filename);
		}
		return inputReader;
	}
	
	public static void main(String args[]) throws Exception {
		long start = System.currentTimeMillis();
		int num_cluster = 6;
		HierarchicalClusterer hc = new HierarchicalClusterer();
		hc.setNumClusters(num_cluster);
		hc.setOptions(new String[] {"-L", "COMPLETE"});
		hc.setDebug(true);
		hc.setDistanceFunction(new EuclideanDistance());
		hc.setDistanceIsBranchLength(true);
		
		BufferedReader datafile = readDataFile("G:/Academics/Semester 7/Data mining and Data Warehousing/Practicals/Clustering/NewData_ARFF.arff"); 
		Instances data = new Instances(datafile);
		
		hc.buildClusterer(data);
		hc.setPrintNewick(false);
		System.out.println(hc.graph());
		
		
		System.out.println("Revision: " + hc.getRevision());
		System.out.println("Graph Type: " + hc.graphType());
		System.out.println("Global Info: " + hc.globalInfo());
		long end = System.currentTimeMillis();
	    System.out.println("Time Taken: " + (end - start));
	    
	    JFrame mainFrame = new JFrame("Weka Test");
		mainFrame.setSize(600, 400);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container content = mainFrame.getContentPane();
		content.setLayout(new GridLayout(1, 1));
		
		HierarchyVisualizer visualizer = new HierarchyVisualizer(hc.graph());
		content.add(visualizer);
		
		mainFrame.setVisible(true);
	}
}
