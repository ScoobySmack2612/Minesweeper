package SourcePackages;

import SourcePackages.db.components.DbObjects.UserScoreObject;
import SourcePackages.db.components.Tables.Score;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import javax.swing.text.TableView;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * Created by Heron on 4/24/2017.
 */

public class UserScore{
    public UserScore(Stage window, int userId) {
        getUserScores(userId);
        Group root = new Group();
        //layouts
        TabPane tp = new TabPane();
        tp.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        //elements
        String[] tabs = {"Easy", "Medium", "Hard"};
        for (int x = 0; x < tabs.length; x++) {
            Tab tab = new Tab();
            tab.setText(tabs[x]);
            tp.getTabs().add(tab);
        }

        BorderPane bp = new BorderPane();
        bp.setCenter(tp);
        root.getChildren().add(bp);

        window.setScene(new Scene(root,400,400));
    }
    private void getUserScores(int userId){
        UserScoreObject scores = new UserScoreObject(userId);

    }
}
