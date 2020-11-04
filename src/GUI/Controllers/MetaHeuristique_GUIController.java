
package GUI.Controllers;


import animatefx.animation.FadeIn;
import animatefx.animation.FadeOut;
import animatefx.animation.ZoomOut;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author PC
 */
public class MetaHeuristique_GUIController implements Initializable {

    
    public static Stage stage;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    
    
    
    @FXML
    void startBackgroundAnimation(MouseEvent event) {
        Object obj=event.getSource();
        while(!( obj instanceof Button)){
            obj=((Node)obj).getParent();
        }
        Button target=(Button)obj;
        Node gifImg=target.lookup(".backgroundAnim");
        gifImg.setVisible(true);
        new FadeIn(gifImg).play();
        Node title=target.lookup(".Title");
        title.setVisible(false);
        /*ZoomOut zoomAnnimation=new ZoomOut(gifImg);
        //zoomAnnimation.setCycleDuration(10);
        zoomAnnimation.setSpeed(0.4);
        gifImg.setVisible(true);
        zoomAnnimation.play();
*/
        /*FadeOut fadeOutAnim=new FadeOut(gifImg);
        fadeOutAnim.setResetOnFinished(false);
        
        fadeOutAnim.play();*/

    }

    @FXML
    void stopBackgroundAnimation(MouseEvent event) {

        
        Object obj=event.getSource();
        while(!( obj instanceof Button))
        {
            obj=((Node)obj).getParent();
        }
        Button target=(Button)obj;
        Node gifImg=target.lookup(".backgroundAnim");
        Node title=target.lookup(".Title");
        title.setVisible(true);
        new FadeIn(title).play();
        gifImg.setVisible(false);
        
        
        /*try {
           stage.setScene( new Scene((Parent) FXMLLoader.load(getClass().getResource("/GUI/Views/Execution_GUI.fxml"))) );
          
          stage.show();
        } catch (IOException ex) {
            Logger.getLogger(MetaHeuristique_GUIController.class.getName()).log(Level.SEVERE, null, ex);
        }*/

    }
    
    
    
    
    @FXML
    void openACOGUI(ActionEvent event) throws IOException {

        Stage childStage=new Stage();
        childStage.setScene( new Scene((Parent) FXMLLoader.load(getClass().getResource("/GUI/Views/ACO_GUI.fxml"))) );
        childStage.initStyle(StageStyle.UNDECORATED);
        BSO_GUIController.stage=childStage;
        childStage.show();
    }

    @FXML
    void openBAGUI(ActionEvent event) {

    }

    @FXML
    void openPSOGUI(ActionEvent event) throws IOException
    {
        Stage childStage=new Stage();
        childStage.setScene( new Scene((Parent) FXMLLoader.load(getClass().getResource("/GUI/Views/PSO_GUI.fxml"))) );
        PSO_GUIController.stage=childStage;
        PSO_GUIController.stage.show();
        stage.close();
    }

    @FXML
    void openGAGUI(ActionEvent event) throws IOException
    {
        Stage childStage=new Stage();
        FXMLLoader fxml=new FXMLLoader();
        Parent parent=(Parent) fxml.load(getClass().getResource("/GUI/Views/GA_GUI.fxml"));
        childStage.setScene( new Scene(parent) );
        GA_GUIController.stage=childStage;
        stage.close();
        GA_GUIController.stage.show();
        //GA_GUIController.executionController=fxml2.getController();
        //Execution_GUIController.execController=fxml.getController();
    }
    
    @FXML
    void goBack(ActionEvent event ) throws IOException
    {
        Parent fxml=FXMLLoader.load(getClass().getResource("/GUI/Views/MainInterface.fxml"));
        stage.close();
        Scene scene=new Scene(fxml);
        MainInterfaceController.stage.setScene(scene);
        MainInterfaceController.stage.show();
    }
}
