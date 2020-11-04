
package GUI.Controllers;

import BSO.bases.BSOEvaluator;
import BSO.bases.HeapDanceList;
import BSO.sat.SATBee;
import BSO.sat.SATSolution;
import BSO.tabu.TabuList;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Dialog;
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
public class BSO_GUIController implements Initializable,ExecutionController {

    
    
    @FXML
    private Text execTime_text;

    @FXML
    private Text bestSol_text;

    @FXML
    private Text rate_text;

    @FXML
    private Text rate_text1;

    @FXML
    private Text rate_text2;

    @FXML
    private Text rate_text3;
    
    
    public static Stage stage;
    
    
    
    
    public static Execution_GUIController executionController;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        executionController=new Execution_GUIController();
        executionController.setExecController(this);
        
    }  
    
    
    
    
    
    
    @Override
    public void launch() {
        try {
            BSO.sat.SATInstance instance=BSO.sat.SATInstance.getInstanceFromFile(inputFile);
            
            SATSolution startPoint=SATSolution.generateRandomSolution(instance);
            
            HeapDanceList heapDances=new HeapDanceList(10000) {
                @Override
                public double evaluate(Object solution) {
                    SATSolution sol=(SATSolution) solution;
                    double eval = sol.getEvaluation();
                    if(eval<this.getMinEvaluation()) this.setMinEvaluation(eval);
                    
                    return this.getMinEvaluation();
                }
            };
            TabuList<SATSolution> tabuList=new TabuList<SATSolution>(10000000);
            BSOEvaluator evaluator=new BSOEvaluator(instance,tabuList,heapDances, 200000, 10000000);
            
           SATBee initialBee=new SATBee(instance);
           
           evaluator.lookForSolution(initialBee,executionController);
            
            
        } catch (IOException ex) {
            Logger.getLogger(SATPROBLEM.class.getName()).log(Level.SEVERE, null, ex);
        }
            
    }

    @Override
    public void showResults(Execution_GUIController resultsController) {
        
        execTime_text.setText(resultsController.getElapsedTime_text().getText());
        bestSol_text.setText(resultsController.getCurrectSol_text().getText());
        
        
    }

    
    
    
    
    
    
    
    
    
    @FXML
    void showExecutionDetails(ActionEvent event) {

    }
    
    
    @FXML
    void openBSOSettingsGUI(ActionEvent event) throws IOException {

        Dialog dialog=new Dialog();
        
        FXMLLoader loader=new FXMLLoader(getClass().getResource("/GUI/Views/BSO_Settings_GUI.fxml"));
        Parent fxml=(Parent) loader.load();
        /*dialog.initOwner(stage.getScene().getWindow());
        dialog.getDialogPane().getChildren().add(fxml);
        
        dialog.show();*/
        BSO_Settings_GUIController controller=(BSO_Settings_GUIController)loader.getController();
        
        Scene scene=new Scene(fxml);
        
        //stg.setScene(new Scene(alert.getDialogPane()));
        Stage stg=new Stage();
        stg.initOwner(stage);
        stg.setScene(scene);
        stg.initStyle(StageStyle.UNDECORATED);
        controller.setStage(stg);
       
        
        stg.show();
        
        System.out.println("GUI.Controllers.BSO_GUIController.openBSOSettingsGUI()");
    }
    
    
    
    
    
    
    
    
    
    
                // Getters & Setters 

    public static Stage getStage() {
        return stage;
    }

    public static void setStage(Stage stage) {
        BSO_GUIController.stage = stage;
    }

    
    
    
    
}
