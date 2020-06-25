import java.io.BufferedReader;

import java.io.FileNotFoundException;
import java.io.FileReader;

import weka.classifiers.Classifier;
import weka.classifiers.lazy.IBk;
import weka.core.Instance;
import weka.core.Instances;


public class KNN {
	public static BufferedReader readDataFile(String filename) {
		BufferedReader inputReader = null;
		try {
			inputReader = new BufferedReader(new FileReader(filename));
		} catch(FileNotFoundException e) {
			System.err.println("File not found: " + filename);
		}
		return inputReader;
	}
	
	public static void main(String args[]) throws Exception{
		Classifier ibk = new IBk();
		
		BufferedReader trainingFile = readDataFile("G:/Academics/Semester 7/Data mining and Data Warehousing/Practicals/Clustering/NewData_ARFF.arff");
		
		Instances train = new Instances(trainingFile);
		train.setClassIndex(4);
		train.setClass(train.attribute(4));
		
		Instance test[] = new Instance[10];

		int ct = 0;
		for(Instance i : train){
			test[ct] = i;
			train.delete(ct);
			ct++;
			if(ct == 10){
				break;
			}
		}
		
		ibk.buildClassifier(train);
		
		for(Instance i: test){
			System.out.println("Classification: " + ibk.classifyInstance(i));
		}
	}
}
