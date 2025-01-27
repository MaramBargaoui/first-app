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

    // New method for performing linear regression on budgets and revenues
    public static double[] performBudgetRevenueRegression(List<Double> budgets, List<Double> revenues) {
        int n = budgets.size();

        // Prepare the data for regression: 2D array for X (budgets) and Y (revenues)
        double[][] xData = new double[n][1];
        double[] yData = new double[n];

        for (int i = 0; i < n; i++) {
            xData[i][0] = budgets.get(i); // budget
            yData[i] = revenues.get(i);   // revenue
        }

        // Perform Linear Regression using Apache Commons Math
        OLSMultipleLinearRegression regression = new OLSMultipleLinearRegression();
        regression.newSampleData(yData, xData);

        // Get the coefficients (intercept and slope)
        double[] coefficients = regression.estimateRegressionParameters();
        return coefficients;  // Returns [intercept, slope]
    }

    // Method to predict revenue based on budget using the regression model
    public static double predictRevenue(double budget, double[] coefficients) {
        return coefficients[0] + coefficients[1] * budget; // Y = intercept + (slope * X)
    }

    // New method for performing linear regression on votes and IMDb ratings
    public static double[] performVotesRatingRegression(List<Double> votes, List<Double> imdbRatings) {
        int n = votes.size();

        // Prepare the data for regression: 2D array for X (votes) and Y (IMDb ratings)
        double[][] xData = new double[n][1];
        double[] yData = new double[n];

        for (int i = 0; i < n; i++) {
            xData[i][0] = votes.get(i); // number of votes
            yData[i] = imdbRatings.get(i);   // IMDb ratings
        }

        // Perform Linear Regression using Apache Commons Math
        OLSMultipleLinearRegression regression = new OLSMultipleLinearRegression();
        regression.newSampleData(yData, xData);

        // Get the coefficients (intercept and slope)
        double[] coefficients = regression.estimateRegressionParameters();
        return coefficients;  // Returns [intercept, slope]
    }

    // Method to predict IMDb rating based on votes using the regression model
    public static double predictImdbRating(double votes, double[] coefficients) {
        return coefficients[0] + coefficients[1] * votes; // Y = intercept + (slope * X)
    }

    public static void main(String[] args) {
        // Example data for regression
        List<String> countries = Arrays.asList("US", "GB", "IN", "JP", "TH", "DE", "FR", "IT", "ES", "CA");
        List<Double> profits = Arrays.asList(5000000.0, 3000000.0, 1000000.0, 2000000.0, 1500000.0, 1800000.0, 1200000.0, 2500000.0, 1600000.0, 4000000.0);
        List<Double> revenues = Arrays.asList(6000000.0, 3500000.0, 1200000.0, 2200000.0, 1700000.0, 1900000.0, 1300000.0, 2700000.0, 1800000.0, 4200000.0);
        List<Double> budgets = Arrays.asList(5500000.0, 3200000.0, 1100000.0, 2100000.0, 1600000.0, 2000000.0, 1300000.0, 2600000.0, 1700000.0, 4300000.0);
        List<Double> imdbRatings = Arrays.asList(7.5, 6.8, 5.9, 6.0, 6.5, 7.0, 6.8, 7.2, 7.3, 6.9);  // IMDb ratings
        List<Double> votes = Arrays.asList(10000.0, 20000.0, 5000.0, 7000.0, 8000.0, 15000.0, 12000.0, 17000.0, 16000.0, 13000.0); // Votes

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

        // Perform Linear Regression (country codes vs profits)
        double[] profitCoefficients = performLinearRegression(countryCodes, profits);
        System.out.println("Regression Coefficients for Country Codes vs Profits:");
        System.out.println("Intercept: " + profitCoefficients[0]);
        System.out.println("Slope: " + profitCoefficients[1]);

        // Perform Linear Regression (budgets vs revenues)
        double[] revenueCoefficients = performBudgetRevenueRegression(budgets, revenues);
        System.out.println("\nRegression Coefficients for Budgets vs Revenues:");
        System.out.println("Intercept: " + revenueCoefficients[0]);
        System.out.println("Slope: " + revenueCoefficients[1]);

        // Perform Linear Regression (votes vs IMDb ratings)
        double[] imdbRatingCoefficients = performVotesRatingRegression(votes, imdbRatings);
        System.out.println("\nRegression Coefficients for Votes vs IMDb Ratings:");
        System.out.println("Intercept: " + imdbRatingCoefficients[0]);
        System.out.println("Slope: " + imdbRatingCoefficients[1]);

        // Predictions with rounded values for consistency
        System.out.println("\nPrediction of Profit Based on Production Country:");
        for (int i = 0; i < countries.size(); i++) {
            double countryCode = countryCodes.get(i);
            double predictedProfit = predictProfit(countryCode, profitCoefficients);
            System.out.println("Country: " + countries.get(i) + ", Predicted Profit: " + String.format("%.2f", predictedProfit));
        }

        System.out.println("\nPrediction of Revenue Based on Budget:");
        for (int i = 0; i < countries.size(); i++) {
            double budget = budgets.get(i);
            double predictedRevenue = predictRevenue(budget, revenueCoefficients);
            System.out.println("Country: " + countries.get(i) + ", Predicted Revenue: " + String.format("%.2f", predictedRevenue));
        }

        // Prediction of IMDb Ratings Based on Votes:
System.out.println("\nPrediction of IMDb Ratings Based on Votes:");
for (int i = 0; i < countries.size(); i++) {
    double voteCount = votes.get(i);  // Getting the number of votes
    double predictedImdbRating = predictImdbRating(voteCount, imdbRatingCoefficients);
    System.out.println("Votes: " + voteCount + ", Predicted IMDb Rating: " + String.format("%.2f", predictedImdbRating));
}

    }
}
