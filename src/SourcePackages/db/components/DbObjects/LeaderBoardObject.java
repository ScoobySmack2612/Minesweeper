package SourcePackages.db.components.DbObjects;

import SourcePackages.db.components.Tables.User;

import java.util.ArrayList;

/**
 * Created by Heron on 4/30/2017.
 */
public class LeaderBoardObject {
    String username;
    int rank;
    String difficulty;
    String time;
    long score;
    public LeaderBoardObject(String username, int rank, String difficulty, String time, long score){
        this.username = username;
        this.rank = rank;
        this.difficulty = difficulty;
        this.time = time;
        this.score = score;
    }

    public String getUsername(){return this.username;}
    public void setUsername(String un){this.username = un;}

    public int getRank(){return this.rank;}
    public void setRank(int rank){this.rank = rank;}

    public String getDifficulty(){return this.difficulty;}
    public void setDifficulty(String difficulty){this.difficulty = difficulty;}


    public String getTime(){return this.time;}
    public void setTime(String time){this.time = time;}

    public long getScore(){return this.score;}
    public void setScore(long score){this.score = score;}
}
