
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
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
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
public class GA_GUIController implements Initializable,ExecutionController {

 
    @FXML
    private Button graphBtn;
    @FXML
    private AnchorPane executionContainer;
    
    @FXML
    private Text execTime_text;
    
    @FXML
    private Text bestSol_text;
    
    @FXML
    private Text bestSol_rate;
    
    @FXML
    private Text population_size;
    
    @FXML
    private Text maximum_iterations;
    
    @FXML
    private Text nbrOfIndividuals;
    
    
    @FXML
    private Text mutation_rate;
    
    @FXML
    private Text crossover_rate;

    @FXML
    private Text rate_text;

    @FXML
    private Text rate_text1;

    @FXML
    private Text rate_text2;

    @FXML
    private  Text rate_text3;
    
            //*******//
    @FXML
    private Text maxItText;

    @FXML
    private Text popsizeText;

    @FXML
    private Text crossOverText;

    @FXML
    private Text mutationText;

    @FXML
    private Text benchmarkText;
  
    
    public static Stage stage;
    public static HashMap<String,Object> PARAMS;
    public static GA_GUIController CURRENT_INSTANCE;
    
    
    
    public static Execution_GUIController executionController;
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {   CURRENT_INSTANCE=this;
        FXMLLoader fxml2=new FXMLLoader(getClass().getResource("/GUI/Views/Execution_GUI.fxml"));
        try
        {
           Object root = fxml2.load();
           graphBtn.setDisable(true);
            executionContainer.getChildren().add((Parent)root);
            executionController=fxml2.getController();
            Execution_GUIController.execController=this;   
        }
        catch (IOException ex)
        {
            Logger.getLogger(GA_GUIController.class.getName()).log(Level.SEVERE, null, ex);
        }
        initDefaultParams();  
        
        displaySettings();
    }  
    
    
    @Override
    public void launch() {
        
        executionController.getStatus_text().setText("Running");
        try {

            /**
             * Décommenter les lignes commentés 'Massive Combination Execution'  pour exécution Massives des différents valuers des paramètres
             */
            PrintWriter writer=new PrintWriter(new File("GATestresults.csv"));
            StringBuilder sBuilder=new StringBuilder();
            sBuilder.append("Population size,Max iteration,CrossOver rate,Mutation Rate,Best fitness \n");
            writer.append(sBuilder.toString()+"\n");
            writer.flush();
            //if(true) throw new IOException();
            HashMap<Integer,Double> resultsMap=new HashMap<>();
            
            
            
                //Récupération des paramètres depuis Gui des settings
                int popsize=(int)PARAMS.get("PopulationSize"),
                    maxit=(int)PARAMS.get("MaxIteration");
                int CR=(int)PARAMS.get("CrossOverRate"),
                    MR=(int)PARAMS.get("MutationRate");
                int nbrExecution=(int)PARAMS.get("MutationRate");;
                
              
            for(int cpt=0;cpt<nbrExecution;cpt++){  // Massive Combination Execution
              //  for(int maxit=500;maxit<5000;maxit+=500){  //Massive Combination Execution
                    double bestFitness=0;
                    sBuilder=new StringBuilder();
                    sBuilder.append(""+popsize+","+maxit+","+CR+","+MR);
                      //Préparer la liste des composants graphique à passer:
                    HashMap<String,Text> GUIComponentsMap=new  HashMap<>();
                    GUIComponentsMap.put("nbrOfIndividuals",nbrOfIndividuals);
                    GUIComponentsMap.put("currentIteration",executionController.getIterationNbr_text());
                    GUIComponentsMap.put("currentStep",executionController.getCurrentStep());
                    GUIComponentsMap.put("bestSolFound",bestSol_text); 
                    GUIComponentsMap.put("optimalSolutionEvaluation",bestSol_rate);
                   
                      // Lancer l'exécution de l'algorithme
                    double res=satproblem.SATPROBLEM.geneticAlgorithm(GUIComponentsMap, maxit, popsize, CR,MR );
                    
                    if(res>bestFitness) 
                    {
                        bestFitness=res;
                    }
                        
                    
                    System.err.println("\n \n \t \t \t "+res+"\n \n ");
                    sBuilder.append(","+res);
                    writer.append(sBuilder.toString()+"\n");
                    writer.flush();
                    resultsMap.put(maxit, bestFitness);
                //}
                
                 //PARSE THE GRAPH DATA TO THE GRAPH CONTROLLER
                 Graph_GUIController.resultsMap = resultsMap;
                 Graph_GUIController.xAxisLabel ="Maximum iterations";
                
                 
                 
                
            //}
          Platform.runLater(() -> {
              graphBtn.setDisable(false);
              //executionController.stopRunningThread();
              executionController.getStatus_text().setText("Finished");
              executionController.getStatus_text().setFill(Paint.valueOf("#36af26"));
              executionController.stopRunningThread();
                   });           
           
            }
        } 
        catch (IOException ex) 
        {
            executionController.getStatus_text().setFill(Paint.valueOf("#d01e1e"));
            executionController.getStatus_text().setText("Stopped ! An Error has occured");
            Logger.getLogger(SATPROBLEM.class.getName()).log(Level.SEVERE, null, ex);
            //System.out.println("Erreur de l'execution de l'AG"+ex.getMessage());
            executionController.stopRunningThread();
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
    void openGASettingsGUI(ActionEvent event) throws IOException {

        
        FXMLLoader loader=new FXMLLoader(getClass().getResource("/GUI/Views/GA_Settings_GUI.fxml"));
        Parent fxml=(Parent) loader.load();
        /*dialog.initOwner(stage.getScene().getWindow());
        dialog.getDialogPane().getChildren().add(fxml);
        
        dialog.show();*/
        GA_Settings_GUIController controller=(GA_Settings_GUIController)loader.getController();
        
        Scene scene=new Scene(fxml);
        
        //stg.setScene(new Scene(alert.getDialogPane()));
        Stage stg=new Stage();
        stg.initOwner(stage);
        stg.setScene(scene);
        stg.initStyle(StageStyle.UNDECORATED);
        controller.setStage(stg);
           
        stg.show();
    }
    
    @FXML
    void closeGAGUI(ActionEvent event)
    {
        Node node=(Node) event.getSource();
        Stage stage=(Stage) node.getScene().getWindow();
        
        stage.close();
    }
    
     @FXML
    void goBack(ActionEvent event ) throws IOException
    {
        Parent fxml=FXMLLoader.load(getClass().getResource("/GUI/Views/MetaHeuristique_GUI.fxml"));
        Scene scene=new Scene(fxml);
        MetaHeuristique_GUIController.stage.setScene(scene);
        stage.close();
        MetaHeuristique_GUIController.stage.show();
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
        PARAMS=new HashMap<String,Object>();
        GA_GUIController.PARAMS.put("PopulationSize", 100);
        GA_GUIController.PARAMS.put("MaxIteration", 1000);
        GA_GUIController.PARAMS.put("CrossOverRate",43);
        GA_GUIController.PARAMS.put("MutationRate", 16);
        GA_GUIController.PARAMS.put("nbrOfExecution", 2);
        GA_GUIController.PARAMS.put("CNFFileName", inputFile);
        GA_GUIController.PARAMS.put("CNFFilePath", inputFile.substring(inputFile.lastIndexOf("\\")+1));
    }
    
    
    
    
    
    /**
     * Méthode qui afficher les paramètres actuelle sur GUI
     */
    public void displaySettings(){
        popsizeText.setText((int)PARAMS.get("PopulationSize")+"");
        crossOverText.setText((int)PARAMS.get("CrossOverRate")+"");
        mutationText.setText((int)PARAMS.get("MutationRate")+"");
        maxItText.setText((int)PARAMS.get("MaxIteration")+"");
        benchmarkText.setText(PARAMS.get("CNFFileName").toString());

    }
    
    
        
    @FXML
    void resetParametres(ActionEvent event ) throws IOException
    {
        GA_GUIController.stage.close();
        Parent fxml=FXMLLoader.load(getClass().getResource("/GUI/Views/GA_GUI.fxml"));
        Scene scene=new Scene(fxml);
        GA_GUIController.stage.setScene(scene);
        GA_GUIController.stage.show();
    }
    
    
    
     // Getters & Setters 

    public static Stage getStage() {
        return stage;
    }

    public static void setStage(Stage stage) {
        GA_GUIController.stage = stage;
    }

    

    
    
    
    
}
