package bmt;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class AppTest {

    // Test the Linear Regression method
    @Test
    public void testPerformLinearRegression() {
        App app = new App();

        List<Double> xValues = List.of(1.0, 2.0, 3.0, 4.0, 5.0);
        List<Double> yValues = List.of(2.0, 3.0, 4.0, 5.0, 6.0);

        double[] coefficients = app.performLinearRegression(xValues, yValues);

        // Check if the intercept and slope are within expected values
        assertNotNull(coefficients);
        assertEquals(1.0, coefficients[0], 0.1); // Intercept should be close to 1
        assertEquals(1.0, coefficients[1], 0.1); // Slope should be close to 1
    }

    // Test the Pearson Correlation method
    @Test
    public void testPearsonCorrelation() {
        PearsonCorrelation pearsonCorrelation = new PearsonCorrelation();

        List<Double> xValues = List.of(1.0, 2.0, 3.0, 4.0, 5.0);
        List<Double> yValues = List.of(1.0, 2.0, 3.0, 4.0, 5.0);

        double correlation = pearsonCorrelation.calculate(xValues, yValues);

        // Pearson correlation should be 1 for perfectly correlated data
        assertEquals(1.0, correlation, 0.1);
    }

    // Test the Spearman Correlation method
    @Test
    public void testSpearmanCorrelation() {
        SpearmanCorrelation spearmanCorrelation = new SpearmanCorrelation();

        List<Double> xValues = List.of(1.0, 2.0, 3.0, 4.0, 5.0);
        List<Double> yValues = List.of(1.0, 2.0, 3.0, 4.0, 5.0);

        double correlation = spearmanCorrelation.calculate(xValues, yValues);

        // Spearman correlation should be 1 for perfectly correlated data
        assertEquals(1.0, correlation, 0.1);
    }

    // Test the mean calculation method
    @Test
    public void testCalculateMean() {
        App app = new App();

        List<Double> values = List.of(1.0, 2.0, 3.0, 4.0, 5.0);

        double mean = app.calculateMean(values);

        // Mean should be (1+2+3+4+5) / 5 = 3
        assertEquals(3.0, mean, 0.1);
    }

    // Test prediction of profit based on regression coefficients
    @Test
    public void testPredictProfit() {
        double[] coefficients = {1.0, 2.0}; // Intercept = 1, Slope = 2
        double predictedProfit = App.predictProfit(3.0, coefficients);

        // Predicted profit should be 1 + 2 * 3 = 7
        assertEquals(7.0, predictedProfit, 0.1);
    }

    // Test prediction of revenue based on regression coefficients
    @Test
    public void testPredictRevenue() {
        double[] coefficients = {1.0, 2.0}; // Intercept = 1, Slope = 2
        double predictedRevenue = App.predictRevenue(3.0, coefficients);

        // Predicted revenue should be 1 + 2 * 3 = 7
        assertEquals(7.0, predictedRevenue, 0.1);
    }

    // Test prediction of IMDb rating based on votes
    @Test
    public void testPredictImdbRating() {
        double[] coefficients = {5.0, 0.0005}; // Intercept = 5, Slope = 0.0005
        double predictedImdbRating = App.predictImdbRating(10000.0, coefficients);

        // Predicted IMDb rating should be 5 + 0.0005 * 10000 = 10
        assertEquals(10.0, predictedImdbRating, 0.1);
    }
}
