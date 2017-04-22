package SourcePackages.db.components.DbUtilities;

import SourcePackages.db.components.DbCon.Connect;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Heron on 4/20/2017.
 */
public class Utilities {
    private final Connect connect = new Connect();
    private final Connection con = connect.getCon();
    Statement statement;
    public Utilities(){
        try {
            statement = con.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public ResultSet mkQuery(String query) {
        ResultSet result;
        try {
            result = this.statement.executeQuery(query);
            return result;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public boolean insertRecord(String insertion){
        try{
            statement.executeUpdate(insertion);
            return true;
        }catch(SQLException e){
            e.printStackTrace();
            return false;
        }
    }
    public void closeDbCon() {
        try {
            con.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
