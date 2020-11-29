import Database.Connect;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.io.*;
import java.sql.*;
import java.util.Scanner;
import Database.Connect;

public class MedalsWon {
    private int totalMedals;
    private int totalGoldMedals;
    private int totalSilverMedals;
    private int totalBronzeMedals;
    
    //instances
    Countries country = new Countries();
    
    public void displayLeaderBoard(Connect conn) {
    	String countryName = new String();
        try {
            Statement stmt = conn.getConn().createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM olympics.leaderboards");
            System.out.printf("%-40s%-10s%-10s%-10s%-10s\n", "Country", "Gold", "Silver", "Bronze", "Total Medals"); //header

            while (rs.next()) {
            	int id = rs.getInt("countryID");
            	try {
            		countryName = country.getCountries(id, conn);
            	} catch (Exception e) {
            		System.out.println("SQL exception occured: " + e);
            	}
                totalGoldMedals= rs.getInt("gold");
                totalSilverMedals = rs.getInt("silver");
                totalBronzeMedals = rs.getInt("bronze");
                totalMedals = totalGoldMedals + totalSilverMedals + totalBronzeMedals;
                	 System.out.printf("%-40s%-10s%-10s%-10s%-10s\n", countryName, totalGoldMedals, totalSilverMedals, totalBronzeMedals, totalMedals);
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
    
    //insert values to database from csv file
    public void setValues(Connect conn) throws IOException{
    	try {
    		String query = "INSERT INTO olympics.leaderboards" + "(countryID, gold, silver, bronze)" + "VALUES (?, ?, ?, ?)";
    		PreparedStatement pstmt = conn.getConn().prepareStatement(query);
    		Scanner sc = new Scanner(new File("C:\\Olympic-Games-Management\\src\\csvFiles\\medal.csv"));
    		sc.useDelimiter("[,\\n\\r]");

    		//members
    		int countryID;
    		int gold;
    		int silver;
    		int bronze;
    		
    		while (sc.hasNext()) {
    			countryID = sc.nextInt();
    			gold = sc.nextInt();
    			silver = sc.nextInt();
    			bronze = sc.nextInt();
    			sc.nextLine();
    			//insert into database
    			pstmt.setInt(1, countryID);
    			pstmt.setInt(2, gold);
    			pstmt.setInt(3, silver);
    			pstmt.setInt(4, bronze);
    			pstmt.executeUpdate();
    			System.out.println("ID:" + countryID + "   Gold:" + gold + "    Silver:" + silver + "    Bronze:" + bronze);
    			System.out.println("Success!");
    		}
    	} catch (Exception e) {
    		System.out.println("UNABLE TO SET VALUES: " + e.getMessage() + "   " + e);
    	}
    }
}