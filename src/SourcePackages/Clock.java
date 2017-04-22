package SourcePackages;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.text.Font;

import java.text.SimpleDateFormat;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Heron on 4/13/2017.
 */
public class Clock extends Label {
    long start;
    long interval;
    long difference;
    String label = "00:00";
    Timer timer;
    boolean run = true;
    Scene scene;
    public Clock(Scene scene) {
        this.scene = scene;
        loadFont();

        loadStyleSheet();
        this.getStyleClass().add("clock");
        startTime();
        if(run) {
            timer = new Timer();
            TimerTask runTask = new TimerTask() {
                @Override
                public void run() {
                    setInterval();
                    difference = interval - start;
                    label = new SimpleDateFormat("mm:ss").format(difference);
                    Platform.runLater(new Runnable() {
                        public void run() {
                            setText(label);
                        }
                    });
                }
            };
            timer.schedule(runTask, 0, 1000);
        }
    }
    private void startTime(){
        this.start = System.currentTimeMillis();
    }
    private void setInterval(){
        this.interval = System.currentTimeMillis();
    }
    public String stopTimer(){
        timer.cancel();
        timer.purge();
        this.run = false;
        String result = this.label;
        return result;
    }
    private void loadFont(){
        try {
            Font.loadFont(Clock.class.getResource("../fonts/DS-DIGIB.TTF").toExternalForm(), 10);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private void loadStyleSheet(){
        scene.getStylesheets().add(getClass().getResource("../css/styles.css").toExternalForm());
    }
}