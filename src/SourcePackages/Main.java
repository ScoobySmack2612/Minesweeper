package SourcePackages;

import SourcePackages.db.components.Tables.User;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.ArrayList;

/**
 * Created by Heron on 3/15/2017.
 */
public class Main extends Application {
    Stage window;
    int WIDTH = 0;
    int HEIGHT= 0;
    Scene start, menu, addUser, game, won, lost;
    GridPane boardContainer;
    HBox gh;
    String windowIcon = "./images/ms_logo.png";
    User userModel = new User();
    ArrayList<String> userList;
    private int selectedUser;
    private boolean userChosen = false;

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
        Label selectUserText = new Label("Please select a user to begin");
        welcomeText.setPadding(new Insets(0,0,100,0));
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

        MenuBar menuBar = new MenuBar();
        menuBar.prefWidthProperty().bind(window.widthProperty());
        final Menu file = new Menu("User Options");
        final Menu usersDropdown = new Menu("Existing User");
        this.getUsersList(userModel, usersDropdown);
        final MenuItem newUser = new MenuItem("New User");
        newUser.setOnAction(e ->{
            Label firstName = new Label("First Name");
            Label lastName = new Label("Last Name");

            TextField fnTextfield = new TextField();
            TextField lnTextfield = new TextField();
            HBox errContainer = new HBox();

            VBox tfLabels = new VBox(30);
            tfLabels.getChildren().addAll(firstName,lastName);

            VBox tf = new VBox(20);
            tf.getChildren().addAll( fnTextfield,lnTextfield);
            Button menuBtn = new Button("Add User");
            menuBtn.setOnAction(ex ->{
                String[] userInfo = new String[2];
                try {
                    String in1 = fnTextfield.getText();
                    String in2 = lnTextfield.getText();
                    if (!in1.equals(null)&&!in2.equals(null) && (!in1.isEmpty()&&!in2.isEmpty()) ) {
                        userInfo[0] = in1;
                        userInfo[1] = in2;

                        boolean addedUser = userModel.addNewUser(userInfo);
                        if (addedUser){
                            this.getUsersList(userModel,usersDropdown);
                            window.setScene(menu);
                        }else{
                            fnTextfield.clear();
                            lnTextfield.clear();

                            Label errMsg = new Label("I'm sorry there was an error. Please try again later");
                            errContainer.getChildren().add(errMsg);
                        }
                    }else{
                        Label errMsg = new Label("Please enter a valid name, AKA not blank.");

                        errContainer.getChildren().add(errMsg);
                    }
                }catch (Exception exy){
                    System.out.println(exy);
                }
            });


            BorderPane bp = new BorderPane();
            bp.setTop(errContainer);
            bp.setLeft(tfLabels);
            bp.setCenter(tf);
            bp.setBottom(menuBtn);

            addUser = new Scene(bp, 450,150);

            window.setScene(addUser);
        });
        menuBar.getMenus().add(file);
        file.getItems().addAll(newUser,usersDropdown);


        //menu scene layout
        VBox mh = new VBox(20);
        mh.getChildren().addAll(welcomeText,easy,medium,hard);
        mh.setAlignment(Pos.CENTER);

        BorderPane menuLayout = new BorderPane();
        menuLayout.setTop(menuBar);
        menuLayout.setCenter(mh);

        //set the menu scene to use the menu layout
        //menu layout encompasses all sub layouts for menu elements

        menu = new Scene(menuLayout, 400,400);

        //game scene

        gh = new HBox(20);
        gh.setAlignment(Pos.CENTER);

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
        GameBoard theBoard = new GameBoard(size,mines, boardContainer, window, menu, gh,game,selectedUser);
        window.setOnCloseRequest(e->theBoard.quitApplication());
    }
    private void getUsersList(User userModel, Menu usersDropdown){
        usersDropdown.getItems().clear();
        userList = userModel.getAllUsers();
        ArrayList<MenuItem> miList = userModel.listToMenuItems(userList);
        for (MenuItem rb : miList) {
            usersDropdown.getItems().add(rb);
            rb.setOnAction(e -> {
                String chosenUser = rb.getText();
                selectedUser = userModel.getUserId(chosenUser);
                userChosen = true;
            });
        }
    }
}
