
package GUI.Controllers;

import animatefx.animation.FadeOut;
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
public class PSO_Settings_GUIController implements Initializable {

    
    @FXML
    private Slider particlesNumberSlider;

    @FXML
    private Label particlesNumberVal;

    @FXML
    private Slider maxIterSlider;

    @FXML
    private Label maxitSliderVal;

   
    @FXML
    private Label cnfFileLabel;

    @FXML
    private HBox uploadBtnContainer;

    @FXML
    private Button confirmBtn;

    @FXML
    private Button cancelBtn;

    
    private FileChooser fileCh;
    private String benchmarkFilePath=SATPROBLEM.inputFile;
    private Stage stage;
    
    private Desktop desktop = Desktop.getDesktop();
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        particlesNumberSlider.setShowTickLabels(true);
        
        initSliders();
        
            // Initialiser le boutton de 'Browse' pour choisir le fichier CNF
        fileCh=new FileChooser();
        fileCh.getExtensionFilters().add(new FileChooser.ExtensionFilter("CNF files (*.cnf)", "*.cnf"));
        
        AnchorPane parent=(AnchorPane) cnfFileLabel.getParent() ;
        Button uploadBTN=new Button("Choose the .cnf file");
        uploadBTN.getStyleClass().add("upload_btn");
        //uploadBTN.setLayoutX(cnfFileLabel.getLayoutX()+cnfFileLabel.getMaxWidth()+10);
        //uploadBTN.setLayoutY(cnfFileLabel.getLayoutY());
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
        
         // Ajouter le boutton à sa position 
        (uploadBtnContainer).getChildren().add(uploadBTN);
    }    
    
    
    @FXML
    void dismissWindow(ActionEvent event) {

        Node container=(Node)( (Node) event.getSource()).getParent();
        FadeOut fadeOut=new FadeOut(container);
        fadeOut.setResetOnFinished(false);
        
        /*fadeOut.setTimeline(new Timeline(new KeyFrame(Duration.millis(10), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                container.setOpacity(container.opacityProperty().doubleValue()-0.01);
                //if(container.opacityProperty().doubleValue()<=0.1)stage.close();
            }
        })));
*/
        
        fadeOut.play();
        fadeOut.getTimeline().setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
               stage.close();
            }
        });
    }
    
    
    @FXML
    void saveSettings(ActionEvent event) {

        PSO_GUIController.PARAMS.put("particlesNumberVal", (int)particlesNumberSlider.getValue());
        PSO_GUIController.PARAMS.put("maxIter", (int)maxIterSlider.getValue());
        PSO_GUIController.PARAMS.put("CNFFileName", benchmarkFilePath.substring(benchmarkFilePath.lastIndexOf("\\")+1));
        PSO_GUIController.PARAMS.put("CNFFilePath", benchmarkFilePath);
        SATPROBLEM.inputFile=benchmarkFilePath;
        PSO_GUIController.CURRENT_INSTANCE.displaySettings();
        dismissWindow(event);
        
    }
  
    /**
     * Méthoe pour ouvrir la fenêtre standard de Windows/Mac/Linux pour choisir un fichier
     * @param file 
     */
    private void openFile(File file) {
        try {
            desktop.open(file);
        } catch (IOException ex) {
            
        }
    }
        
    private void initSliders() {
        particlesNumberSlider.setMax(10000);
        particlesNumberSlider.setMin(100);
        particlesNumberSlider.setBlockIncrement(100);
        particlesNumberSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                particlesNumberVal.setText((int)particlesNumberSlider.getValue()+"");
            }
        });
        particlesNumberSlider.setValue((int)PSO_GUIController.PARAMS.get("particlesNumberVal"));
        
        maxIterSlider.setMax(1000000);
        maxIterSlider.setMin(1000);
        maxIterSlider.setBlockIncrement(1000);
        maxIterSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                maxitSliderVal.setText((int)maxIterSlider.getValue()+"");
            }
        });     
        
        maxIterSlider.setValue((int)PSO_GUIController.PARAMS.get("maxIter"));
    }
    
    
                    // Getters & Setters 

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    
    
}
