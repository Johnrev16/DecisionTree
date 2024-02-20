# DecisionTree

John Bermudez and Daniela Delgado
File names: TestClassifier.java, TestClassifierHepatitis.java, DecisionTree.java, set1 small, set1 big, set2 small, set2 big
Known bugs: None

(1.) 

(2A.)
Our decision tree output the same result as what is shown in the 'expected result' files. 
The same amount of positive & negative examples being correct. However, we ran into a little bug when it comes to outputting the decision tree for 'set2 big'. At first, our code would not run as it would run into an 'index out of bound' error. But after doing a little bit of debugging, we found that our code takes in splitfeatures that are non-existent (i.e. the splitfeature value is -1). And so to take this into account, we added an additional condition in our 'classify' if-condition that ignores the -1 features. By doing so, we are able to get the code gonig again and still outputting the correct results as shown in the 'expected results' files. Except with this fix, the decision tree includes a section of "features -1" while the rest of the tree's outputs match the expected results. 

(2B.)
    (i.) Our decision tree classifies patients as follows:
     - In general, the decision tree begins at a feature and branches from that feature until it reaches a positive or negative result. Some symptoms are  more serious than others, so the depth of that section is not as deep as other symptom's branches. 
     - if the patient has Varices, an SGOT of >156, a liver big, malaise, then the patient survived Hepatitis
     - if the patient has Varices, an SGOT of >156, and no liver big, then the patient has likely died
     - if the patient has Varices, an SGOT of <156, a liver big, no malaise, and not a male, then they likely survived.
     - if the patient has Varices, an SGOT of <156, a liver big, no malaise, a male, age is < 30, then patient died.
     - if the patient has Varices, an SGOT of <156, a liver big, no malaise, a male, age is not < 30, and is on steroid, then patient survived
     - if the patient has Varices, an SGOT of <156, a liver big, no malaise, a male, age is not < 30, and is not on steroid, then patient died
     - if the patient has Varices, an SGOT not greater than 156, and a bilirubin less than or equal to 2.9, age between 31-50, yes fatigue, then patient survived
     - if the patient has Varices, an SGOT not greater than 156, and a bilirubin less than or equal to 2.9, age between 31-50, no fatigue, has anorexia, has liver big, has spiders, alk phospate is < 127, then the patient survived.
     - if the patient has Varices, an SGOT not greater than 156, and a bilirubin less than or equal to 2.9, age between 31-50, no fatigue, has anorexia, has liver big, has spiders, alk phospate not < 127, has steroid, then patient survived.
     - if the patient has Varices, an SGOT not greater than 156, and a bilirubin less than or equal to 2.9, age between 31-50, no fatigue, has anorexia, has liver big, has spiders, alk phospate not < 127, no steroid, then patient died.
     - if the patient has Varices, an SGOT not greater than 156, and a bilirubin less than or equal to 2.9, age between 31-50, no fatigue, has anorexia, has liver big, has no spiders, then patient died.
     - if the patient has Varices, an SGOT not greater than 156, and a bilirubin less than or equal to 2.9, age between 31-50, no fatigue, has anorexia, has no liver big, then the patient survived.
     - if the patient has Varices, an SGOT not greater than 156, and a bilirubin less than or equal to 2.9, age between 31-50, no fatigue, has no anorexia,  then the patient survived.
     - if the patient has Varices, an SGOT not greater than 156, and a bilirubin less than or equal to 2.9, age not between 31-50, no fatigue, then the patient survived.
     - if the patient has Varices, an SGOT not greater than 156, and a bilirubin is not less than or equal to 2.9, has anorexia, then the patient survived.
     - if the patient has Varices, an SGOT not greater than 156, and a bilirubin is not less than or equal to 2.9, does not have anorexia then the patient died.
     - if the patient does not have Varices, and does have Histology, then the patient died. 
     - if the patient does not have Varices and does not have histology, then the patient survived.

    (ii.)There are 38 out of 42 correct positive examples (90.47%)
         There are 7 out of 10 correct negative examples (70%)
         There are 5 false:negative
         There are also 5 false:positive


(3.)
    (a). The name of the dataset is Stroke Prediction Dataset
    (b). We obtained the dataset from kaggle.come
    (c). Our final dataset has 11 features: gender, age (true if older than 50, otherwise false), hypertension, heart_disease, ever_married, work_type, residence_type, avg_glucose_level, bmi, and smoking status
        We did a little bit of data cleaning first before using testing our data with our decision tree. For example, for work type, we put 'false' if the person is a 'children' or if they have never worked. For
        avg_glucose_level, we separated it into true if the person has a healthy level of glucose and false if not. This concept we applied to other features such as bmi. Using this dataset, we are trying to
        predict if the person has had a stroke or not given the features.

    (d). In each of our dataset, we have 51 examples
    (e). The number of features for each dataset is 10

    Our decision tree on stroke data performs averagely. This can be due to the fact that we cut our examples size down significantly from the given samples to not
    overwhelm our program. Thus, we had less data to train our decision tree. Additionally, to give our data binary values, we had to generalize certain features,
    such as clustering the age groups into two instead of it being individual. The tree classifies data by looking at the feature with the most information gain
    and branches with the next best information gain feature.

    Positive Examples correct: 37 out of 51 (72.5%)
    Negative Examples correct: 35 out of 51 (68.6%)

    6 false:positives
    10 false:negatives
   
    Disclaimer: To make our code simpler, we simply copy pasted our dataset over to one of the pre-existing dataset so that we don't have to code for our dataset case. We still have the old dataset that we pasted over saved to another
    text file for testing purposes. We pasted our dataset on train_pos2-small, train_neg2-small, test_pos2-small, and test_neg2-small (to run our data set, just simply run 'java TestClassifier set2 small').
    




