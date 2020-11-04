
package GUI.Controllers;


import static GUI.Controllers.MetaHeuristique_GUIController.stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


/**
 * FXML Controller class
 *
 * @author PC
 */
public class Blind_Search_GUIController implements Initializable {
    public static Stage stage;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        System.out.println("GUI.Controllers.Blind_Search_GUIController.initialize()");
        
    } 
    
    
    @FXML
    void openBFDDFSGUI(MouseEvent event) throws IOException {
        Parent fxml=FXMLLoader.load(getClass().getResource("/GUI/Views/BFS_DFS_GUI.fxml"));
        Scene scene=new Scene(fxml);
        Stage stg = new Stage();
        stg.setScene(scene);
        BFS_DFS_GUIController.stage=stg;
        BFS_DFS_GUIController.stage.show();
        stage.close();
    }
    @FXML
    void openAStartGui(MouseEvent event) throws IOException {
        
        Stage childStage=new Stage();
        FXMLLoader fxml=new FXMLLoader();
        Parent parent=(Parent) fxml.load(getClass().getResource("/GUI/Views/AStart_GUI.fxml"));
        childStage.setScene( new Scene(parent) );
        AStart_GUIController.stage=childStage;
        AStart_GUIController.stage.show(); 
        stage.close();
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
