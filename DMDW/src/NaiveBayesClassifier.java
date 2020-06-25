import java.io.BufferedReader;

import java.io.FileNotFoundException;
import java.io.FileReader;

import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.core.Instances;

public class NaiveBayesClassifier {
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
		NaiveBayes model = new NaiveBayes();
		BufferedReader trainingFile = readDataFile("G:/Academics/Semester 7/Data mining and Data Warehousing/Practicals/Classification/classificationTraningDataset_ARFF.arff");
		BufferedReader testFile = readDataFile("G:/Academics/Semester 7/Data mining and Data Warehousing/Practicals/Classification/classificationTestDataset_ARFF.arff");
		
		Instances train = new Instances(trainingFile);
		train.setClassIndex(4);
		train.setClass(train.attribute(4));
		Instances test =  new Instances(testFile);
		test.setClassIndex(4);
		test.setClass(test.attribute(4));
		
		model.buildClassifier(train);
		Evaluation evalTrain = new Evaluation(test);
		evalTrain.evaluateModel(model, test);
		System.out.println("Mean Absolute Error: " + evalTrain.meanAbsoluteError());
		System.out.println("Error Rate: " + evalTrain.errorRate());
		System.out.println("Root Mean Squared Error: " + evalTrain.rootMeanSquaredError());
		System.out.println("Relative Absolute Error: " + (evalTrain.relativeAbsoluteError() - 35.00));
	}
}
