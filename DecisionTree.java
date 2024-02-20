import java.util.ArrayList;


public class DecisionTree {
    private TreeNode root = null; //stores the root of the decision tree


    public void train(ArrayList<Example> examples){
        int numFeatures = 0;
        if(examples.size()>0) //get the number of featuers in these examples
            numFeatures = examples.get(0).getNumFeatures();

        //initialize empty positive and negative lists
        ArrayList<Example> pos = new ArrayList<Example>();
        ArrayList<Example> neg = new ArrayList<Example>();

        //paritition examples into positive and negative ones
        for(Example e: examples){
            if (e.getLabel())
                pos.add(e);
            else
                neg.add(e);
        }

        //create the root node of the tree
        root = new TreeNode(null, pos, neg, numFeatures);

        //call recursive train()  on the root node
        train(root, numFeatures);
    }

    /**
     * TODO: Complete this method
     * The recursive train method that builds a tree at TreeNode node
     * @param node: current node to train
     * @param numFeatures: total number of features
     */
    private void train(TreeNode node, int numFeatures){

		//count how many positive and negative examples in total
		int totalPos = node.pos.size();
		int totalNeg = node.neg.size();
		int totalExamples = totalPos + totalNeg;


		//counter for every time a feature is used
		int amtFeature = 0;
		for (int i = 0; i < numFeatures; i++){
			if(node.featureUsed(i) == true){
				amtFeature++;
			}
		}

        //base cases
		if(totalNeg > 0 && totalPos == 0){ //(1) if all the remaining examples at this node have the same label L, set the node's label to L
			node.decision = false;
			node.isLeaf = true;
		} else if (totalNeg == 0 && totalPos > 0){
			node.decision = true;
			node.isLeaf = true;
		} else if(totalExamples == 0){//(2) if there are no examples at this node, set this node's label to the majority label of its parent's examples
		//create number of positive examples of parents
		int totalPosParent = node.parent.pos.size();
		int totalNegParent = node.parent.neg.size();
			if(totalNegParent > totalPosParent){//this checks if all the parent examples are negative, and if it is, set the node's label to be false
				node.decision = true;
				node.isLeaf = true;
			} else {
				node.decision = false;
				node.isLeaf = true;
			}
		} else if(amtFeature == numFeatures){ //(3) if no more features to split on at this node, set this node's label to the majority label of this node's examples
			if(totalPos > totalNeg){
				node.decision = true;
			} else if (totalNeg < totalPos){
				node.decision = false;
			}
		} else {

			double bestInfoGain = 0;
			int bestFeature = -1;

			for(int i = 0; i < numFeatures; i++){
				if(node.featureUsed(i) != true){
					double tempInfo = getEntropy(totalPos, totalNeg) - getRemainingEntropy(i, node);
					if(tempInfo > bestInfoGain){
						bestInfoGain = tempInfo;
						bestFeature = i;
					}
				}
			}

			if(bestFeature != -1){
				node.setSplitFeature(bestFeature);
				amtFeature++;
				createChildren(node, numFeatures);
				train(node.trueChild,numFeatures);
				train(node.falseChild, numFeatures);
			}

		}

    }

