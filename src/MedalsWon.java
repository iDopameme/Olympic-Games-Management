import Database.Connect;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MedalsWon {
    private int totalMedals;
    private int totalGoldMedals;
    private int totalSilverMedals;
    private int totalBronzeMedals;


    public void displayLeaderBoard(Connect conn) throws IOException {
        try {
            Statement stmt = conn.getConn().createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM olympics.leaderboards");
            System.out.println("Gold | Silver | Bronze");

            while (rs.next()) {
                int Gold = rs.getInt("gold");
                int Silver = rs.getInt("silver");
                int Bronze = rs.getInt("bronze");
                System.out.println(Gold + " | " + Silver + " | " + Bronze);
            }
        } catch(SQLException e) {
            System.out.println("SQL exception occurred" + e);
        }
    }


    public void updateMedals(){

    }
}
