package SourcePackages;
import SourcePackages.db.components.DbObjects.UserScoreObject;
import SourcePackages.db.components.Tables.Score;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Created by Heron on 4/24/2017.
 */

public class UserScore{
    int userId;
    public UserScore(Stage window, int userId, Scene menu) {
        this.userId = userId;
        ObservableList<UserScoreObject> scores = getUserScores();
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

        for (int x = 0; x < tabs.length; x++) {
            Tab tab = new Tab();
            tab.setText(tabs[x]);
            tp.getTabs().add(tab);
            tab.setContent(getTabContent(tableHeader,tab,scores));
            tab.setOnSelectionChanged(e -> getTabContent(tableHeader,tab,scores));
        }
        BorderPane bp = new BorderPane();
        bp.setTop(tableHeader);
        bp.setBottom(mm);
        root.getChildren().add(bp);

        window.setScene(new Scene(root,300,475));
    }
    private ObservableList<UserScoreObject> getUserScores(){
        return new Score().getUserScores(userId);
    }
    private TableView getTabContent(VBox header, Tab tab,ObservableList<UserScoreObject> scores){
        TableColumn[] columns = {new TableColumn("Rank"), new TableColumn("Score"), new TableColumn("Time")};
        TableView tv = new TableView();
        for (UserScoreObject usc : scores) {
            if (tab.getText().equals(usc.getDifficulty())) {
                for (TableColumn column : columns) {
                    tv.getColumns().remove(column);
                    tv.getColumns().add(column);
                    if (column.getText().equals("Rank")){
                        column.setCellValueFactory(new PropertyValueFactory<>("rank"));
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
        tv.setPlaceholder(new Label("You don't have any "+tab.getText()+ " records."));
        return tv;
    }
}
