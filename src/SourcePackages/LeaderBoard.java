package SourcePackages;

import SourcePackages.db.components.DbObjects.LeaderBoardObject;
import SourcePackages.db.components.DbObjects.UserScoreObject;
import SourcePackages.db.components.Tables.Score;
import SourcePackages.db.components.Tables.User;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Created by Heron on 4/24/2017.
 */
public class LeaderBoard {
    public LeaderBoard(Stage window, Scene menu,int userId){
        ObservableList<LeaderBoardObject> leaderboard = getTopScores();
        Group root = new Group();
        //layouts
        TabPane tp = new TabPane();
        tp.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        VBox tableHeader = new VBox();
        String[] tabs = {"Easy", "Medium", "Hard"};
        tableHeader.getChildren().addAll(tp);

        //elements

        Button mm = new Button("Main Menu");
        mm.setOnAction( e -> window.setScene(menu));
        Button userScores = new Button("Your Scores");
        userScores.setOnAction( e -> new UserScore(window,userId,menu));


        HBox buttons = new HBox();
        buttons.getChildren().addAll(mm,userScores);

        for (int x = 0; x < tabs.length; x++) {
            Tab tab = new Tab();
            tab.setText(tabs[x]);
            tp.getTabs().add(tab);
            tab.setContent(getTabContent(tab,leaderboard));
            tab.setOnSelectionChanged(e -> getTabContent(tab,leaderboard));
        }
        BorderPane bp = new BorderPane();
        bp.setTop(tableHeader);
        bp.setBottom(buttons);
        root.getChildren().add(bp);

        window.setScene(new Scene(root,300,475));
    }
    private ObservableList<LeaderBoardObject> getTopScores(){return new Score().getTopScores();}
    private TableView getTabContent(Tab tab, ObservableList<LeaderBoardObject> scores){
        TableColumn[] columns = {new TableColumn("Rank"), new TableColumn("User"),new TableColumn("Score"), new TableColumn("Time")};
        TableView tv = new TableView();
        for (LeaderBoardObject usc : scores) {
            if (tab.getText().equals(usc.getDifficulty())) {
                for (TableColumn column : columns) {
                    tv.getColumns().remove(column);
                    tv.getColumns().add(column);
                    if (column.getText().equals("Rank")) {
                        column.setCellValueFactory(new PropertyValueFactory<>("rank"));
                    }else if (column.getText().equals("User")){
                        column.setCellValueFactory(new PropertyValueFactory<>("username"));
                    }else if (column.getText().equals("Score")) {
                        column.setCellValueFactory(new PropertyValueFactory<>("score"));
                    }else if (column.getText().equals("Time")) {
                        column.setCellValueFactory(new PropertyValueFactory<>("time"));
                    }
                }
            }
        }
        tv.setId(tab.getText());
        tv.setItems(scores);
        tab.setContent(tv);
        tv.setPlaceholder(new Label("There are no "+tab.getText()+ " records."));
        return tv;
    }
}
