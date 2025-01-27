package bmt;

import org.apache.commons.math3.stat.regression.OLSMultipleLinearRegression;
import java.util.*;

public class RegressionAnalyzer {

    // Method to perform Linear Regression on country codes and profits
    public static double[] performLinearRegression(List<Double> countryCodes, List<Double> profits) {
        int n = countryCodes.size();

        // Prepare the data for regression: 2D array for X (countries) and Y (profits)
        double[][] xData = new double[n][1];
        double[] yData = new double[n];

        for (int i = 0; i < n; i++) {
            xData[i][0] = countryCodes.get(i); // country code
            yData[i] = profits.get(i); // profit
        }

        // Perform Linear Regression using Apache Commons Math
        OLSMultipleLinearRegression regression = new OLSMultipleLinearRegression();
        regression.newSampleData(yData, xData);

        // Get the coefficients (intercept and slope)
        double[] coefficients = regression.estimateRegressionParameters();
        return coefficients;  // Returns [intercept, slope]
    }

    // Method to predict profit based on country code using the regression model
    public static double predictProfit(double countryCode, double[] coefficients) {
        return coefficients[0] + coefficients[1] * countryCode; // Y = intercept + (slope * X)
    }

    public static void main(String[] args) {
        // Example data for regression
        // Assume this data is loaded from the CSV files
        List<String> countries = Arrays.asList("US", "GB", "IN", "JP", "TH", "DE", "FR", "IT", "ES", "CA");
        List<Double> profits = Arrays.asList(5000000.0, 3000000.0, 1000000.0, 2000000.0, 1500000.0, 1800000.0, 1200000.0, 2500000.0, 1600000.0, 4000000.0);

        // Map countries to numeric values (e.g., "US" -> 1, "GB" -> 2, etc.)
        Map<String, Double> countryMap = new HashMap<>();
        int countryIndex = 1;

        for (String country : countries) {
            countryMap.put(country, (double) countryIndex++);
        }

        // Prepare data for regression
        List<Double> countryCodes = new ArrayList<>();
        for (String country : countries) {
            countryCodes.add(countryMap.get(country));
        }

        // Perform Linear Regression
        double[] coefficients = performLinearRegression(countryCodes, profits);

        // Output the coefficients
        System.out.println("Regression Coefficients:");
        System.out.println("Intercept: " + coefficients[0]);
        System.out.println("Slope: " + coefficients[1]);

        // Predict profit for the top 10 countries
        System.out.println("\nPrediction of Profit Based on Production Country:");
        for (int i = 0; i < countries.size(); i++) {
            String country = countries.get(i);
            double countryCode = countryCodes.get(i);
            double predictedProfit = predictProfit(countryCode, coefficients);
            System.out.println("Country: " + country + ", Predicted Profit: " + predictedProfit);
        }
    }
}
