package SourcePackages.db.components.Tables;

import SourcePackages.db.components.DbObjects.LeaderBoardObject;
import SourcePackages.db.components.DbObjects.UserScoreObject;
import SourcePackages.db.components.DbUtilities.Utilities;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.ResultSet;

/**
 * Created by Heron on 4/22/2017.
 */
public class Score {
    public Score(){}

    public boolean enterScore(String diff, long tInMillis, String time, int userId){
        Utilities util = new Utilities();

        try{
            String insertStatement = ("INSERT INTO score"+
                    "(difficulty, score, time, user_id)"+
                    "VALUES"+
                    " (" +"'"+ diff+"', " + "'" + tInMillis + "', " + "'" + time + "', " + "'" + userId + "'" + ")" );

            boolean inserted = util.insertRecord(insertStatement);

            if (inserted){
                return true;
            }else{
                return false;
            }
        }catch(Exception e){
            System.out.println(e);
        }finally {
            util.closeDbCon();
        }
        return false;
    }
    public ObservableList<LeaderBoardObject> getTopScores(){
        ObservableList<LeaderBoardObject> leaderboard = FXCollections.observableArrayList();
        int rank = 1;
        Utilities util = new Utilities();
        try {
            ResultSet rs = util.mkQuery("SELECT * FROM score ORDER BY time LIMIT 10");

            while(rs.next()){
                int userId = rs.getInt("user_id");
                String difficulty = rs.getString("difficulty");
                String time = rs.getString("time");
                long score = rs.getLong("score");
                leaderboard.add(new LeaderBoardObject(userId,rank,difficulty,time, score));
                rank++;
            }
        }catch (Exception e){
            System.out.println(e);
        }finally{
            util.closeDbCon();
        }
        return null;
    }
    public ObservableList<UserScoreObject> getUserScores(int userId){
        ObservableList<UserScoreObject> scores = FXCollections.observableArrayList();
        int rank = 1;
        Utilities util = new Utilities();

        try{
            String query = "select * from score where user_id = "+userId+" order by time LIMIT 10";

            ResultSet result = util.mkQuery(query);
            while(result.next()){
                String difficulty = result.getString("difficulty");
                String time = result.getString("time");
                long score = result.getLong("score");
                scores.add(new UserScoreObject(userId,rank,difficulty,time, score));
                rank++;
            }
            return scores;
        }catch(Exception e){
            System.out.println(e);
        }finally {
            util.closeDbCon();
        }
        return null;
    }
}
