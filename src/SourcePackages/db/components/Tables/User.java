package SourcePackages.db.components.Tables;

import SourcePackages.db.components.DbUtilities.Utilities;
import com.mysql.jdbc.Util;
import javafx.scene.control.MenuItem;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class User {
    Map<String, Integer> userDictionary = new HashMap<String, Integer>();
    public User(){}
    public ArrayList<String> getAllUsers(){
        Utilities util = new Utilities();
        ArrayList<String> result = new ArrayList<>();

        try {
            ResultSet rs = util.mkQuery("select * from user");
            while (rs.next()) {
                String fn = rs.getString("first_name");
                String ln = rs.getString("last_name");
                int id = rs.getInt("id");

                String user = (fn+" "+ln);
                userDictionary.put(user,id);
                result.add(user);
            }
            return result;
        }catch (Exception e){
            e.printStackTrace();
        }
        finally {
            util.closeDbCon();
        }
        result = null;
        return result;
    }
    public ArrayList<MenuItem> listToMenuItems(ArrayList<String> users){
        ArrayList<MenuItem> result = new ArrayList<>();
        for (int user = 0; user < users.size(); user++) {
            MenuItem mi = new MenuItem();
            mi.setText(users.get(user));
            result.add(mi);
        }
        return result;
    }
    public boolean addNewUser(String[] userInfo){
        Utilities util = new Utilities();
        boolean result;
        String fn = userInfo[0];
        String ln = userInfo[1];

        try {

            String statement = ("INSERT INTO user" +
                    " (first_name, last_name) " +
                    "VALUES" + " (" + "'" + fn + "'" + "," + "'" + ln + "'" + ")");

            boolean inserted = util.insertRecord(statement);
            if (inserted) {
                result = true;
            } else {
                result= false;
            }
            return result;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            util.closeDbCon();
        }
        return true;
    }
    public int getUserId(String key){ return this.userDictionary.get(key); }
}