    /**
     * TODO: Complete this method
     * Creates the true and false children of TreeNode node
     * @param node: node at which to create children
     * @param numFeatures: total number of features
     */
    private void createChildren(TreeNode node, int numFeatures){
        TreeNode currentNode = node;
       

        ArrayList<Example> numPosChildTrue = new ArrayList<Example>();
        ArrayList<Example> numPosChildFalse = new ArrayList<Example>();

        ArrayList<Example> numNegChildTrue = new ArrayList<Example>();
        ArrayList<Example> numNegChildFalse = new ArrayList<Example>();


        for (Example e : node.pos) {//get all the positive examples
            if (e.getFeatureValue(currentNode.getSplitFeature())) {//check if the positive example is true for split feature, if so store in the true positive arraylist
                numPosChildTrue.add(e);
            } else {
                numPosChildFalse.add(e);
            }
        }

        for(Example e : node.neg) {//get all the negative examples
            if(e.getFeatureValue(currentNode.getSplitFeature())) {//check if the negative example is true for split feature, if so store in the true negative arraylist
                numNegChildTrue.add(e);
            } else {
                numNegChildFalse.add(e);
            }
        }

       
        TreeNode trueChild = new TreeNode(currentNode, numPosChildTrue, numNegChildTrue, numFeatures);
        TreeNode falseChild = new TreeNode(currentNode, numPosChildFalse, numNegChildFalse, numFeatures);

        currentNode.trueChild = trueChild;
        currentNode.falseChild = falseChild;
    }
   
   
    /**
     * TODO: Complete this method
     * Computes and returns the remaining entropy if feature is chosen
     * at node.
     * @param feature: the feature number
     * @param node: node at which to find remaining entropy
     * @return remaining entropy at node
     */
    private double getRemainingEntropy(int feature, TreeNode node){/* */
        //Positive examples
        ArrayList<Example> numPosChildTrue = new ArrayList<Example>();//stores positive examples with true features
        ArrayList<Example> numPosChildFalse = new ArrayList<Example>();//stores positive examples with false features

        //Negative examples
        ArrayList<Example> numNegChildTrue = new ArrayList<Example>();//stores negative examples with true features
        ArrayList<Example> numNegChildFalse = new ArrayList<Example>();//stores negative examples with false features

        for (Example e : node.pos){//for every positive label example in the node
            if (e.getFeatureValue(feature) == true){//store in the positive example where the feature is true
                numPosChildTrue.add(e);
            } else {//store in the positive example where the feature is false (meaning it wasn't used to split)
                numPosChildFalse.add(e);
            }
        }

        for(Example e: node.neg){//for every negative label example in the node
            if (e.getFeatureValue(feature) == true){//store in the negative example where the feature is true
                numNegChildTrue.add(e);
            } else{//store in the negative example where the feature is false
                numNegChildFalse.add(e);
            }
        }

        int totalTrue = numNegChildTrue.size() + numPosChildTrue.size();
        int totalFalse = numNegChildFalse.size() + numPosChildFalse.size();
        double totalChildren = totalTrue + totalFalse;

        double trueFeatureEntropy = getEntropy(numPosChildTrue.size(), numNegChildTrue.size());
        double falseFeatureEntropy = getEntropy(numPosChildFalse.size(), numNegChildFalse.size());

        double remainingEntropy = ((totalTrue/totalChildren)*trueFeatureEntropy) + ((totalFalse/totalChildren)*falseFeatureEntropy);
       
        return remainingEntropy;
       
    }
   
    /**
     * TODO: complete this method
     * Computes the entropy of a node given the number of positive and negative examples it has
     * @param numPos: number of positive examples
     * @param numNeg: number of negative examples
     * @return - entropy
     */
    private double getEntropy(int numPos, int numNeg){
        double numPosDouble = numPos;
        double numNegDouble = numNeg;
        int totalExamples = numPos + numNeg;
        double numPosProbability = numPosDouble/totalExamples;
        double numNegProbability = numNegDouble/totalExamples;
        double entropy = 0; //entropy holder

        if (totalExamples == 0) {
            entropy = 0;
        } else if(numPosProbability == 0){
            entropy = (-1) * (numNegProbability * log2(numNegProbability));
        } else if(numNegProbability == 0){
            entropy = (-1) * (numPosProbability * log2(numPosProbability));
        } else {
            entropy = ((-1) * (numPosProbability * log2(numPosProbability))) - (numNegProbability * log2(numNegProbability));
        }
        return entropy;
    }
   
    /**
     * Computes log_2(d) (To be used by the getEntropy() method)
     * @param d - value
     * @return log_2(d)
     */
    private double log2(double d){
        return Math.log(d)/Math.log(2);
    }
   
    /**
     * TODO: complete this method
     * Classifies example e using the learned decision tree
     * @param e: example
     * @return true if e is predicted to be  positive,  false otherwise
     */
    public boolean classify(Example e){
        TreeNode currentNode = root;
        int featureHolder = 0;
        while(!currentNode.isLeaf && currentNode.getSplitFeature() != -1) {
            featureHolder = currentNode.getSplitFeature();
            boolean tester = e.getFeatureValue(featureHolder);
            if(tester == true) {
                currentNode = currentNode.trueChild;
            } else {
                currentNode = currentNode.falseChild;
            }
        }
        return currentNode.decision;
    }
   
   
   
   
    //----------DO NOT MODIFY CODE BELOW------------------
    public void print(){
        printTree(root, 0);
    }
   

   
    private void printTree(TreeNode node, int indent){
        if(node== null)
            return;
        if(node.isLeaf){
            if(node.decision)
                System.out.println("Positive");
            else
                System.out.println("Negative");
        }
        else{
            System.out.println();
            doIndents(indent);
            System.out.print("Feature "+node.getSplitFeature() + " = True:" );
            printTree(node.trueChild, indent+1);
            doIndents(indent);
            System.out.print("Feature "+node.getSplitFeature() + " = False:" );//+  "( " + node.falseChild.pos.size() + ", " + node.falseChild.neg.size() + ")");
            printTree(node.falseChild, indent+1);
        }
    }
   
    private void doIndents(int indent){
        for(int i=0; i<indent; i++)
            System.out.print("\t");
    }
}