package GUI.Controllers;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import satproblem.SATPROBLEM;

/**
 * FXML Controller class
 *
 * @author PC
 */
public class BFS_DFS_SettingsController implements Initializable {
    @FXML
    Slider maxDepthSlider;
    
    @FXML
    Label maxDepthVal;
    
    
    @FXML
    private AnchorPane main_container;
    
    @FXML
    private FontAwesomeIcon close_settings_windowBTN;
    
    @FXML
    private HBox filename_hbox;
    
    private String benchmarkFilePath=SATPROBLEM.inputFile;
    
    
    
    private Desktop desktop = Desktop.getDesktop();
    static Stage stage;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        maxDepthSlider.setShowTickLabels(true);
        initSliders();
        
        FileChooser fileCh=new FileChooser();
        fileCh.getExtensionFilters().add(new FileChooser.ExtensionFilter("CNF files (*.cnf)", "*.cnf"));
        
        Button uploadBTN=new Button("Choose the .cnf file");
        uploadBTN.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));
        uploadBTN.getStyleClass().add("upload_BTN");
               
        uploadBTN.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                File file = fileCh.showOpenDialog(stage);
                    if (file != null) {
                        benchmarkFilePath=file.getAbsolutePath();
                        openFile(file);
                    }
            }
        });
        filename_hbox.getChildren().add(uploadBTN);
    }    
    
    
    @FXML
    void closeSettingsWindow(MouseEvent event){
        
        
        Node node=(Node) event.getSource();
        Stage stage=(Stage) node.getScene().getWindow();
        
        stage.close();
    }
    
    @FXML
    void saveSettings(ActionEvent event)
    { 
        System.out.print("##########@");
        BFS_DFS_GUIController.PARAMS.put("maxDepth", (int)maxDepthSlider.getValue());
        System.out.print("##########@");
        BFS_DFS_GUIController.PARAMS.put("CNFFileName", benchmarkFilePath.substring(benchmarkFilePath.lastIndexOf("\\")+1));
        System.out.print("##########@");
        BFS_DFS_GUIController.PARAMS.put("CNFFilePath", benchmarkFilePath);        
        System.out.print("##########@");
        SATPROBLEM.inputFile=benchmarkFilePath;        
        System.out.print("##########@");
        dismissWindow(event);
    }
    
    @FXML
    void dismissWindow(ActionEvent event)
    {
        stage.close();
    }
    
    private void openFile(File file) {
        try {
            desktop.open(file);
        } catch (IOException ex) {
            
        }
    }
    
     private void initSliders() 
     {
        maxDepthSlider.setMax(10000);
        maxDepthSlider.setMin(100);
        maxDepthSlider.setBlockIncrement(100);
        maxDepthSlider.valueProperty().addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
            maxDepthVal.setText((int)maxDepthSlider.getValue()+"");
        });
     }
    
    
    
    
    
    
                                // Getters & Setters 

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
    
    
    
    
}
