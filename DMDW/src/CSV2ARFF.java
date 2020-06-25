

/**
 * This Class takes CSV file as input and converts it to ARFF (Attribute Relation File Format)
 * ARFF file format is necessary for computing clusters
*/

import java.io.File;
import java.io.BufferedWriter;
import java.io.FileWriter;

import weka.core.Instances;
import weka.core.converters.CSVLoader;

public class CSV2ARFF {

	public static void main(String arg[])throws Exception {
		File input = new File("G:/Academics/Semester 7/Data mining and Data Warehousing/Practicals/Classification/classificationTestDataset.csv");
		File output = new File("G:/Academics/Semester 7/Data mining and Data Warehousing/Practicals/Classification/classificationTestDataset_ARFF.arff");
		String outputFile = "G:/Academics/Semester 7/Data mining and Data Warehousing/Practicals/Classification/classificationTestDataset_ARFF.arff";
		Instances data = null;
		if(output.exists()){
			output.delete();
		}
		long start = System.currentTimeMillis();
		
		CSVLoader loader = new CSVLoader();
		
		if(input != null){
			loader.setSource(input);
			data = loader.getDataSet();
			for(int i = 0; i < data.size(); i++){
				System.out.println(data.instance(i));
			}
		}

	    BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));
	    writer.write(data.toString());
	    writer.flush();
	    writer.close();
	    long end = System.currentTimeMillis();
	    System.out.println(end - start);
	    System.out.println("Done");
	}
}
