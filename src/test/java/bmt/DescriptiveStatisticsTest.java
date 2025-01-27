package bmt;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


import java.util.Arrays;
import java.util.List;


public class DescriptiveStatisticsTest {


    private final DescriptiveStatistics stats = new DescriptiveStatistics();


    @Test
    public void testCalculateMean() {
        List<Double> data = Arrays.asList(1.0, 2.0, 3.0, 4.0, 5.0);
        double expectedMean = 3.0;
        assertEquals(expectedMean, stats.calculateMean(data), 0.001);
    }


    @Test
    public void testCalculateMedian() {
        List<Double> data = Arrays.asList(1.0, 2.0, 3.0, 4.0, 5.0);
        double expectedMedian = 3.0;
        assertEquals(expectedMedian, stats.calculateMedian(data), 0.001);
    }


    @Test
    public void testCalculateMode() {
        List<Double> data = Arrays.asList(1.0, 2.0, 3.0, 2.0, 4.0, 2.0);
        double expectedMode = 2.0;
        assertEquals(expectedMode, stats.calculateMode(data), 0.001);
    }


    @Test
    public void testCalculateStandardDeviation() {
        List<Double> data = Arrays.asList(1.0, 2.0, 3.0, 4.0, 5.0);
        double expectedStandardDeviation = Math.sqrt(2.0); // Variance is 2, so SD is sqrt(2)
        assertEquals(expectedStandardDeviation, stats.calculateStandardDeviation(data), 0.001);
    }


    @Test
    public void testGenerateHistogramWithEmptyData() {
        List<Double> data = Arrays.asList();
        // No exception should be thrown, and we expect a message that there is no data for the histogram
        stats.generateHistogram(data, "Empty Data Histogram");
    }


    @Test
    public void testGenerateHistogramWithData() {
        List<Double> data = Arrays.asList(1.0, 2.0, 3.0, 4.0, 5.0);
        // The test will print the histogram output, no assertion needed as we are checking for successful execution
        stats.generateHistogram(data, "Data Histogram");
    }
}



