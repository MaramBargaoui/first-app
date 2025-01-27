package bmt;

import java.io.*;
import java.sql.*;
import java.util.*;

public class CSVToSQLite {
    public static void main(String[] args) {
        // Updated SQLite database path based on your provided path
        String jdbcURL = "jdbc:sqlite:C:/Users/maram/OneDrive/Desktop/java project/first-app/movies.db"; 
        Connection connection = null;

        try {
            // Establish a connection to SQLite database
            connection = DriverManager.getConnection(jdbcURL);
            connection.setAutoCommit(false); // Disable auto-commit for batch processing

            // Create tables in the database
            createTables(connection);
            try (Statement stmt = connection.createStatement()) {
                stmt.execute("PRAGMA foreign_keys = ON;");
            }
            
            // Insert data and get row counts for each dataset
            int moviesLibraryRows = insertCSVData(connection, "src/main/java/bmt/movies_library.csv", "movies_library");
            int imdbRatingsRows = insertCSVData(connection, "src/main/java/bmt/imdb_ratings.csv", "imdb_ratings");
            int moviesRevenuesRows = insertCSVData(connection, "src/main/java/bmt/movies_revenues.csv", "movies_revenues");

            connection.commit(); // Commit the transaction

            // Log number of rows inserted for each table
            System.out.println("Inserted " + moviesLibraryRows + " rows into movies_library.");
            System.out.println("Inserted " + imdbRatingsRows + " rows into imdb_ratings.");
            System.out.println("Inserted " + moviesRevenuesRows + " rows into movies_revenues.");

            System.out.println("Data import successful!");

        } catch (SQLException | IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) {
                    connection.close(); // Close the connection
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private static void createTables(Connection connection) throws SQLException {
        // Drop existing tables if they exist
        String dropMoviesLibraryTable = "DROP TABLE IF EXISTS movies_library";
        String dropImdbRatingsTable = "DROP TABLE IF EXISTS imdb_ratings";
        String dropMoviesRevenuesTable = "DROP TABLE IF EXISTS movies_revenues";

        // Execute the drop statements to remove existing tables
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(dropMoviesLibraryTable);
            stmt.execute(dropImdbRatingsTable);
            stmt.execute(dropMoviesRevenuesTable);
        }

        // Create new tables with the correct structure
        String createMoviesLibraryTable = "CREATE TABLE IF NOT EXISTS movies_library (" +
                "imdb_id TEXT PRIMARY KEY, " +
                "movie_id INTEGER, " +
                "title TEXT, " +
                "genres TEXT, " +
                "production_countries TEXT, " +
                "release_year INTEGER" +
                ")";
        
        String createImdbRatingsTable = "CREATE TABLE IF NOT EXISTS imdb_ratings (" +
                "imdb_id TEXT, " +
                "average_rating FLOAT, " +
                "num_votes INTEGER, " +
                "FOREIGN KEY(imdb_id) REFERENCES movies_library(imdb_id)" +
                ")";
        
        String createMoviesRevenuesTable = "CREATE TABLE IF NOT EXISTS movies_revenues (" +
                "imdb_id TEXT, " +
                "budget REAL, " + 
                "revenue REAL, " + 
                "profit REAL, " +
                "FOREIGN KEY(imdb_id) REFERENCES movies_library(imdb_id)" +
                ")";

        try (Statement stmt = connection.createStatement()) {
            stmt.execute(createMoviesLibraryTable);
            stmt.execute(createImdbRatingsTable);
            stmt.execute(createMoviesRevenuesTable);
        }
    }

    private static int insertCSVData(Connection connection, String filePath, String tableName) throws SQLException, IOException {
        int rowCount = 0;
        // Prepare the SQL insert statement
        String insertSQL = getInsertSQL(tableName);
        try (PreparedStatement pstmt = connection.prepareStatement(insertSQL);
             PreparedStatement checkStmt = connection.prepareStatement("SELECT COUNT(*) FROM " + tableName + " WHERE imdb_id = ?");
             BufferedReader br = new BufferedReader(new FileReader(filePath))) {

            String line;
            br.readLine(); // Skip the header line
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)"); // Handles quoted commas

                // Check for the expected number of columns
                int expectedColumns = (tableName.equals("movies_library")) ? 6
                                : (tableName.equals("imdb_ratings")) ? 3
                                : (tableName.equals("movies_revenues")) ? 4
                                : 0;

                if (data.length < expectedColumns) {
                    System.out.println("Skipped row due to column mismatch: " + Arrays.toString(data));
                    continue;
                }

                String imdbId = data[0].trim();
                if (imdbId.isEmpty()) {
                    System.out.println("Skipped row due to missing imdb_id: " + Arrays.toString(data));
                    continue;
                }

                // Check for duplicates
                checkStmt.setString(1, imdbId);
                try (ResultSet rs = checkStmt.executeQuery()) {
                    if (rs.next() && rs.getInt(1) > 0) {
                        System.out.println("Skipped duplicate imdb_id: " + imdbId);
                        continue;
                    }
                }

                try {
                    // Insert data based on the table structure
                    pstmt.setString(1, imdbId);
                    if (tableName.equals("movies_library")) {
                        pstmt.setInt(2, Integer.parseInt(data[1].trim())); // movie_id
                        pstmt.setString(3, data[2].trim()); // title
                        pstmt.setString(4, data[3].replaceAll("[\\[\\]\"]", "").trim()); // genres
                        pstmt.setString(5, data[4].replaceAll("[\\[\\]\"]", "").trim()); // production_countries
                        pstmt.setInt(6, Integer.parseInt(data[5].trim())); // release_year
                    } else if (tableName.equals("imdb_ratings")) {
                        pstmt.setFloat(2, Float.parseFloat(data[1].trim())); // average_rating
                        pstmt.setInt(3, Integer.parseInt(data[2].trim())); // num_votes
                    } else if (tableName.equals("movies_revenues")) {
                        pstmt.setLong(2, Long.parseLong(data[1].trim())); // budget
                        pstmt.setLong(3, Long.parseLong(data[2].trim())); // revenue
                        pstmt.setLong(4, Long.parseLong(data[3].trim())); // profit
                    }

                    pstmt.addBatch();
                    rowCount++;
                } catch (Exception e) {
                    System.out.println("Skipped row due to data processing error: " + Arrays.toString(data));
                }

                if (rowCount % 100 == 0) {
                    pstmt.executeBatch();
                }
            }

            // Insert any remaining rows
            pstmt.executeBatch();
        }

        return rowCount;
    }

    private static String getInsertSQL(String tableName) {
        if (tableName.equals("movies_library")) {
            return "INSERT INTO movies_library (imdb_id, movie_id, title, genres, production_countries, release_year) VALUES (?, ?, ?, ?, ?, ?)";
        } else if (tableName.equals("imdb_ratings")) {
            return "INSERT INTO imdb_ratings (imdb_id, average_rating, num_votes) VALUES (?, ?, ?)";
        } else if (tableName.equals("movies_revenues")) {
            return "INSERT INTO movies_revenues (imdb_id, budget, revenue, profit) VALUES (?, ?, ?, ?)";
        }
        return "";
    }
}
