import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;
/**
 * Created by Heron on 3/15/2017.
 */
public class Main extends Application {
    Stage window;
    int WIDTH = 0;
    int HEIGHT= 0;
    Scene menu, game, won, lost;
    GridPane boardContainer;
    HBox gh;
    Flag flag;
    String windowIcon = "./images/ms_logo.png";

    public static void main(String[] args){
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        window.setTitle("inesweeper");
        window.getIcons().add(new Image(windowIcon));

        //menu scene elements
        Label welcomeText = new Label("Welcome to Minesweeper :)");
        Button easy = new Button("Easy");
        easy.setOnAction(e -> {
            int[] level = setDifficulty("easy");
            window.setScene(game);
            startGame(level);
        });
        Button medium = new Button("Medium");
        medium.setOnAction(e -> {
            int[] level = setDifficulty("medium");
            window.setScene(game);
            startGame(level);
        });
        Button hard = new Button("Hard");
        hard.setOnAction(e -> {
            int[] level = setDifficulty("hard");
            window.setScene(game);
            startGame(level);
        });
        //menu scene layout
        VBox mv = new VBox(20);
        mv.getChildren().add(welcomeText);
        mv.setAlignment(Pos.CENTER);

        HBox mh = new HBox(20);
        mh.getChildren().addAll(easy,medium,hard);
        mh.setAlignment(Pos.CENTER);

        BorderPane menuLayout = new BorderPane();
        menuLayout.setTop(mv);
        menuLayout.setCenter(mh);

        //set the menu scene to use the menu layout
        //menu layout encompasses all sub layouts for menu elements
        menu = new Scene(menuLayout, 400,400);

        //game scene
        flag = new Flag();
        flag.setOnAction(e -> flag.setFlagState());


        gh = new HBox(20);
        gh.setAlignment(Pos.CENTER);
        gh.getChildren().addAll(flag);

        BorderPane gameLayout = new BorderPane();
        gameLayout.setTop(gh);

        boardContainer = new GridPane();

        game = new Scene(gameLayout, 570,600);

        boardContainer.setPadding(new Insets(0,0,0,0) );
        boardContainer.setAlignment(Pos.CENTER);
        gameLayout.setCenter(boardContainer);

        window.setScene(menu);
        window.show();
    }
    private int[] setDifficulty(String DIFFICULTY){
        int result[] = new int[2];
        if (DIFFICULTY == "easy"){
            result[0] = 8;
            result[1] = 10;
        }else if (DIFFICULTY == "medium"){
            result[0] = 10;
            result[1] = 12;
        }else if (DIFFICULTY == "hard"){
            result[0] = 12;
            result[1] = 14;
        }

        return result;
    }
    private void startGame(int[] level){
        int size = level[0];
        int mines = level[1];
        GameBoard theBoard = new GameBoard(size,mines, boardContainer, window, menu,flag, gh,game);
        window.setOnCloseRequest(e->theBoard.quitApplication());
    }
}
