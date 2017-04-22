package SourcePackages;

import javafx.animation.ParallelTransition;
import javafx.animation.PathTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.CubicCurveTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

/**
 * Created by Heron on 4/16/2017.
 */

public class Rainbow extends Rectangle {
    private int xCoord;
    private int yCoord;
    private int width;
    private int height;

    public Rainbow(int x,int y,int w,int h){
        this.xCoord = x;
        this.yCoord = y;
        this.width = w;
        this.height = h;
    }
}
