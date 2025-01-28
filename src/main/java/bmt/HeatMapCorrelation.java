package bmt;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;


public class HeatmapCorrelation extends Application {
    @Override
    public void start(Stage primaryStage) {
        // Example correlation matrix (simplified)
        double[][] correlationMatrix = {
                {1.0, 0.8, 0.4},
                {0.8, 1.0, 0.6},
                {0.4, 0.6, 1.0}
        };


        GridPane grid = new GridPane();


        // Iterate over the matrix and create rectangles
        for (int i = 0; i < correlationMatrix.length; i++) {
            for (int j = 0; j < correlationMatrix[i].length; j++) {
                double value = correlationMatrix[i][j];
                Rectangle rect = new Rectangle(50, 50);


                // Set the color based on correlation value (simple gradient)
                if (value > 0.7) {
                    rect.setFill(Color.GREEN);  // Strong positive correlation
                } else if (value < 0.3) {
                    rect.setFill(Color.RED);    // Strong negative correlation
                } else {
                    rect.setFill(Color.YELLOW); // Moderate correlation
                }


                grid.add(rect, j, i); // Add rectangle to the grid
            }
        }


        Scene scene = new Scene(grid, 300, 300);
        primaryStage.setTitle("Correlation Heatmap");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}




