package bmt;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;


import javax.swing.*;
import java.util.Map; // Import for Map
import java.util.HashMap; // Import for HashMap


public class PieChartGenres {


    public static void displayPieChart(String chartTitle, Map<String, Double> data) {
        // Create dataset
        DefaultPieDataset dataset = new DefaultPieDataset();
        for (Map.Entry<String, Double> entry : data.entrySet()) {
            dataset.setValue(entry.getKey(), entry.getValue());
        }


        // Create Pie Chart
        JFreeChart chart = ChartFactory.createPieChart(
                chartTitle, // Chart title
                dataset, // Dataset
                true, // Include legend
                true, // Tooltips
                false // URLs
        );


        // Display the Pie Chart in a JFrame
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(800, 600));
        JFrame frame = new JFrame(chartTitle);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(chartPanel);
        frame.pack();
        frame.setVisible(true);
    }


    public static void main(String[] args) {
        // Example data: Genre distribution
        Map<String, Double> genreDistribution = new HashMap<>();
        genreDistribution.put("Action", 30.0);
        genreDistribution.put("Comedy", 20.0);
        genreDistribution.put("Drama", 25.0);
        genreDistribution.put("Horror", 10.0);
        genreDistribution.put("Sci-Fi", 15.0);


        // Display the pie chart
        displayPieChart("Genre Distribution", genreDistribution);
    }
}




