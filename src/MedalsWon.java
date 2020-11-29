import Database.Connect;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MedalsWon {
	
	private int totalMedals;
    private int totalGoldMedals;
    private int totalSilverMedals;
    private int totalBronzeMedals;

    public void displayLeaderBoard(){
    	
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

//Grab top 3 scores from leader board and distribute medals accordingly
    public void updateMedals(Connect conn, Tournament tourn, int tourn_id){
    	
    	//getting highest scores from the tournament
    	try {
    		//database location and setInt need adjusting according to playTournament
    		String query = "SELECT TOP 3 olympics.locationTBD WHERE tournament_id = ?";
    		PreparedStatement pstmt = conn.getConn().prepareStatement(query);
            //pstmt.setInt(1, tournamentID);
            ResultSet rs = pstmt.executeQuery();
            	int gold = rs.getInt(1);
            	int silver = rs.getInt(2);
            	int bronze = rs.getInt(3);
            	totalGoldMedals++;
            	totalSilverMedals++;
            	totalBronzeMedals++;
    	} catch (Exception ex) {
    		System.out.println("ERROR: " + ex.getMessage());
    	}
    	
    	//inserting the values into leaderboard
    	try {	
    		String query = "INSERT INTO olympics.leaderboard" + "(gold, silver, bronze) VALUES" + "(?, ?, ?);";
    		PreparedStatement pstmt = conn.getConn().prepareStatement(query);

    		pstmt.setInt(1, totalGoldMedals);
    		pstmt.setInt(2, totalSilverMedals);
    		pstmt.setInt(3, totalBronzeMedals);
    		pstmt.executeUpdate();
		
    	} catch (Exception ex) {
    		System.out.println("ERROR: " + ex.getMessage());
    	}
    	
    	
    }
    
}
