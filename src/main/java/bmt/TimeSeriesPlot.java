package bmt;

import org.jfree.chart.*;
import org.jfree.chart.plot.*;
import org.jfree.data.category.*;
import org.jfree.chart.axis.*;


import java.util.*;


public class TimeSeriesPlot {
    public static void main(String[] args) {
        // Example data: Replace with the data obtained from DataOrganizer
        // Data format: Map<Genre, Map<Year, List<Ratings>>>
        Map<String, Map<Integer, List<Double>>> genreRatings = DataOrganizer.organizeData(getMovieData());


        // Create a dataset for the time series plot
        DefaultCategoryDataset dataset = createDataset(genreRatings);


        // Create the chart
        JFreeChart chart = createChart(dataset);


        // Display the chart
        displayChart(chart);
    }


    // Method to create the dataset for the time series plot
    private static DefaultCategoryDataset createDataset(Map<String, Map<Integer, List<Double>>> genreRatings) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();


        for (Map.Entry<String, Map<Integer, List<Double>>> genreEntry : genreRatings.entrySet()) {
            for (Map.Entry<Integer, List<Double>> yearEntry : genreEntry.getValue().entrySet()) {
                double avgRating = DataOrganizer.calculateAverage(yearEntry.getValue());
                dataset.addValue(avgRating, genreEntry.getKey(), yearEntry.getKey());
            }
        }


