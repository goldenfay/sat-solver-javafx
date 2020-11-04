package satproblem;
import GraphSearchs.Bases.Node;
import javafx.animation.FillTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public  class ToggleSwitch extends Parent {

        private BooleanProperty switchedOn = new SimpleBooleanProperty(false);
        private Label label;
        private TranslateTransition translateAnimation = new TranslateTransition(Duration.seconds(0.3));
        private FillTransition fillAnimation = new FillTransition(Duration.seconds(0.3));

        private ParallelTransition animation = new ParallelTransition(translateAnimation, fillAnimation);

        public BooleanProperty switchedOnProperty() {
            return switchedOn;
        }

        public ToggleSwitch() {
            Rectangle background = new Rectangle(70, 30);
            background.setArcWidth(30);
            background.setArcHeight(30);
            background.setFill(Color.WHITE);
            background.setStroke(Color.rgb(76, 144, 255));

            Circle trigger = new Circle(15);
            trigger.setCenterX(15);
            trigger.setCenterY(15);
            trigger.setFill(Color.WHITE);
            trigger.setStroke(Color.LIGHTGRAY);
            
            DropShadow shadow = new DropShadow();
            shadow.setRadius(2);
            trigger.setEffect(shadow);
            HBox toggleContainer=new HBox(); 
            
            
            translateAnimation.setNode(trigger);
            fillAnimation.setShape(background);

            getChildren().addAll(background,trigger);
            
            label=new Label("BFS");
            label.setPrefHeight(20);
            
            label.setLayoutX(this.getLayoutX()-label.getMaxWidth()-30);
            label.setLayoutY(trigger.getCenterY()-(label.getPrefHeight()/2));
            
            label.getStyleClass().add("label_isUNSelected");

            getChildren().add(label);

            switchedOn.addListener((obs, oldState, newState) -> {
                boolean isOn = newState.booleanValue();
                translateAnimation.setToX(isOn ? 70-30: 0);
                fillAnimation.setFromValue(isOn ? Color.WHITE : Color.rgb(76, 144, 255));
                fillAnimation.setToValue(isOn ? Color.rgb(76, 144, 255) : Color.WHITE);
                label.setLayoutX(isOn?label.getLayoutX()+40 :this.getLayoutX()-label.getMaxWidth()-30);
                if(isOn) {
                    label.getStyleClass().add("label_isSelected");
                    label.setText("DFS");
                }
                else {
                    label.getStyleClass().remove("label_isSelected");
                    label.setText("BFS");
                }

                animation.play();
            });

            setOnMouseClicked(event -> {
                switchedOn.set(!switchedOn.get());
            });
        }

    public Label getLabel() {
        return label;
    }

    public void setLabel(String title) {
        this.label.setText(title);
    }
        
        
        
        
        
}

