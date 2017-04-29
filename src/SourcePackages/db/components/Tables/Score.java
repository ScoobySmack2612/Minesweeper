package SourcePackages.db.components.Tables;

import SourcePackages.db.components.DbUtilities.Utilities;

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
    public ArrayList<ResultSet> getUserScores(int userId){
        ArrayList<ResultSet> scores = new ArrayList<>();
        Utilities util = new Utilities();

        try{
            String query = "select * from score where user_id = "+userId+" order by time";

            ResultSet result = util.mkQuery(query);
            while(result.next()){
                scores.add(result);
            }
        }catch(Exception e){
            System.out.println(e);
        }finally {
            util.closeDbCon();
        }
        return null;
    }
}
