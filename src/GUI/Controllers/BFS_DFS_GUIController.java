
package GUI.Controllers;


import static GUI.Controllers.AStart_GUIController.executionController;
import static GUI.Controllers.GA_GUIController.PARAMS;
import static GUI.Controllers.GA_GUIController.executionController;
import static GUI.Controllers.MetaHeuristique_GUIController.stage;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import satproblem.ExecutionController;
import satproblem.SATPROBLEM;
import static satproblem.SATPROBLEM.inputFile;
import satproblem.ToggleSwitch;



/**
 * FXML Controller class
 *
 * @author PC
 */
public class BFS_DFS_GUIController implements Initializable , ExecutionController{

    
    public static Stage stage;
    @FXML
    private HBox hb;

    @FXML
    private Label file_name_label;

    @FXML
    private Label nbr_clauses_label;

    @FXML
    private Label nbr_variables_label;


    @FXML
    private Button launch_btn;
/*****************************************/
    @FXML
    private Label current_sol_label;

   
    @FXML
    private Label nbr_nodeVisited_label;
            
    @FXML
    private Label current_depth;
//#############################################################################################################################################

    @FXML
    private Text bestSol_text;
 
   
    @FXML
    private Text solutionFitness;
    
    @FXML
    private Text finalDepth;
    
    
//##############################################################################################################################
    @FXML
    private Label time_label;
    
    @FXML
    private Text execTime_text;
    
    
    
    @FXML
    private AnchorPane rootContainer;
    
    @FXML
    private AnchorPane graphsContainer;
    
    @FXML
    private AnchorPane executionContainer;
    
    @FXML
    public static  Execution_GUIController executionController;
    
    
    public static HashMap<String,Object> PARAMS;
    

    private ToggleSwitch methodeSwitch;
    /**
     * Initializes the controller class.
     */
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    { 
        initDefaultParams();
        FXMLLoader fxml2=new FXMLLoader(getClass().getResource("/GUI/Views/Blind_Execution_GUI.fxml"));
        try
        {
            Object root = fxml2.load();
            executionContainer.getChildren().add((Parent)root);
            executionController=fxml2.getController();
            Blind_Execution_GUIController.execController=this;   
        }
        catch (IOException ex)
        {
            Logger.getLogger(GA_GUIController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        rootContainer.setBackground(new Background(new BackgroundFill(Color.YELLOW, CornerRadii.EMPTY, Insets.EMPTY)));
        graphsContainer.setBackground(new Background(new BackgroundFill(Color.BLUE, CornerRadii.EMPTY, Insets.EMPTY)));
        methodeSwitch=new ToggleSwitch();
        methodeSwitch.setLabel("BFS");
        methodeSwitch.getStyleClass().add("GUITS");
        hb.getChildren().add(methodeSwitch);
        
        methodeSwitch.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent event) -> {
            methodeSwitch.setLabel("DFS");
        });
        
        int nbrInLine,level=1,nbr=0; double Xorigin=rootContainer.getLayoutX(),Yorigin=rootContainer.getLayoutY()+(3*rootContainer.getHeight()/2);
        HBox line=new HBox();
       
        for(int i=1;i<16;i++)
        { 
            nbrInLine=(int) Math.pow(2, level);            
            if(nbr<nbrInLine){
                Circle circle=new Circle(25);
                circle.setFill(Color.LIGHTGREEN);
                Xorigin=(Xorigin+(rootContainer.getPrefWidth()/nbrInLine/2)  );
                circle.setCenterX(Xorigin);
                circle.setCenterY(Yorigin);
               // nodeContainer.getChildren().add(circle);
                graphsContainer.getChildren().add(circle);
                nbr++;
                
            }
            else { level++; nbr=0;
                Yorigin=Yorigin+rootContainer.getPrefHeight();
                    Xorigin=rootContainer.getLayoutX();
             
            }
        }
        
        System.out.println(graphsContainer.getChildren().size());
        graphsContainer.setVisible(false);
    }   
    
    
    
    
    @FXML
    void openSettingsGUI(MouseEvent event) throws IOException 
    {
        Parent fxml=FXMLLoader.load(getClass().getResource("/GUI/Views/BFS_DFS_Settings.fxml"));
        Scene scene=new Scene(fxml);
        Stage stg = new Stage();
        stg.setScene(scene);
        BFS_DFS_SettingsController.stage= stg;
        BFS_DFS_SettingsController.stage.show();
    }
    
