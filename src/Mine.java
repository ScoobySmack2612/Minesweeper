/**
 * Created by Heron on 3/23/2017.
 */
public class Mine implements CoordinatePair{
    int xCoord;
    int yCoord;
    public Mine(int x, int y){
        this.xCoord = setX(x);
        this.yCoord = setY(y);
    }
}
