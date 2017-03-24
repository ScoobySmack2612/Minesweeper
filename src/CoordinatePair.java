/**
 * Created by Heron on 3/20/2017.
 */
public interface CoordinatePair {


    default int setX(int coordinate){
        int xCoord = coordinate;
        return xCoord;
    }
    default  int setY(int coordinate){
        int yCoord = coordinate;
        return yCoord;
    }
}