        return dataset;
    }


    // Method to create the time series chart
    private static JFreeChart createChart(DefaultCategoryDataset dataset) {
        return ChartFactory.createLineChart(
            "Average Rating per Year by Genre",  // Chart title
            "Year",                             // X-axis label
            "Average Rating",                   // Y-axis label
            dataset,                            // Dataset
            PlotOrientation.VERTICAL,           // Plot orientation
            true,                               // Include legend
            true,                               // Tooltips
            false                               // URLs
        );
    }


    // Method to display the chart
    private static void displayChart(JFreeChart chart) {
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(800, 600));


        // Create a JFrame to display the chart
        javax.swing.JFrame frame = new javax.swing.JFrame();
        frame.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(chartPanel);
        frame.pack();
        frame.setVisible(true);
    }


    // Sample method to get movie data (replace with actual data source)
    private static List<Movie> getMovieData() {
        return Arrays.asList(


            new Movie(274, "action", 2010, 6.2),
            new Movie(297, "action", 2010, 5.4),
            new Movie(355, "action", 2010, 6.7),
            new Movie(625, "action", 2011, 6.5),
            new Movie(637, "action", 2011, 5.1),
            new Movie(725, "action", 2011, 6.6),
            new Movie(615, "action", 2012, 5.9),
            new Movie(633, "action", 2012, 6.1),
            new Movie(638, "action", 2012, 4.4),
            new Movie(653, "action", 2012, 6.2),
            new Movie(654, "action", 2012, 5.2),
            new Movie(658, "action", 2012, 5.8),
            new Movie(679, "action", 2012, 4.1),
            new Movie(799, "action", 2013, 4.7),
            new Movie(816, "action", 2013, 4.9),
            new Movie(823, "action", 2013, 5.3),
            new Movie(834, "action", 2013, 5.1),
            new Movie(839, "action", 2013, 4.8),
            new Movie(815, "action", 2014, 6.3),
            new Movie(837, "action", 2014, 5.2),
            new Movie(841, "action", 2014, 5.9),
            new Movie(848, "action", 2014, 3.4),
            new Movie(1078, "action", 2015, 4.4),
            new Movie(1080, "action", 2015, 5.1),
            new Movie(1084, "action", 2015, 5.7),
            new Movie(1091, "action", 2015, 6.1),
            new Movie(1138, "action", 2016, 6.5),
            new Movie(1141, "action", 2016, 5.1),
            new Movie(1144, "action", 2016, 4.8),
            new Movie(1664, "action", 2017, 5.1),
            new Movie(1711, "action", 2017, 5.3),
            new Movie(1719, "action", 2017, 5.6),
            new Movie(2883, "action", 2017, 6.1),
            new Movie(1647, "action", 2018, 4.1),
            new Movie(1658, "action", 2018, 6.1),
            new Movie(1685, "action", 2018, 4.8),
            new Movie(1706, "action", 2018, 4.9),
            new Movie(1721, "action", 2018, 6.8),
            new Movie(1733, "action", 2018, 5.7),
            new Movie(369, "animation", 2010, 6.1),
            new Movie(418, "animation", 2010, 5.3),
            new Movie(428, "animation", 2010, 4.1),
            new Movie(441, "animation", 2010, 5.5),
            new Movie(631, "animation", 2011, 5.1),
            new Movie(647, "animation", 2011, 4.9),
            new Movie(673, "animation", 2011, 6.5),
            new Movie(688, "animation", 2011, 5.1),
            new Movie(698, "animation", 2011, 5.5),
            new Movie(757, "animation", 2011, 4.8),
            new Movie(616, "animation", 2012, 4.4),
            new Movie(634, "animation", 2012, 5.3),
            new Movie(636, "animation", 2012, 5.1),
            new Movie(846, "animation", 2013, 5.3),
            new Movie(853, "animation", 2013, 6.3),
            new Movie(869, "animation", 2013, 6.6),
            new Movie(883, "animation", 2013, 3.9),
            new Movie(1097, "animation", 2015, 3.1),
            new Movie(1257, "animation", 2015, 4.8),
            new Movie(1279, "animation", 2015, 7.7),
            new Movie(1306, "animation", 2015, 5.8),
            new Movie(1308, "animation", 2015, 6.3),
            new Movie(1369, "animation", 2015, 4.7),
            new Movie(1500, "animation", 2015, 5.4),
            new Movie(1527, "animation", 2015, 5.5),
            new Movie(1599, "animation", 2015, 5.1),
            new Movie(1135, "animation", 2016, 6.1),
            new Movie(1668, "animation", 2017, 5.5),
            new Movie(1703, "animation", 2018, 4.2),
            new Movie(1716, "animation", 2018, 4.1),
            new Movie(1731, "animation", 2018, 5.6),
            new Movie(2960, "animation", 2019, 6.8),
            new Movie(3055, "animation", 2020, 5.6),
            new Movie(3060, "animation", 2020, 5.4),
            new Movie(4323, "animation", 2020, 6.9),
            new Movie(4338, "animation", 2020, 6.1),
            new Movie(4365, "animation", 2020, 4.8),
            new Movie(312, "comedy", 2010, 6.4),
            new Movie(342, "comedy", 2010, 6.6),
            new Movie(343, "comedy", 2010, 5.7),
            new Movie(365, "comedy", 2010, 3.9),
            new Movie(380, "comedy", 2010, 5.1),
            new Movie(414, "comedy", 2010, 5.4),
            new Movie(482, "comedy", 2010, 5.8),
            new Movie(499, "comedy", 2010, 4.9),
            new Movie(662, "comedy", 2011, 4.4),
            new Movie(676, "comedy", 2011, 3.8),
            new Movie(700, "comedy", 2011, 4.5),
            new Movie(718, "comedy", 2011, 4.4),
            new Movie(729, "comedy", 2011, 6.4),
            new Movie(641, "comedy", 2012, 3.4),
            new Movie(643, "comedy", 2012, 5.0),
            new Movie(646, "comedy", 2012, 4.2),
            new Movie(657, "comedy", 2012, 4.8),
            new Movie(661, "comedy", 2012, 5.6),
            new Movie(667, "comedy", 2012, 4.9),
            new Movie(885, "comedy", 2013, 4.2),
            new Movie(891, "comedy", 2013, 6.5),
            new Movie(894, "comedy", 2013, 5.7),
            new Movie(1056, "comedy", 2013, 3.6),
            new Movie(1066, "comedy", 2013, 5.8),
            new Movie(804, "comedy", 2014, 5.6),
            new Movie(812, "comedy", 2014, 7.4),
            new Movie(821, "comedy", 2014, 5.0),
            new Movie(828, "comedy", 2014, 7.7),
            new Movie(1063, "comedy", 2014, 5.2),
            new Movie(1165, "comedy", 2015, 6.0),
            new Movie(1173, "comedy", 2015, 6.1),
            new Movie(1210, "comedy", 2015, 4.9),
            new Movie(1220, "comedy", 2015, 3.6),
            new Movie(1235, "comedy", 2015, 5.5),
            new Movie(1241, "comedy", 2015, 5.7),
            new Movie(1115, "comedy", 2016, 4.9),
            new Movie(1119, "comedy", 2016, 4.9),
            new Movie(1134, "comedy", 2016, 7.4),
            new Movie(1613, "comedy", 2016, 4.2),
            new Movie(1625, "comedy", 2016, 5.0),
            new Movie(1627, "comedy", 2016, 7.9),
            new Movie(1628, "comedy", 2016, 6.4),
            new Movie(1709, "comedy", 2017, 5.1),
            new Movie(1729, "comedy", 2017, 6.2),
            new Movie(2855, "comedy", 2017, 6.2),
            new Movie(2889, "comedy", 2017, 6.0),
            new Movie(2901, "comedy", 2017, 7.1),
            new Movie(2909, "comedy", 2017, 5.1),
            new Movie(1648, "comedy", 2018, 5.5),
            new Movie(2999, "comedy", 2019, 6.4),
            new Movie(3054, "comedy", 2020, 6.5),
            new Movie(3056, "comedy", 2020, 6.4),
            new Movie(3058, "comedy", 2020, 5.0),
            new Movie(3081, "comedy", 2020, 7.0),
            new Movie(3084, "comedy", 2020, 6.3)
        );
    }
}




