import javafx.scene.control.Button;

/**
 * Created by Heron on 3/15/2017.
 */
public class Tile extends Button implements CoordinatePair {
    private int xCoord;
    private int yCoord;

    public Tile(int x, int y){
        this.xCoord = setX(x);
        this.yCoord = setY(y);

        Button square = new Button();
    }
    public String getPosAsString(){
        String result = ("("+this.yCoord+","+this.xCoord+")");
        return result;
    }
}
