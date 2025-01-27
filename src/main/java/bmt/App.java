package bmt;

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

public class App {

    // Descriptive Statistics - Mean
    public static double calculateMean(List<Double> data) {
        double sum = 0.0;
        for (double value : data) {
            sum += value;
        }
        return sum / data.size();
    }

    // Pearson Correlation Coefficient
    public static double calculatePearson(List<Double> x, List<Double> y) {
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

    // Spearman Rank Correlation Coefficient
    public static double calculateSpearman(List<Double> x, List<Double> y) {
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

    // Helper method to calculate the ranks of a list of values
    private static List<Integer> rank(List<Double> data) {
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

    // Perform Linear Regression on country codes and profits
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
        org.apache.commons.math3.stat.regression.OLSMultipleLinearRegression regression = new org.apache.commons.math3.stat.regression.OLSMultipleLinearRegression();
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
        String revenuesFilePath = "src/main/java/bmt/movies_revenues.csv";
        String libraryFilePath = "src/main/java/bmt/movies_library.csv";

        Map<String, Double> budgetData = new HashMap<>();
        Map<String, Double> revenueData = new HashMap<>();
        Map<String, Double> profitData = new HashMap<>();
        Map<String, String> countryData = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(revenuesFilePath))) {
            String line;
            boolean isFirstLine = true;
            while ((line = br.readLine()) != null) {
                if (isFirstLine) { isFirstLine = false; continue; }
                String[] values = line.split(",");
                try {
                    String imdbId = values[0];
                    double budget = Double.parseDouble(values[1]);
                    double revenue = Double.parseDouble(values[2]);
                    double profit = Double.parseDouble(values[3]);

                    budgetData.put(imdbId, budget);
                    revenueData.put(imdbId, revenue);
                    profitData.put(imdbId, profit);
                } catch (NumberFormatException e) {
                    continue;
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading the revenues file: " + e.getMessage());
        }

        try (BufferedReader br = new BufferedReader(new FileReader(libraryFilePath))) {
            String line;
            boolean isFirstLine = true;
            while ((line = br.readLine()) != null) {
                if (isFirstLine) { isFirstLine = false; continue; }
                String[] values = line.split(",");
                String imdbId = values[0];
                String productionCountries = values[5].replaceAll("[\\[\\]'\"]", "").trim();

                if (productionCountries.matches(".*\\b(US|GB|AU|IN|JP|TH|EG|LB|CA|AR|DK)\\b.*")) {
                    countryData.put(imdbId, productionCountries);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading the library file: " + e.getMessage());
        }

        Set<String> targetCountries = new HashSet<>();
        Collections.addAll(targetCountries, "US", "GB", "AU", "IN", "JP", "TH", "EG", "LB", "CA", "AR", "DK");

        Map<String, List<Double>> countryProfits = new HashMap<>();
        Map<String, List<Double>> countryRevenues = new HashMap<>();
        Map<String, List<Double>> countryBudgets = new HashMap<>();

        for (Map.Entry<String, Double> entry : profitData.entrySet()) {
            String imdbId = entry.getKey();
            double profit = entry.getValue();
            double revenue = revenueData.getOrDefault(imdbId, 0.0);
            double budget = budgetData.getOrDefault(imdbId, 0.0);

            if (countryData.containsKey(imdbId)) {
                String[] countries = countryData.get(imdbId).split(";");
                for (String country : countries) {
                    if (targetCountries.contains(country)) {
                        countryProfits.putIfAbsent(country, new ArrayList<>());
                        countryRevenues.putIfAbsent(country, new ArrayList<>());
                        countryBudgets.putIfAbsent(country, new ArrayList<>());
                        countryProfits.get(country).add(profit);
                        countryRevenues.get(country).add(revenue);
                        countryBudgets.get(country).add(budget);
                    }
                }
            }
        }

        // Example data for Rating/Revenue correlation
        List<Double> ratings = List.of(3.5, 4.0, 4.5, 5.0, 3.0);
        List<Double> revenue = List.of(1000.0, 1200.0, 1300.0, 1500.0, 900.0);

        double pearson = calculatePearson(ratings, revenue);
        double spearman = calculateSpearman(ratings, revenue);

        System.out.println("Pearson Correlation (Ratings & Revenue): " + pearson);
        System.out.println("Spearman Correlation (Ratings & Revenue): " + spearman);

        List<Double> countryCodes = new ArrayList<>();
        List<Double> profitsList = new ArrayList<>();

        for (String country : targetCountries) {
            countryCodes.add((double) country.hashCode());  // Country code for regression
            profitsList.add(countryProfits.getOrDefault(country, new ArrayList<>()).get(0)); // Taking first value for simplicity
        }

        double[] coefficients = performLinearRegression(countryCodes, profitsList);

        System.out.println("\nRegression Coefficients:");
        System.out.println("Intercept: " + coefficients[0]);
        System.out.println("Slope: " + coefficients[1]);

        System.out.println("\nPrediction of Profit Based on Production Country:");
        for (String country : targetCountries) {
            double countryCode = (double) country.hashCode();
            double predictedProfit = predictProfit(countryCode, coefficients);
            System.out.println("Country: " + country + ", Predicted Profit: " + predictedProfit);
        }

        // Descriptive Statistics for Profit/Revenue/Budget (Mean)
        System.out.println("\nDescriptive Statistics for Countries:");
        for (String country : targetCountries) {
            List<Double> profitsListCountry = countryProfits.getOrDefault(country, new ArrayList<>());
            List<Double> revenuesListCountry = countryRevenues.getOrDefault(country, new ArrayList<>());
            List<Double> budgetsListCountry = countryBudgets.getOrDefault(country, new ArrayList<>());

            double meanProfit = calculateMean(profitsListCountry); // Calculate mean profit
            double meanRevenue = calculateMean(revenuesListCountry); // Calculate mean revenue
            double meanBudget = calculateMean(budgetsListCountry); // Calculate mean budget

            System.out.println("Country: " + country + ", Mean Profit: " + meanProfit + ", Mean Revenue: " + meanRevenue + ", Mean Budget: " + meanBudget);
        }
    }
}
