package SourcePackages;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * Created by Heron on 4/12/2017.
 */
public class Flag extends Button {
    private boolean clicked;

    public Flag(){
        this.setStyles();
        this.setPrefSize(25,25);
        this.setTooltip(new Tooltip("Try pressing F"));

    }
    private void setStyles(){
        if (this.clicked){
            setStyle("-fx-background-color:#CCCCCC");
        }else{
            setStyle(null);
        }
        Image flag = new Image("./images/flag.png");
        this.setGraphic(new ImageView(flag));
    }
    public void setFlagState(){
        if (this.clicked){
            this.clicked = false;
        }else{
            this.clicked = true;
        }
        setStyles();
    }
    public boolean isClicked(){return this.clicked;}
}
