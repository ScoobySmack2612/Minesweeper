package SourcePackages.db.components.DbObjects;

/**
 * Created by Heron on 4/28/2017.
 */
public class UserScoreObject {
    int userId;
    int rank;
    String difficulty;
    String time;
    long score;
    public UserScoreObject(int userId){
        this.userId = userId;
    }
    public UserScoreObject(int userId, int rank, String difficulty,String time, long score){
        this.userId = userId;
        this.rank = rank;
        this.difficulty = difficulty;
        this.time = time;
        this.score = score;
    }
    public int getRank(){return this.rank;}
    public void setRank(int rank){this.rank = rank;}
    public String getDifficulty(){return this.difficulty;}
    public void setDifficulty(String difficulty){this.difficulty = difficulty;}
    public String getTime(){return this.time;}
    public void setTime(String time){this.time = time;}
    public long getScore(){return this.score;}
    public void setScore(long score){this.score = score;}
}