    @FXML
    void goBack(ActionEvent event ) throws IOException
    {
        Parent fxml=FXMLLoader.load(getClass().getResource("/GUI/Views/Blind_Search_GUI.fxml"));
        stage.close();
        Stage stg = new Stage();
        Scene scene=new Scene(fxml);
        stg.setScene(scene);
        Blind_Search_GUIController.stage=stg;
        MainInterfaceController.stage.show();
    }

    @Override
    public void launch() 
    {
        executionController.getStatus_text().setText("Running");
       
        //Préparer la liste des composants graphique à passer:
        HashMap<String,Text> GUIComponentsMap=new  HashMap<>();
        GUIComponentsMap.put("currentSolution",((Blind_Execution_GUIController)executionController).getCurrentSolution());
        GUIComponentsMap.put("depth",((Blind_Execution_GUIController)executionController).getDepth());
        GUIComponentsMap.put("finalDepth",finalDepth);
        GUIComponentsMap.put("solution",bestSol_text);
        GUIComponentsMap.put("solutionFitness",solutionFitness);
        GUIComponentsMap.put("nbrVisitedNodes",((Blind_Execution_GUIController)executionController).getNbrVisitedNodes());
        
        int maxDepth = (int)BFS_DFS_GUIController.PARAMS.get("maxDepth");
        // Lancer l'exécution de l'algorithme
        System.out.println("La methode choisie est ====> "+this.methodeSwitch.getLabel().getText());
        if((this.methodeSwitch.getLabel().getText()).compareTo("BFS")==0)
        {
            try {
                satproblem.SATPROBLEM.BreathTransverseSearche(GUIComponentsMap,maxDepth);
                this.showResults(executionController);
            } catch (IOException ex) {
                Logger.getLogger(BFS_DFS_GUIController.class.getName()).log(Level.SEVERE, null, ex);
            }
          
        }else
        {
            
            try {
                satproblem.SATPROBLEM.DepthTransverseSearch(GUIComponentsMap,maxDepth);
            } catch (IOException ex) {
                Logger.getLogger(BFS_DFS_GUIController.class.getName()).log(Level.SEVERE, null, ex);
            }
           
        }
        /*if(res>bestFitness)
        bestFitness=res;
        
        System.err.println("\n \n \t \t \t "+res+"\n \n ");
        sBuilder.append(","+res);
        writer.append(sBuilder.toString()+"\n");
        writer.flush();
        
        resultsMap.put(maxit, bestFitness);
        //}
        
        
        XYChart.Series dataSerie= new XYChart.Series<>();
        //dataSerie.setName("Instanc1+i");
        int i=0;
        resultsMap.forEach((Integer t, Double u) -> {
        dataSerie.getData().add(new XYChart.Data<>(String.valueOf(t), (Number)u));
        
        Platform.runLater(() -> {
        bChart.getData().add(dataSerie);
        });
        
        });
        //}*/
        Platform.runLater(() -> {
            //executionController.stopRunningThread();
            executionController.getStatus_text().setText("Finished");       
            executionController.getStatus_text().setFill(Paint.valueOf("#36af26"));
            executionController.stopRunningThread();
        });
    }

    @Override
    public void showResults(Execution_GUIController resultsController) {
        execTime_text.setText(resultsController.getElapsedTime_text().getText());
    }
    
   
     private void initDefaultParams() {
        PARAMS=new HashMap<>();
        PARAMS.put("maxDepth",10);
        PARAMS.put("CNFFileName", inputFile);
        PARAMS.put("CNFFilePath", inputFile.substring(inputFile.lastIndexOf("\\")+1));
    }
    
    
    
}
