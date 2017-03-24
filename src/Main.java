import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;


/**
 * Created by Heron on 3/15/2017.
 */
public class Main extends Application {
    Stage window;
    Scene menu, game;

    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        window.setTitle("Minesweeper");


        //menu scene elements
        Label welcomeText = new Label("Welcome to Minesweeper :)");
        Button goButton = new Button("Easy");
        goButton.setOnAction(e -> {
            window.setScene(game);
        });

        //menu scene layout
        VBox mv = new VBox(20);
        mv.getChildren().add(welcomeText);
        mv.setAlignment(Pos.CENTER);

        HBox mh = new HBox(20);
        mh.getChildren().add(goButton);
        mh.setAlignment(Pos.CENTER);

        BorderPane menuLayout = new BorderPane();
        menuLayout.setTop(mv);
        menuLayout.setCenter(mh);

        //set the menu scene to use the menu layout
        //menu layout encompasses all sub layouts for menu elements
        menu = new Scene(menuLayout, 400,400);

        //game scene
        Label gameText = new Label("Here we go!");
        gameText.setAlignment(Pos.CENTER);

        VBox gv = new VBox(20);
        gv.getChildren().add(gameText);

        BorderPane gameLayout = new BorderPane();
        gameLayout.setTop(gv);

        GridPane board = new GridPane();

        game = new Scene(gameLayout, 400,400);

        board.setPadding(new Insets(0,0,0,0) );
        board.setAlignment(Pos.CENTER);
        gameLayout.setCenter(board);

        Tile[][] tile = getBoard(8, board);



        window.setScene(menu);
        window.show();
    }
    private static Tile[][] getBoard(int size, GridPane board){
        //instantiate tile in board layout
        Tile[][] tile = new Tile[size][size];
        for (int x = 0; x < size; x ++){
            for (int y = 0; y < size; y++){
                //get a button with referable location
                Tile slot = new Tile(x,y);
                //set referable location to easy callback
                slot.setId(slot.getPosAsString());
                //add the button to the gridpane layout in the position of loop
                board.add(slot, x,y);
                tile[x][y] = slot;
            }
        }
        return tile;
    }
}
