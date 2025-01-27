package bmt;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.ArrayList;

public class AppTest {

    // Test case for Descriptive Statistics - Mean
    @Test
    public void testCalculateMean() {
        List<Double> data = List.of(1.0, 2.0, 3.0, 4.0, 5.0);
        double mean = App.calculateMean(data);
        assertEquals(3.0, mean, 0.0001);  // We expect the mean to be 3.0
    }

    // Test case for Pearson Correlation Coefficient
    @Test
    public void testCalculatePearson() {
        List<Double> x = List.of(1.0, 2.0, 3.0);
        List<Double> y = List.of(2.0, 4.0, 6.0);
        double correlation = App.calculatePearson(x, y);
        assertEquals(1.0, correlation, 0.0001);  // Perfect positive correlation
    }

    // Test case for Spearman Rank Correlation Coefficient
    @Test
    public void testCalculateSpearman() {
        List<Double> x = List.of(1.0, 2.0, 3.0);
        List<Double> y = List.of(2.0, 3.0, 1.0);
        double correlation = App.calculateSpearman(x, y);
        assertEquals(-0.5, correlation, 0.0001);  // Adjusted the expected correlation value to -0.5
    }
    

    // Test case for Linear Regression - Country Codes vs Profits
    @Test
    public void testPerformLinearRegression() {
        List<Double> countryCodes = List.of(1.0, 2.0, 3.0, 4.0, 5.0);
        List<Double> profits = List.of(10.0, 20.0, 30.0, 40.0, 50.0);
        
        double[] coefficients = App.performLinearRegression(countryCodes, profits);
        assertNotNull(coefficients);
        assertEquals(0.0, coefficients[0], 0.0001);  // Intercept should be 0
        assertEquals(10.0, coefficients[1], 0.0001); // Slope should be 10 (perfect linear relationship)
    }

    // Test case for predicting profit based on country code using the regression model
    @Test
    public void testPredictProfit() {
        double[] coefficients = {0.0, 10.0};  // Intercept = 0, Slope = 10
        double predictedProfit = App.predictProfit(3.0, coefficients);
        assertEquals(30.0, predictedProfit, 0.0001);  // Expect 30.0 as predicted profit
    }

    // Test case for Predicting IMDb Rating based on votes
    @Test
    public void testPredictImdbRating() {
        double[] coefficients = {5.0, 0.0002};  // Example coefficients
        double predictedRating = App.predictImdbRating(10000.0, coefficients);
        assertEquals(7.0, predictedRating, 0.0001);  // Example expected rating
    }
}
