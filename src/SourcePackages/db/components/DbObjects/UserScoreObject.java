package SourcePackages.db.components.DbObjects;

import SourcePackages.db.components.Tables.Score;
import org.codehaus.jackson.map.ObjectMapper;

/**
 * Created by Heron on 4/28/2017.
 */
public class UserScoreObject {
    int userId;
    public UserScoreObject(int userId){
        this.userId = userId;


    }
    private void getUserScores(){
        ObjectMapper
        Score scoreTable = new Score();
        scoreTable.getUserScores(userId);
    }
}
