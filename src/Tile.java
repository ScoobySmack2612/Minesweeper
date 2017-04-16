import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Created by Heron on 3/15/2017.
 */

public class Tile extends Button{
    private int xCoord;
    private int yCoord;
    private char state;
    private boolean clicked = false;
    private boolean flagged = false;

    public Tile(int x, int y){
        this.xCoord = x;
        this.yCoord = y;
        this.setId(x+","+y);
        this.setPrefSize(47,47);
        this.setStyle(null);
    }
    public int getX(){return this.xCoord;}
    public int getY(){return this.yCoord;}

    public boolean setMineState(Mine[] mines){
        boolean result = false;
        for (Mine mine : mines){
            String mineId = mine.getId();
            if (this.getId().equals(mineId)){
                this.state = 'M';
                result = true;
            }
        }
        return result;
    }
    public boolean isClicked(){return this.clicked;}
    public void setBlankState(){
        this.state ='E';
    }
    public void setNeighboringState(){
        this.state = 'N';
    }
    public char getState(){return this.state;}
    public void setMineStyle(){
        Image bomb = new Image("./images/bomb.png", 25, 25, true,true);
        this.setStyle("-fx-background-color: #B33A3A");
        this.setGraphic(new ImageView(bomb));
    }
    public void setBlankTileStyles(){
        this.setStyle("-fx-background-color: #CCCCCC; -fx-border-color: black;");
        this.clicked = true;
    }
    public void setNumMineTileStyles(int surroundingMines){
        this.setStyle("-fx-background-color: #CCCCCC; -fx-font: 23px Georgia; -fx-border-color:black;");
        this.setText(Integer.toString(surroundingMines));
        this.clicked = true;
    }
    public void setFlagStyles(){
        Image flag = new Image("./images/flag.png");
        this.setGraphic(new ImageView(flag));
        this.flagged = true;
    }
    public boolean isFlagged(){return this.flagged;}
    public void setNonFlagStyles(){
        this.setGraphic(null);
        this.flagged = false;
    }
}
