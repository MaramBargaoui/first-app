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

// Abstract Class for Statistics (Abstraction)
abstract class Statistics {
    public abstract double calculate(List<Double> x, List<Double> y);
}

// Encapsulation: Create a class to hold data related to countries
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

    // Getters and Setters (Encapsulation)
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public double getMeanProfit() {
        return meanProfit;
    }

    public void setMeanProfit(double meanProfit) {
        this.meanProfit = meanProfit;
    }

    public double getMeanRevenue() {
        return meanRevenue;
    }

    public void setMeanRevenue(double meanRevenue) {
        this.meanRevenue = meanRevenue;
    }

    public double getMeanBudget() {
        return meanBudget;
    }

    public void setMeanBudget(double meanBudget) {
        this.meanBudget = meanBudget;
    }

    public void displayCountryData() {
        System.out.println("Country: " + country + ", Mean Profit: " + meanProfit + ", Mean Revenue: " + meanRevenue + ", Mean Budget: " + meanBudget);
    }
}

// Concrete class for Pearson Correlation (Polymorphism, Inheritance, Abstraction)
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

// Concrete class for Spearman Correlation (Polymorphism, Inheritance, Abstraction)
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

    // Helper method to calculate the ranks of a list of values
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

    // Method for linear regression on country codes and profits
    public double[] performLinearRegression(List<Double> countryCodes, List<Double> profits) {
        int n = countryCodes.size();
        double[][] xData = new double[n][1];
        double[] yData = new double[n];

        for (int i = 0; i < n; i++) {
            xData[i][0] = countryCodes.get(i);
            yData[i] = profits.get(i);
        }

        org.apache.commons.math3.stat.regression.OLSMultipleLinearRegression regression = new org.apache.commons.math3.stat.regression.OLSMultipleLinearRegression();
        regression.newSampleData(yData, xData);

        return regression.estimateRegressionParameters();
    }

    // Non-static calculateMean method
    public double calculateMean(List<Double> data) {
        double sum = 0.0;
        for (double value : data) {
            sum += value;
        }
        return sum / data.size();
    }

    public static void main(String[] args) {
        // Setup your file paths here (same as in the code)
        String revenuesFilePath = "src/main/java/bmt/movies_revenues.csv";
        String libraryFilePath = "src/main/java/bmt/movies_library.csv";
        String imdbRatingsFilePath = "src/main/java/bmt/imdb_ratings.csv";  // New CSV file

        Map<String, Double> budgetData = new HashMap<>();
        Map<String, Double> revenueData = new HashMap<>();
        Map<String, Double> profitData = new HashMap<>();
        Map<String, String> countryData = new HashMap<>();
        Map<String, Double> imdbRatingsData = new HashMap<>();  // To store IMDb Ratings

        // Read revenues data (same as your previous code)
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

        // Read library data (same as your previous code)
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

        // Read IMDb ratings data (Newly added CSV file)
        try (BufferedReader br = new BufferedReader(new FileReader(imdbRatingsFilePath))) {
            String line;
            boolean isFirstLine = true;
            while ((line = br.readLine()) != null) {
                if (isFirstLine) { isFirstLine = false; continue; }
                String[] values = line.split(",");
                String imdbId = values[0];
                double imdbRating = Double.parseDouble(values[1]);

                imdbRatingsData.put(imdbId, imdbRating);
            }
        } catch (IOException e) {
            System.out.println("Error reading the IMDb ratings file: " + e.getMessage());
        }

        Set<String> targetCountries = new HashSet<>();
        Collections.addAll(targetCountries, "US", "GB", "AU", "IN", "JP", "TH", "EG", "LB", "CA", "AR", "DK");

        // Calculate and print Descriptive Statistics
        System.out.println("Descriptive Statistics for Countries:");
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

        // Create and Display Encapsulated Country Data Objects
        App app = new App();
        for (String country : targetCountries) {
            List<Double> profitsListCountry = countryProfits.getOrDefault(country, new ArrayList<>());
            List<Double> revenuesListCountry = countryRevenues.getOrDefault(country, new ArrayList<>());
            List<Double> budgetsListCountry = countryBudgets.getOrDefault(country, new ArrayList<>());

            double meanProfit = app.calculateMean(profitsListCountry); // Calculate mean profit
            double meanRevenue = app.calculateMean(revenuesListCountry); // Calculate mean revenue
            double meanBudget = app.calculateMean(budgetsListCountry); // Calculate mean budget

            // Encapsulate country data and display
            CountryData countryDataObj = new CountryData(country, meanProfit, meanRevenue, meanBudget);
            countryDataObj.displayCountryData();
        }

        // Perform Pearson and Spearman correlation
        PearsonCorrelation pearsonCalculator = new PearsonCorrelation();
        SpearmanCorrelation spearmanCalculator = new SpearmanCorrelation();

        List<Double> ratings = List.of(3.5, 4.0, 4.5, 5.0, 3.0);
        List<Double> revenue = List.of(1000.0, 1200.0, 1300.0, 1500.0, 900.0);

        double pearson = pearsonCalculator.calculate(ratings, revenue);
        double spearman = spearmanCalculator.calculate(ratings, revenue);

        System.out.println("\nPearson Correlation (Ratings & Revenue): " + pearson);
        System.out.println("Spearman Correlation (Ratings & Revenue): " + spearman);

        // Perform linear regression and make predictions (similar to before)
    }
}
