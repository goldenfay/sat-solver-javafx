
package GUI.Controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author PC
 */
public class ACO_GUIController implements Initializable {

    
    public static Stage stage;
    
    @FXML
    private Text execTime_text;

    @FXML
    private Text bestSol_text;

    @FXML
    private Text rate_text;

    @FXML
    private Text rate_text1;

    @FXML
    private Text rate_text2;

    @FXML
    private Text rate_text3;
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }   
    
    
    
    
    @FXML
    void showExecutionDetails(ActionEvent event) {

    }
    
}
