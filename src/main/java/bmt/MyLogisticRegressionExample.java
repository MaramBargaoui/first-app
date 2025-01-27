package bmt;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.ml.classification.LogisticRegression;
import org.apache.spark.ml.classification.LogisticRegressionModel;

import org.apache.spark.ml.linalg.Vector;
import org.apache.spark.ml.feature.VectorAssembler;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.ml.evaluation.BinaryClassificationEvaluator;

public class MyLogisticRegressionExample {

    public static void main(String[] args) {

        // Initialize Spark session
        SparkSession spark = SparkSession.builder()
                .appName("LogisticRegressionExample")
                .master("local[*]")
                .getOrCreate();

        // Load your dataset (assuming a CSV with headers)
        Dataset<Row> data = spark.read()
                .option("header", "true")
                .option("inferSchema", "true")
                .csv("path/to/your/movies_data.csv");

        // Print the schema to understand the data
        data.printSchema();

        // Prepare the data (Select relevant columns and convert them into the right format)
        // For example, let's assume your dataset has the following columns:
        // "revenue", "budget", "success" (where success is a binary outcome: 1 for success, 0 for failure)

        // Select relevant features
        Dataset<Row> selectedData = data.select("revenue", "budget", "success");

        // Create feature vector (combining all features into one vector)
        VectorAssembler assembler = new VectorAssembler()
                .setInputCols(new String[]{"revenue", "budget"}) // Columns used as features
                .setOutputCol("features");

        Dataset<Row> assembledData = assembler.transform(selectedData);

        // Split the data into training and test sets
        Dataset<Row>[] splits = assembledData.randomSplit(new double[]{0.8, 0.2});
        Dataset<Row> trainingData = splits[0];
        Dataset<Row> testData = splits[1];

        // Create and train the Logistic Regression model
        LogisticRegression lr = new LogisticRegression()
                .setLabelCol("success") // Label column
                .setFeaturesCol("features"); // Feature column

                LogisticRegressionModel model = lr.fit(trainingData);

        // Make predictions on the test set
        Dataset<Row> predictions = model.transform(testData);

        // Show some predictions
        predictions.select("revenue", "budget", "success", "prediction").show();

        // Evaluate the model using BinaryClassificationEvaluator
        BinaryClassificationEvaluator evaluator = new BinaryClassificationEvaluator()
                .setLabelCol("success")
                .setRawPredictionCol("prediction");

        double accuracy = evaluator.evaluate(predictions);
        System.out.println("Model Accuracy: " + accuracy);

        // Stop the Spark session
        spark.stop();
    }
}
