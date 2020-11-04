
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
import javafx.scene.control.Button;
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
public class PSO_GUIController implements Initializable,ExecutionController {

    @FXML
    private BarChart<String,Number> barChart;
    @FXML
    private AnchorPane executionContainer;
    
    @FXML
    private Text execTime_text;

    @FXML
    private Text bestSol_text;
    
    @FXML
    private Text bestSol_rate;
    
            //*****//
    @FXML
    private Text nbrParticlesText;

    @FXML
    private Text maxItText;

    @FXML
    private Text benchmarkText;
    
    

    @FXML
    private Text rate_text;

    @FXML
    private Text rate_text1;

    @FXML
    private Text rate_text2;

    @FXML
    private Text rate_text3;
    
    @FXML
    private Button  graphBtn;
    
    public static Stage stage;
    public static HashMap<String,Object> PARAMS;
    public static PSO_GUIController CURRENT_INSTANCE;
    
    
    public static PSO_Execution_GUIController executionController;
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {   CURRENT_INSTANCE=this;
        FXMLLoader fxml2=new FXMLLoader(getClass().getResource("/GUI/Views/PSO_Execution_GUI.fxml"));
        try
        {
            graphBtn.setDisable(true);
            Object root = fxml2.load();
            executionContainer.getChildren().add((Parent)root);
            executionController=fxml2.getController();
            PSO_Execution_GUIController.execController=this;   
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
            PrintWriter writer=new PrintWriter(new File("resultsPso.csv"));
            StringBuilder sBuilder=new StringBuilder();
            sBuilder.append("Number of particles,Max iteration,Best fitness \n");
            writer.append(sBuilder.toString()+"\n");
            writer.flush();
            
            HashMap<Integer,Double> resultsMap=new HashMap<>();
            
            
                //Récupération des paramètres depuis Gui des settings
                int particlesNumber=(int)PARAMS.get("particlesNumberVal"),
                    maxIter=(int)PARAMS.get("maxIter");
                
                
                    double bestFitness=0;
                    sBuilder=new StringBuilder();
                    sBuilder.append("").append(particlesNumber).append(",").append(maxIter);
                    
                    //Préparer la liste des composants graphique à passer:
                    HashMap<String,Text> GUIComponentsMap=new  HashMap<>();
                    GUIComponentsMap.put("currentIteration",executionController.getIterationNbr_text());
                    GUIComponentsMap.put("currentStep",executionController.getCurrectSol_text());
                    GUIComponentsMap.put("bestSolFound",bestSol_text); 
                    GUIComponentsMap.put("gBest",executionController.getGBest()); 
                    GUIComponentsMap.put("optimalSolutionEvaluation",bestSol_rate);
                    
                      // Lancer l'exécution de l'algorithme
                    double res=satproblem.SATPROBLEM.PSOptimisation(GUIComponentsMap, maxIter, particlesNumber);
                    
                    if(res>bestFitness) 
                        bestFitness=res;
                    
                    System.err.println("\n \n \t \t \t "+res+"\n \n ");
                    sBuilder.append(","+res);
                    writer.append(sBuilder.toString()+"\n");
                    writer.flush();                    
                    resultsMap.put(maxIter, bestFitness);
                    Graph_GUIController.resultsMap=resultsMap;
                    Graph_GUIController.xAxisLabel="Maximum iteartions";
                //}
                
                
                    
                } catch (IOException ex) {
            Logger.getLogger(PSO_GUIController.class.getName()).log(Level.SEVERE, null, ex);
        }

            //}
          Platform.runLater(() -> {
              graphBtn.setDisable(false);
              //executionController.stopRunningThread();
              executionController.getStatus_text().setText("Finished");
              executionController.getStatus_text().setFill(Paint.valueOf("#36af26"));
              executionController.stopRunningThread();
                   });           
           
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

    @Override
    public void showResults(Execution_GUIController resultsController) {
        execTime_text.setText(resultsController.getElapsedTime_text().getText());
    }
    
    @FXML
    void showExecutionDetails(ActionEvent event) {

    }
    
    
    @FXML
    void openPSOSettingsGUI(ActionEvent event) throws IOException {

        
        FXMLLoader loader=new FXMLLoader(getClass().getResource("/GUI/Views/PSO_Settings_GUI.fxml"));
        Parent fxml=(Parent) loader.load();
        /*dialog.initOwner(stage.getScene().getWindow());
        dialog.getDialogPane().getChildren().add(fxml);
        
        dialog.show();*/
        PSO_Settings_GUIController controller=(PSO_Settings_GUIController)loader.getController();
        
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
    void resetParametres(ActionEvent event ) throws IOException
    {
        PSO_GUIController.stage.close();
        Parent fxml=FXMLLoader.load(getClass().getResource("/GUI/Views/PSO_GUI.fxml"));
        Scene scene=new Scene(fxml);
        PSO_GUIController.stage.setScene(scene);
        PSO_GUIController.stage.show();
    }
    
    
    
    
    
    /**
     * Méthode qui initialise les paramètres de l'algorithme, par des valeurs par défauts
     */
    private void initDefaultParams() {
        PARAMS=new HashMap<String,Object>();
        PSO_GUIController.PARAMS.put("particlesNumberVal", 100);
        PSO_GUIController.PARAMS.put("maxIter", 1000);
        PSO_GUIController.PARAMS.put("CNFFileName", inputFile);
        PSO_GUIController.PARAMS.put("CNFFilePath", inputFile.substring(inputFile.lastIndexOf("\\")+1));
        
    }
    
    /**
     * Méthode qui afficher les paramètres actuelle sur GUI
     */
    public void displaySettings(){
        nbrParticlesText.setText((int)PARAMS.get("particlesNumberVal")+"");
        maxItText.setText((int)PARAMS.get("maxIter")+"");
        benchmarkText.setText(PARAMS.get("CNFFileName").toString());

    }
    
    
    
    
    
    
                // Getters & Setters 

    public static Stage getStage() {
        return stage;
    }

    public static void setStage(Stage stage) {
        GA_GUIController.stage = stage;
    }

    public BarChart<String, Number> getBarChart() {
        return barChart;
    }

    public void setBarChart(BarChart<String, Number> barChart) {
        this.barChart = barChart;
    }

    public AnchorPane getExecutionContainer() {
        return executionContainer;
    }

    public void setExecutionContainer(AnchorPane executionContainer) {
        this.executionContainer = executionContainer;
    }

    public Text getExecTime_text() {
        return execTime_text;
    }

    public void setExecTime_text(Text execTime_text) {
        this.execTime_text = execTime_text;
    }

    public Text getBestSol_text() {
        return bestSol_text;
    }

    public void setBestSol_text(Text bestSol_text) {
        this.bestSol_text = bestSol_text;
    }

    public Text getBestSol_rate() {
        return bestSol_rate;
    }

    public void setBestSol_rate(Text bestSol_rate) {
        this.bestSol_rate = bestSol_rate;
    }

    public Text getNbrParticlesText() {
        return nbrParticlesText;
    }

    public void setNbrParticlesText(Text nbrParticlesText) {
        this.nbrParticlesText = nbrParticlesText;
    }

    public Text getMaxItText() {
        return maxItText;
    }

    public void setMaxItText(Text maxItText) {
        this.maxItText = maxItText;
    }

    public Text getBenchmarkText() {
        return benchmarkText;
    }

    public void setBenchmarkText(Text benchmarkText) {
        this.benchmarkText = benchmarkText;
    }

    public Button getGraphBtn() {
        return graphBtn;
    }

    public void setGraphBtn(Button graphBtn) {
        this.graphBtn = graphBtn;
    }

    public static HashMap<String, Object> getPARAMS() {
        return PARAMS;
    }

    public static void setPARAMS(HashMap<String, Object> PARAMS) {
        PSO_GUIController.PARAMS = PARAMS;
    }

    public static Execution_GUIController getExecutionController() {
        return executionController;
    }

    public static void setExecutionController(PSO_Execution_GUIController executionController) {
        PSO_GUIController.executionController = executionController;
    }

    
    
    

    
    
    
    
}
