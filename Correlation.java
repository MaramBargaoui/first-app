

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;


import javax.swing.*;
import java.util.*;


public class Correlation {


    // Method to create and display a scatter plot
    public static void displayScatterPlot(Map<String, List<Double>> countryProfits, List<Integer> years, String chartTitle, String xLabel, String yLabel) {
        XYSeriesCollection dataset = new XYSeriesCollection();


        for (String country : countryProfits.keySet()) {
            XYSeries series = new XYSeries(country);


            List<Double> profits = countryProfits.get(country);
            for (int i = 0; i < profits.size(); i++) {
                series.add(years.get(i), profits.get(i)); // Add year (x-axis) and profit (y-axis) data
            }


            dataset.addSeries(series);
        }


        // Create scatter plot
        JFreeChart chart = ChartFactory.createScatterPlot(
                chartTitle, // Chart Title
                xLabel, // X-axis label
                yLabel, // Y-axis label
                dataset, // Data
                PlotOrientation.VERTICAL, // Plot orientation
                true, // Include legend
                true, // Tooltips
                false // URLs
        );


        // Display the scatter plot in a frame
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(800, 600));
        JFrame frame = new JFrame(chartTitle);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(chartPanel);
        frame.pack();
        frame.setVisible(true);
    }


    public static void main(String[] args) {
        // Example data: Profits over the years for 5 countries
        List<Integer> years = List.of(2010, 2011, 2012, 2013, 2014, 2015);


        Map<String, List<Double>> countryProfits = new HashMap<>();


        // Profits for each country over the years
        countryProfits.put("US", List.of(200.0, 220.0, 240.0, 260.0, 280.0, 300.0));
        countryProfits.put("GB", List.of(150.0, 160.0, 170.0, 180.0, 190.0, 210.0));
        countryProfits.put("IN", List.of(100.0, 110.0, 120.0, 130.0, 140.0, 150.0));
        countryProfits.put("JP", List.of(180.0, 190.0, 200.0, 220.0, 240.0, 260.0));
        countryProfits.put("TH", List.of(90.0, 95.0, 100.0, 110.0, 120.0, 130.0));


        // Plot the data as a line chart for profit over the years by country
        displayLineChart(countryProfits, years, "Profit Over the Years by Country (Line Chart)", "Year", "Profit");


        // Plot the data as a scatter plot for profit over the years by country
        displayScatterPlot(countryProfits, years, "Profit Over the Years by Country (Scatter Plot)", "Year", "Profit");
    }


    // Method to create and display a line chart (existing code for reference)
    public static void displayLineChart(Map<String, List<Double>> countryProfits, List<Integer> years, String chartTitle, String xLabel, String yLabel) {
        XYSeriesCollection dataset = new XYSeriesCollection();


        for (String country : countryProfits.keySet()) {
            XYSeries series = new XYSeries(country);


            List<Double> profits = countryProfits.get(country);
            for (int i = 0; i < profits.size(); i++) {
                series.add(years.get(i), profits.get(i));
            }


            dataset.addSeries(series);
        }


        JFreeChart chart = ChartFactory.createXYLineChart(
                chartTitle, // Chart Title
                xLabel, // X-axis label
                yLabel, // Y-axis label
                dataset, // Data
                PlotOrientation.VERTICAL, // Plot orientation
                true, // Include legend
                true, // Tooltips
                false // URLs
        );


        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(800, 600));
        JFrame frame = new JFrame(chartTitle);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(chartPanel);
        frame.pack();
        frame.setVisible(true);
    }
}



