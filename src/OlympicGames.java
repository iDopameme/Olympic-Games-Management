import Database.Connect;

import java.sql.SQLException;
import java.sql.Statement;

public class OlympicGames {
    public static void main(String[] args) throws SQLException {
        // Class Instances
        Sports mySports = new Sports();
        Participants players = new Participants();
        Connect database = new Connect();

        // Testing Sports.java
//        System.out.println("----------------------TESTING SPORTS.JAVA---------------");
//        mySports.setSports("Football");
//        mySports.setSports("Volleyball");
//        mySports.setSports("Tennis");
//        mySports.setSports("Athletics");
//        mySports.setSports("Swimming");

        try {
            database.startConn();
            Statement stmt = database.getConn().createStatement();

            String sql = "INSERT INTO countries VALUES" + "('101', 'Downtown Cafe', 'NY')"; // Testing the sql query statement (SUCCESS)
            stmt.execute(sql);
        }
        catch (Exception ex)
        {
            System.out.println("ERROR: " + ex.getMessage());
        }
    }
}
