package SourcePackages;

import SourcePackages.db.components.Tables.Score;
import SourcePackages.db.components.Tables.User;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;

/**
 * Created by Heron on 4/4/2017.
 */
public class GameBoard {
    private int size;
    private int numMines;
    private Tile[][] board;
    private Mine[] mines;
    private Stage window;
    private Scene menu,game;
    private HBox header;
    private GridPane gameboard;
    private boolean gameEnded = false;
    private Flag flag;
    Clock clock;
    private boolean timing;
    String finalTime = "00:00";
    private long score;
    private int user;
    private String difficulty = null;

    public GameBoard(int size, int mines, GridPane layout, Stage window, Scene menu,HBox heading,Scene game, int selectedUser,String difficulty) {
        this.size = size;
        this.numMines = mines;
        this.window = window;
        this.menu = menu;
        this.gameboard = layout;
        this.header = heading;
        this.game = game;
        this.user = selectedUser;
        this.difficulty = difficulty;

        this.setMines();

        this.setBoardArray();
        this.setMineStates();
        this.setNonMineStates();
        this.setFlag();
    }
    private void startClock(){
        timing = true;
        clock = new Clock(this.game);
        this.header.getChildren().add(clock);
    }
    private void stopClock(){
        this.score = clock.timeInMillis();
        this.finalTime = clock.stopTimer();
    }
    private void setMineStates(){
        for (int x = 0; x < this.size; x++){
            for (int y =0; y < this.size; y++){
                Tile toCheck = this.board[x][y];
                toCheck.setMineState(this.mines);
            }
        }
    }
    private void setNonMineStates(){
        for (int x = 0; x < this.size; x++){
            for (int y =0; y < this.size; y++){
                Tile toCheck = this.board[x][y];
                int numSurrMines = this.countSurroundingMines(toCheck);
                if (numSurrMines!=0&& toCheck.getState()!='M'){
                    toCheck.setNeighboringState();
                }else if (numSurrMines==0&&toCheck.getState()!='M'){
                    toCheck.setBlankState();
                }
            }
        }
    }
    private void setFlag(){
        flag = new Flag();
        header.getChildren().add(flag);
        flag.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(final KeyEvent keyEvent) {
                if (keyEvent.getCode() == KeyCode.F){
                    flag.setFlagState();
                    keyEvent.consume();
                }
            }
        });
        flag.setOnAction( e ->{
           if (!gameEnded){
               flag.setFlagState();
           }
        });
    }
    private void setBoardArray() {
        GridPane boardContainer = this.gameboard;
        board = new Tile[this.size][this.size];
        for (int x = 0; x < this.size; x++) {
            for (int y = 0; y < this.size; y++) {
                Tile square = new Tile(x, y);
                this.board[x][y] = square;
                boardContainer.add(square, y, x);
                boardContainer.setAlignment(Pos.CENTER);
                square.setOnAction(e -> handleButtonClick(square));
            }
        }
    }

    private void handleButtonClick(Tile squareClicked){
        if (!timing) {
            this.startClock();
        }
        boolean isMine = false;
        if (!squareClicked.isFlagged() && !gameEnded) {
            if (!this.flag.isClicked()) {
                try {
                    for (Mine mine : this.mines) {
                        String mineId = mine.getId();
                        if ((squareClicked.getId()).equals(mineId)) {
                            /*isMine = true;
                            gameEnded = true;
                            this.stopClock();
                            this.showAllTiles();
                            timeout();*/
                            this.setWinScene();
                        }
                    }
                    if (!isMine) {
                        int surroundingMines = countSurroundingMines(squareClicked);
                        if (surroundingMines == 0) {
                            squareClicked.setBlankTileStyles();
                            this.openOnUp(squareClicked);

                        } else{
                            squareClicked.setNumMineTileStyles(surroundingMines);
                        }
                        boolean won = checkForWin();
                        if (won&&!gameEnded) {
                            this.stopClock();
                            setWinScene();
                        }
                        else{
                            return;
                        }
                    }
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            } else if (this.flag.isClicked()) {
                if (!squareClicked.isFlagged()&&!squareClicked.isClicked()) {
                    squareClicked.setFlagStyles();
                    return;
                }
            }
        }else if (squareClicked.isFlagged()&& this.flag.isClicked() && !gameEnded){
            squareClicked.setNonFlagStyles();
            return;
        }
    }
    private void setMines(){
        mines = new Mine[numMines];
        int minesLoop = 0;

        for (int mineIndex = 0; mineIndex < numMines;) {
            boolean addedMine = false;
            int coordX = (int) (Math.random() * (size - 1));
            int coordY = (int) (Math.random() * (size - 1));
            Mine mine = new Mine(coordX, coordY);
            if(minesLoop == 0) {
                mines[mineIndex] = mine;
                minesLoop++;
                addedMine = true;
            }else {
                for (int index = 0; index < minesLoop; index++){
                    boolean differentCoordinates = mines[index].differentCoordinates(mine);
                    if (differentCoordinates){
                        mines[mineIndex] = mine;
                        addedMine=true;
                        minesLoop++;
                    }else{
                        break;
                    }
                }

            }
            if (addedMine){
                mineIndex++;
            }
        }
    }
    private int countSurroundingMines(Tile origin){
        int result = 0;

        int[] originCoordinate = {origin.getX(),origin.getY()};

        for (int rangeX = -1; rangeX <= 1; rangeX++){
            for (int rangeY = -1; rangeY <= 1; rangeY++){

                boolean validX = ((0<=(originCoordinate[0]+rangeX)&&(originCoordinate[0]+rangeX)< this.size));
                boolean validY = ((0<=(originCoordinate[1])+rangeY&&(originCoordinate[1]+rangeY)< this.size));

                if (validX && validY){
                    int[] posCheck = {originCoordinate[0]+rangeX,originCoordinate[1]+rangeY};
                    char tileState = this.board[posCheck[0]][posCheck[1]].getState();
                    if (tileState =='M'){
                        result++;
                    }
                }
            }
        }

        return result;
    }
    private void openOnUp(Tile origin){
        int[] originCoordinate = {origin.getX(),origin.getY()};

        for (int rangeX = -1; rangeX <= 1; rangeX++){
            for (int rangeY = -1; rangeY <= 1; rangeY++){

                boolean validX = ((0<=(originCoordinate[0]+rangeX)&&(originCoordinate[0]+rangeX)< this.size));
                boolean validY = ((0<=(originCoordinate[1])+rangeY&&(originCoordinate[1]+rangeY)< this.size));

                if (validX && validY){
                    int[] posCheck = {originCoordinate[0]+rangeX,originCoordinate[1]+rangeY};
                    Tile toCheck = this.board[posCheck[0]][posCheck[1]];
                    if (toCheck.getState()=='E' && !toCheck.isClicked()&&!toCheck.isFlagged()){
                        toCheck.setBlankTileStyles();
                        openOnUp(toCheck);
                    }else if (toCheck.getState()=='N'&&!toCheck.isFlagged()){
                        int numNeighborMines = this.countSurroundingMines(toCheck);
                        toCheck.setNumMineTileStyles(numNeighborMines);
                    }
                }
            }
        }
    }
    private boolean checkForWin(){
        boolean result = true;
        for (Tile[] row: this.board){
            for (Tile slot : row){
                if ((slot.getState()=='E'||slot.getState()=='N')&&!slot.isClicked()){
                    result = false;
                }
            }
        }

        return result;
    }
    private void setWinScene(){
        Label wonTxt = new Label("Congratulations! You beat the game! Your time was: "+this.finalTime);
        Button replay = new Button("Again!!!");
        replay.setOnAction(e ->window.setScene(menu));
        Button quit = new Button("Quit");
        quit.setOnAction(e -> this.quitApplication());

        VBox wonVB = new VBox(30);
        wonVB.setAlignment(Pos.CENTER);

        Button addScore = new Button("Save Score!");
        wonVB.getChildren().addAll(wonTxt,addScore);
        addScore.setOnAction(e ->{
            Score newScore =new Score();
            boolean scoreEntered = newScore.enterScore(difficulty,score,finalTime,user);
            if (scoreEntered){
                addScore.setVisible(false);
                Button userScores = new Button("View Your Scores");
                userScores.setOnAction( ex -> new UserScore(window,user,menu));

                Button leaderboard = new Button("View Leaderboard");
                leaderboard.setOnAction( ex -> new LeaderBoard(window,menu));

                wonVB.getChildren().addAll(userScores,leaderboard);
            }
        });

        HBox wonHB = new HBox(20);
        wonHB.getChildren().addAll(replay,quit);
        wonHB.setAlignment(Pos.CENTER);

        BorderPane wonBP = new BorderPane();
        wonBP.setTop(wonVB);
        wonBP.setCenter(wonHB);

        Scene won = new Scene(wonBP, 400,400);
        window.setScene(won);
    }
    private void setLoseScene(){
        Label lostTxt = new Label("I'm sorry you lost at: "+this.finalTime+ " :(");
        Label lostTxt2 = new Label("Try again!!");

        Button replay = new Button("Again!!!");
        replay.setOnAction(e ->window.setScene(menu));
        Button quit = new Button("Quit");
        quit.setOnAction(e -> this.quitApplication());

        VBox lostVB = new VBox(20);
        lostVB.getChildren().addAll(lostTxt,lostTxt2);
        lostVB.setAlignment(Pos.CENTER);

        HBox lostHB = new HBox(20);
        lostHB.getChildren().addAll(replay,quit);
        lostHB.setAlignment(Pos.CENTER);

        BorderPane lostBP = new BorderPane();
        lostBP.setTop(lostVB);
        lostBP.setCenter(lostHB);

        Scene lost = new Scene(lostBP, 400,400);
        window.setScene(lost);
    }
    private void timeout(){
        Task<Void> sleeper = new Task<Void>(){
            @Override
            protected Void call() throws Exception{
                try {
                    Thread.sleep(5000);
                }catch (InterruptedException e){

                }return null;
            }
        };
        sleeper.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                reset();
                setLoseScene();
            }
        });
        new Thread(sleeper).start();
    }
    private void showAllTiles(){
        for (Tile[] row : this.board){
            for (Tile slot : row){
                if (slot.isFlagged()){
                    slot.setNonFlagStyles();
                }
                if (slot.getState()=='M'){
                    slot.setMineStyle();
                }else if (slot.getState()=='N'){
                    int mines = countSurroundingMines(slot);
                    slot.setNumMineTileStyles(mines);
                }else{
                    slot.setBlankTileStyles();
                }
            }
        }
    }
    private void reset(){
        for ( Tile[] row: this.board){
            for ( Tile slot: row){
                this.gameboard.getChildren().removeAll(slot);

            }
        }
        this.header.getChildren().removeAll(clock, flag);
    }
    public void quitApplication(){
        if (timing) {
            clock.stopTimer();
        }
        window.close();
    }
}
