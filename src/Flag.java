import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
/**
 * Created by Heron on 4/12/2017.
 */
public class Flag extends Button {
    private boolean clicked;

    public Flag(){
        this.setStyles();
        this.setPrefSize(25,25);
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
