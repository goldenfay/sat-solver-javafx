
package GUI.Controllers;

import animatefx.animation.FadeIn;
import java.net.URL;
import java.time.Clock;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.effect.Lighting;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.util.Duration;
import satproblem.Chronometer;
import satproblem.ExecutionController;

/**
 * FXML Controller class
 *
 * @author PC
 */
public class Blind_Execution_GUIController extends Execution_GUIController implements Initializable {

    
    @FXML
    private Text status_text;

    @FXML
    private Text elapsedTime_text;

    @FXML
    private Text iterationNbr_text;
//###########@@###########@@###########@@###########@@###########@@###########@@###########@@###########@@
    @FXML
    public  Text depth;
    
    @FXML
    public Text currentSolution;
            
    @FXML
    public Text nbrVisitedNodes;
//###########@@###########@@###########@@###########@@###########@@###########@@###########@@###########@@###########@@
    @FXML
    private Button starExecutionButton;
    @FXML
    private Button stopExecutionButton;
    
   
    @FXML
    private AnchorPane executionLaunchLayer;
    
    @FXML
    private AnchorPane executionAreaLayout;
    
    public static  ExecutionController execController;
    
    public static Thread executionThread;
    
    private Chronometer chrono;
    
    private Object besSolutionFound;

   
    public Blind_Execution_GUIController() {
        super();
    }
    
    //###########@@###########@@###########@@###########@@###########@@###########@@###########@@###########@@###########@
    
    public Text getDepth()
    {
        return depth;
    }
    public Text getCurrentSolution()
    {
        return currentSolution;
    }
    public Text getNbrVisitedNodes()
    {
        return nbrVisitedNodes;
    }
    
    
    
    
    
    
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        executionLaunchLayer.toFront();
           // Le lancement de l'execution doit etres en parallèles avec les autres traitement lors de click du boutton de lancement
        executionThread=new Thread( () -> {
            execController.launch();
        });
        
                // Gestion des Effects  de style Hover
       stopExecutionButton.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Lighting lightEffect = new Lighting();
                lightEffect.setDiffuseConstant(2.0);
                stopExecutionButton.setEffect(lightEffect);
            }
        });
        stopExecutionButton.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                stopExecutionButton.setEffect(null);
            }
        });
    }  
    
    
    @FXML
    @Override
    void StartExecution(ActionEvent event) {

        executionLaunchLayer.setVisible(false);
        executionAreaLayout.setEffect(null);
        stopExecutionButton.setVisible(true);
        
          // Lancer le thread d'execution
        try{
            executionThread.start();
        }catch(Exception ex){
           stopRunningThread();
           ex.printStackTrace();
        }
          // Cacher le boutton de Run et lancer le chronomettre
        new FadeIn(executionAreaLayout).play();
        chrono=new Chronometer(elapsedTime_text);
        chrono.start();
        
    }
    
    
    
    @FXML
    @Override
    void stopExecution(ActionEvent event) {

       stopExecutionButton.setVisible(false);
       chrono.stop();
       executionThread.stop();
       execController.showResults(this); }
      
    
    
    
    public void stopRunningThread(){
       stopExecutionButton.setVisible(false); 
       chrono.stop();
       execController.showResults(this);
       executionThread.interrupt();
    }
    
    
    
    
    
    
    
    
    
    
    
                    //Getters & Setters

    public Text getStatus_text() {
        return status_text;
    }

    public void setStatus_text(Text status_text) {
        this.status_text = status_text;
    }

 

    public Text getElapsedTime_text() {
        return elapsedTime_text;
    }

    public Button getStarExecutionButton() {
        return starExecutionButton;
    }

    public void setStarExecutionButton(Button starExecutionButton) {
        this.starExecutionButton = starExecutionButton;
    }

    public AnchorPane getExecutionLaunchLayer() {
        return executionLaunchLayer;
    }

    public void setExecutionLaunchLayer(AnchorPane executionLaunchLayer) {
        this.executionLaunchLayer = executionLaunchLayer;
    }

    public AnchorPane getExecutionAreaLayout() {
        return executionAreaLayout;
    }

    public void setExecutionAreaLayout(AnchorPane executionAreaLayout) {
        this.executionAreaLayout = executionAreaLayout;
    }

    public ExecutionController getExecController() {
        return execController;
    }

    public void setExecController(ExecutionController execController) {
        this.execController = execController;
    }

    public Object getBesSolutionFound() {
        return besSolutionFound;
    }

    public void setBesSolutionFound(Object besSolutionFound) {
        this.besSolutionFound = besSolutionFound;
    }

    
    
    public Duration getExecutionTime(){
       String[] execArgs= this.getElapsedTime_text().getText().split(":");
       
       return new Duration(Integer.parseInt(execArgs[0])*60*1000+Integer.parseInt(execArgs[0])*1000+Integer.parseInt(execArgs[0]));
       
    }
    
}
