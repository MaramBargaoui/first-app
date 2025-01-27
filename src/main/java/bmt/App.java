package bmt;

import org.apache.commons.math3.stat.regression.OLSMultipleLinearRegression;

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;
import java.util.stream.Collectors;

abstract class Statistics {
    public abstract double calculate(List<Double> x, List<Double> y);
}

class CountryData {
    private String country;
    private double meanProfit;
    private double meanRevenue;
    private double meanBudget;

    public CountryData(String country, double meanProfit, double meanRevenue, double meanBudget) {
        this.country = country;
        this.meanProfit = meanProfit;
        this.meanRevenue = meanRevenue;
        this.meanBudget = meanBudget;
    }

    public String getCountry() {
        return country;
    }

    public double getMeanProfit() {
        return meanProfit;
    }

    public double getMeanRevenue() {
        return meanRevenue;
    }

    public double getMeanBudget() {
        return meanBudget;
    }

    public void displayCountryData() {
        System.out.println("Country: " + country + ", Mean Profit: " + meanProfit + ", Mean Revenue: " + meanRevenue + ", Mean Budget: " + meanBudget);
    }
}

class PearsonCorrelation extends Statistics {
    @Override
    public double calculate(List<Double> x, List<Double> y) {
        if (x.size() != y.size() || x.size() == 0) {
            throw new IllegalArgumentException("The lists must have the same non-zero size.");
        }

        double sumX = 0, sumY = 0, sumXY = 0, sumX2 = 0, sumY2 = 0;
        int n = x.size();

        for (int i = 0; i < n; i++) {
            sumX += x.get(i);
            sumY += y.get(i);
            sumXY += x.get(i) * y.get(i);
            sumX2 += Math.pow(x.get(i), 2);
            sumY2 += Math.pow(y.get(i), 2);
        }

        double numerator = n * sumXY - sumX * sumY;
        double denominator = Math.sqrt((n * sumX2 - Math.pow(sumX, 2)) * (n * sumY2 - Math.pow(sumY, 2)));

        if (denominator == 0) {
            return 0;  // No correlation
        }

        return numerator / denominator;
    }
}

class SpearmanCorrelation extends Statistics {
    @Override
    public double calculate(List<Double> x, List<Double> y) {
        if (x.size() != y.size() || x.size() == 0) {
            throw new IllegalArgumentException("The lists must have the same non-zero size.");
        }

        int n = x.size();
        double rankSum = 0.0;

        List<Integer> xRanks = rank(x);
        List<Integer> yRanks = rank(y);

        for (int i = 0; i < n; i++) {
            double d = xRanks.get(i) - yRanks.get(i);
            rankSum += Math.pow(d, 2);
        }

        return 1 - (6 * rankSum) / (n * (Math.pow(n, 2) - 1));
    }

    private List<Integer> rank(List<Double> data) {
        List<Integer> indices = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            indices.add(i);
        }

        indices.sort((i1, i2) -> Double.compare(data.get(i1), data.get(i2)));

        List<Integer> ranks = new ArrayList<>(Collections.nCopies(data.size(), 0));
        for (int i = 0; i < indices.size(); i++) {
            ranks.set(indices.get(i), i + 1);
        }
        return ranks;
    }
}

public class App {

    public double[] performLinearRegression(List<Double> xValues, List<Double> yValues) {
        int n = xValues.size();
        double[][] xData = new double[n][1];
        double[] yData = new double[n];

        for (int i = 0; i < n; i++) {
            xData[i][0] = xValues.get(i);
            yData[i] = yValues.get(i);
        }

        OLSMultipleLinearRegression regression = new OLSMultipleLinearRegression();
        regression.newSampleData(yData, xData);

        return regression.estimateRegressionParameters();
    }

    public double calculateMean(List<Double> data) {
        double sum = 0.0;
        for (double value : data) {
            sum += value;
        }
        return sum / data.size();
    }

