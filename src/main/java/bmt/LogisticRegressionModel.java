import weka.classifiers.Classifier;
import weka.classifiers.functions.Logistic;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;


public class LogisticRegressionModel {


    public static void main(String[] args) throws Exception {
        // Load the dataset
        DataSource source = new DataSource("C:\\Users\\hadil\\Desktop\\JAVA\\movies_ratings.arff");
        Instances data = source.getDataSet();


        // Define the binary class based on average_rating
        // If rating is >= 5.5, classify as success (1), else failure (0)
        data.setClassIndex(data.numAttributes() - 1);  // Assuming last attribute is the class


        // Convert average_rating to binary success/failure
        for (int i = 0; i < data.numInstances(); i++) {
            double rating = data.instance(i).value(data.attribute("average_rating"));
            if (rating >= 5.5) {
                data.instance(i).setClassValue(1);  // Success
            } else {
                data.instance(i).setClassValue(0);  // Failure
            }
        }


        // Create the logistic regression model
        Classifier model = new Logistic();


        // Train the model using the dataset
        model.buildClassifier(data);


        // Create a new instance for prediction
        Instances predDataSet = new Instances(data, 0);  // Empty instance set
        double[] values = new double[data.numAttributes()];
        values[0] = 5.6;  // Example average rating
        values[1] = 500;  // Example number of votes
        values[2] = 1995; // Example release year


        predDataSet.add(new DenseInstance(1.0, values));


        // Classify the new instance
        double prediction = model.classifyInstance(predDataSet.instance(0));
        System.out.println("Prediction: " + prediction);
    }
}





