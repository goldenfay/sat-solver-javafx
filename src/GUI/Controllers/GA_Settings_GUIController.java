
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
public class GA_Settings_GUIController implements Initializable {

      @FXML
    private Slider populationSizeSlider;

    @FXML
    private Label popsizeSliderVal;

    @FXML
    private Slider maxIterSlider;

    @FXML
    private Label maxitSliderVal;

    @FXML
    private Slider crossOverRateSlider;

    @FXML
    private Label crossoverSliderVal;

    @FXML
    private Slider mutationRateSlider;

    @FXML
    private Label mutationSliderVal;

    @FXML
    private Slider nbrTestSlide;

    @FXML
    private Label nbrOfTests;

    @FXML
    private Label cnfFileLabel;

    @FXML
    private HBox uploadBtnContainer;

    @FXML
    private Button confirmBtn;

    
    private FileChooser fileCh;
    private String benchmarkFilePath=SATPROBLEM.inputFile;
    private Stage stage;
    
    private Desktop desktop = Desktop.getDesktop();
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        populationSizeSlider.setShowTickLabels(true);
        
        initSliders();
        
            // Initialiser le boutton de 'Browse' pour choisir le fichier CNF
        fileCh=new FileChooser();
        fileCh.getExtensionFilters().add(new FileChooser.ExtensionFilter("CNF files (*.cnf)", "*.cnf"));
        
        AnchorPane parent=(AnchorPane) cnfFileLabel.getParent() ;
        Button uploadBTN=new Button("Choose the .cnf file");
        uploadBTN.getStyleClass().add("upload_btn");
       
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

        GA_GUIController.PARAMS.put("PopulationSize", (int)populationSizeSlider.getValue());
        //GA_GUIController.PARAMS.put("nbrOfTests", (int)populationSizeSlider.getValue());
        GA_GUIController.PARAMS.put("MaxIteration", (int)maxIterSlider.getValue());
        GA_GUIController.PARAMS.put("CrossOverRate",(int) crossOverRateSlider.getValue());
        GA_GUIController.PARAMS.put("MutationRate", (int)mutationRateSlider.getValue());
        GA_GUIController.PARAMS.put("nbrOfExecution", (int)nbrTestSlide.getValue());
        GA_GUIController.PARAMS.put("CNFFileName", benchmarkFilePath.substring(benchmarkFilePath.lastIndexOf("\\")+1));
        GA_GUIController.PARAMS.put("CNFFilePath", benchmarkFilePath);
        SATPROBLEM.inputFile=benchmarkFilePath;
        GA_GUIController.CURRENT_INSTANCE.displaySettings();
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
        populationSizeSlider.setMax(10000);
        populationSizeSlider.setMin(100);
        populationSizeSlider.setBlockIncrement(100);
        populationSizeSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                popsizeSliderVal.setText((int)populationSizeSlider.getValue()+"");
            }
        });
        
        maxIterSlider.setMax(1000000);
        maxIterSlider.setMin(1000);
        maxIterSlider.setBlockIncrement(1000);
        maxIterSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                maxitSliderVal.setText((int)maxIterSlider.getValue()+"");
            }
        });
        
        
        mutationRateSlider.setMax(70);
        mutationRateSlider.setMin(0);
        mutationRateSlider.setBlockIncrement(1);
        mutationRateSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                mutationSliderVal.setText((int)mutationRateSlider.getValue()+"");
            }
        });
        
        crossOverRateSlider.setMax(100);
        crossOverRateSlider.setMin(0);
        crossOverRateSlider.setBlockIncrement(1);
        crossOverRateSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                crossoverSliderVal.setText((int)crossOverRateSlider.getValue()+"");
            }
        });
        
        nbrTestSlide.setMax(15);
        nbrTestSlide.setMin(1);
        nbrTestSlide.setBlockIncrement(1);
        nbrTestSlide.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
               nbrOfTests.setText((int)crossOverRateSlider.getValue()+"");
            }
        });
        nbrTestSlide.setValue(1);
        
    }
    
    
                    // Getters & Setters 

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    
    
}