    public static void main(String[] args) {
        App app = new App();  // Create an instance of the App class to call non-static methods

        // Example data
        List<String> countries = List.of("US", "GB", "IN", "JP", "TH", "DE", "FR", "IT", "ES", "CA");
        List<Double> profitsList = List.of(5000000.0, 3000000.0, 1000000.0, 2000000.0, 1500000.0, 1800000.0, 1200000.0, 2500000.0, 1600000.0, 4000000.0);
        List<Double> revenuesList = List.of(6000000.0, 3500000.0, 1200000.0, 2200000.0, 1700000.0, 1900000.0, 1300000.0, 2700000.0, 1800000.0, 4200000.0);
        List<Double> budgetsList = List.of(5500000.0, 3200000.0, 1100000.0, 2100000.0, 1600000.0, 2000000.0, 1300000.0, 2600000.0, 1700000.0, 4300000.0);
        List<Double> votesList = List.of(10000.0, 20000.0, 5000.0, 7000.0, 8000.0, 15000.0, 12000.0, 17000.0, 16000.0, 13000.0);
        List<Double> imdbRatingsList = List.of(7.5, 6.8, 5.9, 6.0, 6.5, 7.0, 6.8, 7.2, 7.3, 6.9);

        // Display Descriptive Statistics
        System.out.println("Descriptive Statistics for Countries:");
        for (int i = 0; i < countries.size(); i++) {
            System.out.println("Country: " + countries.get(i) + ", Mean Profit: " + profitsList.get(i) + ", Mean Revenue: " + revenuesList.get(i) + ", Mean Budget: " + budgetsList.get(i));
        }

        // Calculate and print Pearson and Spearman correlations
        PearsonCorrelation pearsonCalculator = new PearsonCorrelation();
        SpearmanCorrelation spearmanCalculator = new SpearmanCorrelation();

        double pearson = pearsonCalculator.calculate(imdbRatingsList, revenuesList);
        double spearman = spearmanCalculator.calculate(imdbRatingsList, revenuesList);

        System.out.println("\nPearson Correlation (Ratings & Revenue): " + pearson);
        System.out.println("Spearman Correlation (Ratings & Revenue): " + spearman);

        // Perform linear regression and make predictions
        double[] profitCoefficients = app.performLinearRegression(countries.stream().map(country -> (double) country.hashCode()).collect(Collectors.toList()), profitsList);
        System.out.println("\nRegression Coefficients for Country Codes vs Profits:");
        System.out.println("Intercept: " + profitCoefficients[0]);
        System.out.println("Slope: " + profitCoefficients[1]);

        double[] revenueCoefficients = app.performLinearRegression(budgetsList, revenuesList);
        System.out.println("\nRegression Coefficients for Budgets vs Revenues:");
        System.out.println("Intercept: " + revenueCoefficients[0]);
        System.out.println("Slope: " + revenueCoefficients[1]);

        double[] imdbRatingCoefficients = app.performLinearRegression(votesList, imdbRatingsList);
        System.out.println("\nRegression Coefficients for Votes vs IMDb Ratings:");
        System.out.println("Intercept: " + imdbRatingCoefficients[0]);
        System.out.println("Slope: " + imdbRatingCoefficients[1]);

        // Predictions with rounded values for consistency
        System.out.println("\nPrediction of Profit Based on Production Country:");
        for (int i = 0; i < countries.size(); i++) {
            double countryCode = (double) countries.get(i).hashCode();
            double predictedProfit = predictProfit(countryCode, profitCoefficients);
            System.out.println("Country: " + countries.get(i) + ", Predicted Profit: " + String.format("%.2f", predictedProfit));
        }

        System.out.println("\nPrediction of Revenue Based on Budget:");
        for (int i = 0; i < countries.size(); i++) {
            double budget = budgetsList.get(i);
            double predictedRevenue = predictRevenue(budget, revenueCoefficients);
            System.out.println("Country: " + countries.get(i) + ", Predicted Revenue: " + String.format("%.2f", predictedRevenue));
        }

        System.out.println("\nPrediction of IMDb Ratings Based on Votes:");
        for (int i = 0; i < countries.size(); i++) {
            double voteCount = votesList.get(i);
            double predictedImdbRating = predictImdbRating(voteCount, imdbRatingCoefficients);
            System.out.println("Votes: " + voteCount + ", Predicted IMDb Rating: " + String.format("%.2f", predictedImdbRating));
        }
    }

    public static double predictProfit(double countryCode, double[] coefficients) {
        return coefficients[0] + coefficients[1] * countryCode;
    }

    public static double predictRevenue(double budget, double[] coefficients) {
        return coefficients[0] + coefficients[1] * budget;
    }

    public static double predictImdbRating(double voteCount, double[] coefficients) {
        return coefficients[0] + coefficients[1] * voteCount;
    }
}
