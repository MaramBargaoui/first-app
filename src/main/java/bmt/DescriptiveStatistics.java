package bmt;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class DescriptiveStatistics {

    // Method to calculate the mean of a list of data
    public double calculateMean(List<Double> data) {
        double sum = 0.0;
        for (double value : data) {
            sum += value;
        }
        return sum / data.size();
    }

    // Method to calculate the median of a list of data
    public double calculateMedian(List<Double> data) {
        Collections.sort(data);
        int middle = data.size() / 2;
        if (data.size() % 2 == 1) {
            return data.get(middle);
        } else {
            return (data.get(middle - 1) + data.get(middle)) / 2.0;
        }
    }

    // Method to calculate the mode of a list of data
    public double calculateMode(List<Double> data) {
        Map<Double, Integer> frequencyMap = new HashMap<>();
        for (double value : data) {
            frequencyMap.put(value, frequencyMap.getOrDefault(value, 0) + 1);
        }

        double mode = data.get(0);
        int maxCount = 0;
        for (Map.Entry<Double, Integer> entry : frequencyMap.entrySet()) {
            if (entry.getValue() > maxCount) {
                mode = entry.getKey();
                maxCount = entry.getValue();
            }
        }
        return mode;
    }

    // Method to calculate the standard deviation of a list of data
    public double calculateStandardDeviation(List<Double> data) {
        double mean = calculateMean(data);
        double sumSquaredDifferences = 0.0;
        for (double value : data) {
            sumSquaredDifferences += Math.pow(value - mean, 2);
        }
        return Math.sqrt(sumSquaredDifferences / data.size());
    }

    // Method to generate a histogram for a list of data
    public void generateHistogram(List<Double> data, String title) {
        if (data.isEmpty()) {
            System.out.println(title + ": No data available to generate a histogram.");
            return;
        }

        // Define the range and bins for the histogram
        int numBins = 10;
        double min = Collections.min(data);
        double max = Collections.max(data);
        double binSize = (max - min) / numBins;

        int[] bins = new int[numBins];

        // Populate the bins based on the data
        for (double value : data) {
            int binIndex = (int) ((value - min) / binSize);
            if (binIndex == numBins) {
                binIndex--; // Include the max value in the last bin
            }
            bins[binIndex]++;
        }

        // Print the histogram
        System.out.println("\n" + title + " Histogram:");
        for (int i = 0; i < numBins; i++) {
            double rangeStart = min + i * binSize;
            double rangeEnd = rangeStart + binSize;
            System.out.printf("[%6.2f - %6.2f]: ", rangeStart, rangeEnd);

            for (int j = 0; j < bins[i]; j++) {
                System.out.print("*");
            }
            System.out.println(" (" + bins[i] + ")");
        }
    }

    public static void main(String[] args) {
        // Define paths to the CSV files
        String revenuesFilePath = "src/main/java/bmt/movies_revenues.csv"; // Updated path
        String libraryFilePath = "src/main/java/bmt/movies_library.csv";  // Updated path

        Map<String, Double> budgetData = new HashMap<>();
        Map<String, Double> revenueData = new HashMap<>();
        Map<String, Double> profitData = new HashMap<>();
        Map<String, String> countryData = new HashMap<>();

        // Read the budget, revenue, and profit data from the revenues dataset
        try (BufferedReader br = new BufferedReader(new FileReader(revenuesFilePath))) {
            String line;
            boolean isFirstLine = true;
            while ((line = br.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }
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
                    System.out.println("Skipping invalid value.");
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading the revenues file: " + e.getMessage());
        }

        // Read the country data from the library dataset
        try (BufferedReader br = new BufferedReader(new FileReader(libraryFilePath))) {
            String line;
            boolean isFirstLine = true;
            while ((line = br.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }
                String[] values = line.split(",");
                String imdbId = values[0];
                String productionCountries = values[5].replaceAll("[\\[\\]'\"]", "").trim();

                if (!productionCountries.isEmpty()) {
                    countryData.put(imdbId, productionCountries);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading the library file: " + e.getMessage());
        }

        // Define the target countries
        Set<String> targetCountries = new HashSet<>(Arrays.asList("US", "GB", "AU", "IN", "JP", "TH", "EG", "LB", "CA", "AR", "DK"));

        Map<String, List<Double>> countryBudgets = new HashMap<>();
        Map<String, List<Double>> countryRevenues = new HashMap<>();
        Map<String, List<Double>> countryProfits = new HashMap<>();

        // Populate the country-specific data
        for (Map.Entry<String, Double> entry : budgetData.entrySet()) {
            String imdbId = entry.getKey();
            double budget = entry.getValue();
            double revenue = revenueData.getOrDefault(imdbId, 0.0);
            double profit = profitData.getOrDefault(imdbId, 0.0);

            if (countryData.containsKey(imdbId)) {
                String[] countries = countryData.get(imdbId).split(";");
                for (String country : countries) {
                    if (targetCountries.contains(country)) {
                        countryBudgets.putIfAbsent(country, new ArrayList<>());
                        countryRevenues.putIfAbsent(country, new ArrayList<>());
                        countryProfits.putIfAbsent(country, new ArrayList<>());
                        countryBudgets.get(country).add(budget);
                        countryRevenues.get(country).add(revenue);
                        countryProfits.get(country).add(profit);
                    }
                }
            }
        }

        // Initialize the DescriptiveStatistics instance
        DescriptiveStatistics stats = new DescriptiveStatistics();

        // Generate histograms and print descriptive statistics for each country
        for (String country : targetCountries) {
            System.out.println("\nCountry: " + country);

            List<Double> budgets = countryBudgets.getOrDefault(country, new ArrayList<>());
            List<Double> revenues = countryRevenues.getOrDefault(country, new ArrayList<>());
            List<Double> profits = countryProfits.getOrDefault(country, new ArrayList<>());

            stats.generateHistogram(budgets, "Budget for " + country);
            stats.generateHistogram(revenues, "Revenue for " + country);
            stats.generateHistogram(profits, "Profit for " + country);
        }
    }
}
