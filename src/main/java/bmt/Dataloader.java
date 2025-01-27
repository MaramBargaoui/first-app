package bmt;

import java.io.*;
import java.util.*;
import org.apache.commons.csv.*;
import java.nio.file.*;

public class DataLoader {

    // Method to load CSV data for imdb_ratings.csv
    public static Map<String, Double> loadImdbRatings(String filePath) throws IOException {
        Map<String, Double> imdbRatings = new HashMap<>();
        try (Reader reader = Files.newBufferedReader(Paths.get(filePath))) {
            CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withHeader());
            for (CSVRecord record : csvParser) {
                String imdbId = record.get("imdb_id");
                double rating = Double.parseDouble(record.get("average_rating"));
                imdbRatings.put(imdbId, rating);
            }
        }
        return imdbRatings;
    }

    // Method to load CSV data for movies_library.csv
    public static Map<String, Integer> loadMovieGenres(String filePath) throws IOException {
        Map<String, Integer> movieGenres = new HashMap<>();
        try (Reader reader = Files.newBufferedReader(Paths.get(filePath))) {
            CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withHeader());
            for (CSVRecord record : csvParser) {
                String imdbId = record.get("imdb_id");
                // You can convert genres into integers (e.g., using one-hot encoding, etc.)
                // For simplicity, we assume a placeholder value (e.g., 1).
                movieGenres.put(imdbId, 1);  // Dummy value
            }
        }
        return movieGenres;
    }

    // Method to load CSV data for movies_revenues.csv
    public static Map<String, Double> loadMovieProfits(String filePath) throws IOException {
        Map<String, Double> movieProfits = new HashMap<>();
        try (Reader reader = Files.newBufferedReader(Paths.get(filePath))) {
            CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withHeader());
            for (CSVRecord record : csvParser) {
                String imdbId = record.get("imdb_id");
                double profit = Double.parseDouble(record.get("profit"));
                movieProfits.put(imdbId, profit);
            }
        }
        return movieProfits;
    }

    // Combine the data into a unified structure (list of movies with multiple features)
    public static List<MovieData> combineData(String imdbRatingsPath, String movieGenresPath, String movieProfitsPath) throws IOException {
        Map<String, Double> imdbRatings = loadImdbRatings(imdbRatingsPath);
        Map<String, Integer> movieGenres = loadMovieGenres(movieGenresPath);
        Map<String, Double> movieProfits = loadMovieProfits(movieProfitsPath);

        List<MovieData> combinedData = new ArrayList<>();

        // Combine the data into a list of MovieData objects
        for (String imdbId : movieProfits.keySet()) {
            if (imdbRatings.containsKey(imdbId) && movieGenres.containsKey(imdbId)) {
                double imdbRating = imdbRatings.get(imdbId);
                int genre = movieGenres.get(imdbId);
                double profit = movieProfits.get(imdbId);
                combinedData.add(new MovieData(imdbId, imdbRating, genre, profit));
            }
        }

        return combinedData;
    }
}
