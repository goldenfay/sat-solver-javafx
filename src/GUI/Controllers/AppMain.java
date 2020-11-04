
package GUI.Controllers;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author PC
 */
public class AppMain extends Application{

    @Override
    public void start(Stage primaryStage) throws Exception {
        
        Parent fxml=FXMLLoader.load(getClass().getResource("/GUI/Views/MainInterface.fxml"));
        
            Scene scene=new Scene(fxml);
            primaryStage.initStyle(StageStyle.UNDECORATED);
            primaryStage.setScene(scene);
            primaryStage.setHeight(scene.getHeight());
            MainInterfaceController.stage=primaryStage;
            primaryStage.show();
            
    }
    
    
    
    public static void main(String[] args){
        launch(args);
    }
}
