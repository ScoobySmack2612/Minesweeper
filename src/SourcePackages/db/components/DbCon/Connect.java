package SourcePackages.db.components.DbCon;

import java.sql.*;

/**
 * Created by Heron on 4/20/2017.
 */
public class Connect {
    private final String dbName = "minesweeper";
    private final String username = "root";
    private final String password = "Hbovh12173msnbc!";
    private final String host = "jdbc:mysql://localhost:3306/"+dbName;

    private Connection con;

    public Connect() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(host, username, password);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public Connection getCon(){return this.con;}

}
