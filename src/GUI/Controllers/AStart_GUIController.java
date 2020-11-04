
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
import javafx.event.Event;
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
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
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
public class AStart_GUIController implements Initializable,ExecutionController {

    @FXML
    private BarChart<String,Number> barChart;
    @FXML
    private AnchorPane executionContainer;
    @FXML
    private Text execTime_text;
    
    @FXML
    private Button graphBtn;
//#############################################################################################################################################

    @FXML
    private Text bestSol_text;
 
   
    @FXML
    private Text solutionFitness;
//##############################################################################################################################
   

    public static Stage stage;
    public static HashMap<String,Object> PARAMS;
    
    
    
    public static Execution_GUIController executionController;
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        FXMLLoader fxml2=new FXMLLoader(getClass().getResource("/GUI/Views/Blind_Execution_GUI.fxml"));
        try
        {
            graphBtn.setDisable(true);
            Object root = fxml2.load();
            executionContainer.getChildren().add((Parent)root);
            executionController=fxml2.getController();
            Blind_Execution_GUIController.execController=this;   
        }
        catch (IOException ex)
        {
            Logger.getLogger(GA_GUIController.class.getName()).log(Level.SEVERE, null, ex);
        }
        initDefaultParams();       
    }  
    
    
    @Override
    public void launch() {
        
        executionController.getStatus_text().setText("Running");
        try {

            /**
             * Décommenter les lignes commentés 'Massive Combination Execution'  pour exécution Massives des différents valuers des paramètres
             */
            PrintWriter writer=new PrintWriter(new File("resultsAStart.csv"));
            StringBuilder sBuilder=new StringBuilder();
            sBuilder.append("Max depth,Best fitness \n");
            writer.append(sBuilder.toString()+"\n");
            writer.flush();
                //Récupération des paramètres depuis Gui des settings
                int maxDepth=(int)PARAMS.get("maxDepth");
                
                
                    double bestFitness=0;
                    sBuilder=new StringBuilder();
                    sBuilder.append("").append(maxDepth);
                    
                    //Préparer la liste des composants graphiques à passer:
                    HashMap<String,Text> GUIComponentsMap=new  HashMap<>();
                    GUIComponentsMap.put("currentSolution",((Blind_Execution_GUIController)executionController).getCurrentSolution());
                    GUIComponentsMap.put("depth",((Blind_Execution_GUIController)executionController).getDepth());
                    GUIComponentsMap.put("solution",bestSol_text);
                    GUIComponentsMap.put("solutionFitness",solutionFitness);
                    GUIComponentsMap.put("nbrVisitedNodes",((Blind_Execution_GUIController)executionController).getNbrVisitedNodes());
                    

                    
                      // Lancer l'exécution de l'algorithme
                    double res=satproblem.SATPROBLEM.Astartearch(GUIComponentsMap, maxDepth);
                    if(res==0) GUIComponentsMap.get("solution").setText("Pas de solution trouvée avec cette profondeur");
                    if(res==-1)GUIComponentsMap.get("solution").setText("Cette instance n'a pas de solution");
                    if(res>bestFitness) 
                        bestFitness=res;
                    
                    System.err.println("\n \n \t \t \t "+res+"\n \n ");
                    sBuilder.append(","+res);
                    writer.append(sBuilder.toString()+"\n");
                    writer.flush();
                    HashMap<Integer,Double> resultsMap=new HashMap<>();
                    resultsMap.put(maxDepth, bestFitness);
                    Graph_GUIController.resultsMap =resultsMap;
                    Graph_GUIController.xAxisLabel="maximum depth";
                //}
                
                
               
          Platform.runLater(() -> {
              graphBtn.setDisable(false);
              //executionController.stopRunningThread();
              executionController.getStatus_text().setText("Finished");
              executionController.getStatus_text().setFill(Paint.valueOf("#36af26"));
              executionController.stopRunningThread();
                   });           
           
        } catch (IOException ex) {
            //executionController.getStatus_text().setFill(Paint.valueOf("#d01e1e"));
            //executionController.getStatus_text().setText("Stopped ! An Error has occured");
            Logger.getLogger(SATPROBLEM.class.getName()).log(Level.SEVERE, null, ex);
            //System.out.println("Erreur de l'execution de l'AG"+ex.getMessage());
            //executionController.stopRunningThread();
        } catch (InstantiationException ex) {
            Logger.getLogger(AStart_GUIController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(AStart_GUIController.class.getName()).log(Level.SEVERE, null, ex);
        }       
    }

    @Override
    public void showResults(Execution_GUIController resultsController) {
        execTime_text.setText(resultsController.getElapsedTime_text().getText());
    }
    
    @FXML
    void showExecutionDetails(ActionEvent event) {

    }
    
    
    @FXML
    void openAStartSettingsGUI(ActionEvent event) throws IOException {
        
        FXMLLoader loader=new FXMLLoader(getClass().getResource("/GUI/Views/AStart_Settings_GUI.fxml"));
        Parent fxml=(Parent) loader.load();        
        Scene scene=new Scene(fxml);      
        Stage stg=new Stage();
        stg.initOwner(stage);
        stg.setScene(scene);
        stg.initStyle(StageStyle.UNDECORATED);
        AStart_Settings_GUIController.stage=stg;
        AStart_Settings_GUIController.stage.show();
        
    }
    
    @FXML
    void closeGAGUI(ActionEvent event)
    {
        Node node=(Node) event.getSource();
        Stage stage=(Stage) node.getScene().getWindow();
        
        stage.close();
    }
    
    @FXML
    void goBack(Event event ) throws IOException
    {
        Parent fxml=FXMLLoader.load(getClass().getResource("/GUI/Views/Blind_Search_GUI.fxml"));
        Scene scene=new Scene(fxml);
        Blind_Search_GUIController.stage.setScene(scene);
        stage.close();
        Blind_Search_GUIController.stage.show();
    }
    
     @FXML
    void showGraph(ActionEvent event ) throws IOException
    {
        Parent fxml=FXMLLoader.load(getClass().getResource("/GUI/Views/Graph_GUI.fxml"));
        Scene scene=new Scene(fxml);
        Stage stg = new Stage();
        stg.setScene(scene);
        stg.show();
    }
    
    /**
     * Méthode qui initialise les paramètres de l'algorithme, par des valeurs par défauts
     */
    private void initDefaultParams() {
        PARAMS=new HashMap<>();
        AStart_GUIController.PARAMS.put("maxDepth",20);
        AStart_GUIController.PARAMS.put("CNFFileName", inputFile);
        AStart_GUIController.PARAMS.put("CNFFilePath", inputFile.substring(inputFile.lastIndexOf("\\")+1));
    }
    
    
    
    
    
                // Getters & Setters 

    public static Stage getStage() {
        return stage;
    }

    public static void setStage(Stage stage) {
        GA_GUIController.stage = stage;
    }

    

    
    
    
    
}
