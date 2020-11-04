
package GUI.Controllers;


import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Dialog;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import satproblem.ExecutionController;
import satproblem.SATPROBLEM;
import static satproblem.SATPROBLEM.inputFile;

/**
 * FXML Controller class
 *
 * @author PC
 */
public class Graph_GUIController implements Initializable
{
    public static HashMap<Integer,Double> resultsMap;
    public static String xAxisLabel;
    
    
    @FXML
    private  BarChart<String,Number> barChart;

    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        //CREATING THE GRAPH
            CategoryAxis xAxis=new CategoryAxis();
            xAxis.setLabel(xAxisLabel);
            NumberAxis yAxis=new NumberAxis(0, 100, 5);
            yAxis.setLabel("Best Fitness");
            final BarChart<String,Number> bChart=new BarChart<>(xAxis,yAxis);
             bChart.setTitle("Statistics");
            Platform.runLater(() -> {
                ((VBox)(barChart.getParent())).getChildren().set(0,bChart);
                   });
            
            
          XYChart.Series dataSerie= new XYChart.Series<>();
          dataSerie.setName("Instance : "+GA_GUIController.PARAMS.get("CNFFileName"));
          
          resultsMap.forEach((Integer t, Double u) -> {
                    
                    dataSerie.getData().add(new XYChart.Data<>(String.valueOf(t), (Number)u));
                   
                    Platform.runLater(() -> {
                        bChart.getData().add(dataSerie);
                        
                    });
                    
                });
    }
    
}
