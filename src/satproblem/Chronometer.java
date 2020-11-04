
package satproblem;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.util.Duration;

/**
 * Class qui implémente le fonctionnement d'un chronometre.
 * @author PC
 */
public class Chronometer {
    
    private Scene scene;
    private Text displayText;
    private int millis,secs,mins;
    private boolean sos;
    private Timeline timeline;

    public Chronometer(Scene scene, Text displayText) {
        this.scene = scene;
        this.displayText = displayText;
        this.millis=this.secs=this.mins=0;
    }

    public Chronometer(Text displayText) {
        this.displayText = displayText;
        this.millis=this.secs=this.mins=0;
    }
    
    
    
    public void start(){
        
        timeline=new Timeline(new KeyFrame(Duration.millis(10),new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                incrementTime();
            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }
    
    public void stop(){
        timeline.stop();
    }
    
    
    private void incrementTime(){
        this.millis+=10;
        if(this.millis>=1000) {
            millis=millis-1000;
            secs++;
        }
        if(secs==60){
            secs=secs-60;
            mins++;
        }
        
        displayText.setText(this.toString());
    }

    @Override
    public String toString() {

        return this.mins+":"+this.secs+":"+this.millis;
    }
    
    
    
    
    
    
}
