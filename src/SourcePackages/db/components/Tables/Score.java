package SourcePackages.db.components.Tables;

import SourcePackages.db.components.DbUtilities.Utilities;

import java.sql.SQLException;

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
    public void getUserScores(){

    }
}
