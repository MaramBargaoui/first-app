package bmt;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;
import java.util.List;

public class AppTest {

    @Test
    public void testCalculateMean() {
        List<Double> data = Arrays.asList(1.0, 2.0, 3.0, 4.0, 5.0);
        double expectedMean = 3.0;
        assertEquals(expectedMean, App.calculateMean(data), 0.001);
    }

    @Test
    public void testCalculatePearson() {
        List<Double> x = Arrays.asList(1.0, 2.0, 3.0, 4.0);
        List<Double> y = Arrays.asList(2.0, 4.0, 6.0, 8.0);
        double expectedPearson = 1.0;  // Perfect positive correlation
        assertEquals(expectedPearson, App.calculatePearson(x, y), 0.001);
    }

    @Test
    public void testCalculateSpearman() {
        List<Double> x = Arrays.asList(1.0, 2.0, 3.0, 4.0);
        List<Double> y = Arrays.asList(2.0, 4.0, 6.0, 8.0);
        double expectedSpearman = 1.0;  // Perfect positive correlation
        assertEquals(expectedSpearman, App.calculateSpearman(x, y), 0.001);
    }

    @Test
    public void testPerformLinearRegression() {
        List<Double> countryCodes = Arrays.asList(1.0, 2.0, 3.0, 4.0);
        List<Double> profits = Arrays.asList(1000.0, 2000.0, 3000.0, 4000.0);
        double[] coefficients = App.performLinearRegression(countryCodes, profits);

        // Check if intercept and slope are reasonable for a linear relationship
        assertNotNull(coefficients);
        assertEquals(0.0, coefficients[0], 1000); // Rough check for intercept (expect it to be near 0)
        assertEquals(1000.0, coefficients[1], 100); // Slope expected to be approximately 1000
    }

    @Test
    public void testPredictProfit() {
        double[] coefficients = {1000.0, 1000.0};  // intercept = 1000, slope = 1000
        double countryCode = 5.0;
        double predictedProfit = App.predictProfit(countryCode, coefficients);

        // Expected profit = 1000 + (1000 * 5) = 6000
        assertEquals(6000.0, predictedProfit, 0.001);
    }
}
