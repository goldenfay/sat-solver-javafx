
package GUI.Controllers;


import animatefx.animation.FadeIn;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Reflection;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;


/**
 * FXML Controller class
 *
 * @author PC
 */
public class MainInterfaceController implements Initializable {

    public static Stage stage;
    @FXML
    private HBox blind_hbox;

    @FXML
    private HBox swarm_hbox;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        FadeIn fadeIn=new FadeIn(swarm_hbox.getParent());
        fadeIn.setSpeed(0.3);
        fadeIn.play();
       
                  
    }
    
    @FXML
    public void HoverEffect(MouseEvent event) {

        HBox hb=(HBox) event.getSource();
        Reflection ref=new Reflection();
        ref.setFraction(0.73);
        ref.setTopOffset(0.5);
        ref.setTopOpacity(0.1);
        ref.setBottomOpacity(0.06);
        
        DropShadow drpsh=new DropShadow();
        drpsh.setOffsetX(5);
        drpsh.setOffsetY(5);
        drpsh.setColor(Color.rgb(76, 118, 186, 0.7));
        ref.setInput(drpsh);
        hb.setEffect(ref);
        hb.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                ((HBox)event.getSource() ).setEffect(null);
            }
        });
        
        
    }
    
    
    
    @FXML
    void OpenSwarmSearchGUI(MouseEvent event) throws IOException
    {
        stage.setScene( new Scene((Parent) FXMLLoader.load(getClass().getResource("/GUI/Views/MetaHeuristique_GUI.fxml"))) );
        MetaHeuristique_GUIController.stage=stage;
        stage.show();       
    }

    @FXML
    void openBlinSearchGUI(MouseEvent event) throws IOException {
        
          
          stage.setScene( new Scene((Parent) FXMLLoader.load(getClass().getResource("/GUI/Views/Blind_Search_GUI.fxml"))) );
          Blind_Search_GUIController.stage=stage;
          stage.show();
    }

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
