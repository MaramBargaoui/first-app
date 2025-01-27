package bmt;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;

public class ChartExample {
    public static void main(String[] args) {
        // Create a dataset for the chart
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        
        // Add data to the dataset
        dataset.addValue(1.0, "Series1", "Category1");
        dataset.addValue(4.0, "Series1", "Category2");
        dataset.addValue(3.0, "Series1", "Category3");

        // Create a bar chart using the dataset
        JFreeChart chart = ChartFactory.createBarChart(
            "Bar Chart Example",   // Chart title
            "Category",            // X-axis Label
            "Value",               // Y-axis Label
            dataset               // Dataset
        );

        // Create a chart panel to display the chart
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(800, 600));  // Set the size of the chart

        // Create a JFrame to display the chart
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(chartPanel);  // Add the chart panel to the frame
        frame.pack();
        frame.setVisible(true);  // Make the window visible
    }
}

