package SourcePackages.db.components.Tables;

import SourcePackages.db.components.DbObjects.UserScoreObject;
import SourcePackages.db.components.DbUtilities.Utilities;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonMethod;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayDeque;
import java.util.ArrayList;

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
    public void getTopScores(){

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
