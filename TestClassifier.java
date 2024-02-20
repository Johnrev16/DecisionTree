// TestClassifier.java
// Trains and tests a decision tree classifier.

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class TestClassifier {

    // Random number generator
    static Random rand = new Random();

    // Constants to use
    static int exampleCount;
    static int featureCount;
    
    //added
    static ArrayList<Example> trainExs; //training examples
 
    // Process arguments.
    public static void main(String[] args) throws FileNotFoundException {


    	if (args.length == 2 && args[0].equals("set1")){
    		if(args[1].equals("small")){
    			exampleCount = 10;
    			featureCount = 8;
    			testClassifier("1", "small");
    		}
    		else if(args[1].equals("big")){
    			exampleCount = 100;
    			featureCount = 10;
    			testClassifier("1", "big");
    		}
    		else
    			System.out.println("Usage: java TestClassifier set1|set2 small|big");
    	}
    	else if (args.length == 2 && args[0].equals("set2")){
    		if(args[1].equals("small")){
    			exampleCount = 51;
    			featureCount = 10;
    			testClassifier("2", "small");
    		}
    		else if(args[1].equals("big")){
    			exampleCount = 100;
    			featureCount = 10;
    			testClassifier("2", "big");
    		}
    		else
    			System.out.println("Usage: java TestClassifier set1|set2 small|big");
    	}
    	else
    	    System.out.println("Usage: java TestClassifier set1|set2 small|big");
        }


    // Train and test.
    private static void testClassifier(String dataset, String size) throws FileNotFoundException {
	
    	//Load training examples from input files
	    Example[] trainPos = loadExamples("train_pos"+dataset+"-"+size+".txt");
	    Example[] trainNeg = loadExamples("train_neg"+dataset+"-"+size+".txt");


	    
	   
	    trainExs = new ArrayList<Example>();
		
	    //System.out.println(trainPos.length+" "+trainNeg.length);
	    

	    for(int i=0; i<trainPos.length; i++) {
	    	trainPos[i].setLabel(true);
	    	trainExs.add(trainPos[i]);
	    }
	    
	    
	    for(int i=0; i<trainNeg.length; i++) {
	    	trainNeg[i].setLabel(false);
	    	trainExs.add(trainNeg[i]);
	    }
	    
	    
	    //for (Example e : trainExs)
		  //  System.out.println(e);
	    
	    
	    
		// Train the tree
		DecisionTree tree = new DecisionTree();

		

		tree.train(trainExs);


	
		//Load the testing examples from input files
		Example[] testPos = loadExamples("test_pos"+dataset+"-"+size+".txt");
		Example[] testNeg = loadExamples("test_neg"+dataset+"-"+size+".txt");
	
		// Evaluate on positives
		int correct = 0;
		for (Example e : testPos)
			if (tree.classify(e))
		    	correct++;
		System.out.println("Positive examples correct: "+correct+" out of "+testPos.length);
	
		// Evaluate on negatives
		correct = 0;
		for (Example e : testNeg)
		    if (!tree.classify(e))
		    	correct++;
		System.out.println("Negative examples correct: "+correct+" out of "+testNeg.length);
		System.out.println();
		
		 //Display the tree
		 tree.print();
		
	
    }

    /*********************************************
     * Loads examples from file.
     * @throws FileNotFoundException 
     */
    private static Example[] loadExamples(String file) throws FileNotFoundException
    {
    	Example[] exs = new Example[exampleCount];
    	
    	Scanner scan = new Scanner(new File(file));
    
    	for(int i=0; i<exampleCount; i++){
    		
    		exs[i] = new Example(featureCount);
    		
    		for(int j=0; j<featureCount; j++){
    			if(scan.hasNextBoolean())
    				exs[i].setFeatureValue(j, scan.nextBoolean());
    		}
    	}
    	
    	scan.close();
    	
    	return exs; 
    }
    
    
    
}
