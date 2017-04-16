/**
 * Created by Heron on 3/23/2017.
 */
public class Mine {
    private int xCoord;
    private int yCoord;
    private String id;

    public Mine(int x, int y){
        this.xCoord = x;
        this.yCoord = y;

        this.setId(this.xCoord+","+this.yCoord);
    }
    private void setId(String id){this.id = id;}

    public String getId(){return this.id;}

    public int getX(){return this.xCoord;}

    public int getY(){return this.yCoord;}
    public boolean differentCoordinates(Mine toCompare){
        boolean result;
        if (this.getId().equals(toCompare.getId())){
            result = false;
        }else{
            result = true;
        }
        return result;
    }
}
