import Database.Connect;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.io.File;

public class OlympicGames {
    public static void main(String[] args) throws SQLException {
        // Class Instances
        Sports mySports = new Sports();
        Participants players = new Participants();
        Connect database = new Connect();

        try {
            database.startConn();
            Statement stmt = database.getConn().createStatement();
            String query = "INSERT INTO countries" + "(ID, countryName, countryAbbrev) VALUES" + "(?, ?, ?);"; // SQL Query
            PreparedStatement pstmt = database.getConn().prepareStatement(query); // Prepared Statement in order to pass values through SQL statements

            Scanner sc = new Scanner(new File("C:\\Olympic-Games-Management\\src\\csvFiles\\countries.csv")); // Opening csv file
            sc.useDelimiter(",|\\n"); // Delimiter skips comma and new line


            //////////////////////////////////////
            // Variables required to read in from csv file to pass through the query
            int countryID = 0;
            String countryName = "";
            String countryAbbrev = "";
            //////////////////////////////////////

            while (sc.hasNext()) {
                countryID = sc.nextInt();
                countryName = sc.next();
                countryAbbrev = sc.next();

                pstmt.setInt(1, countryID);
                pstmt.setString(2, countryName);
                pstmt.setString(3, countryAbbrev);
                pstmt.executeUpdate();
                System.out.println("ID: " + countryID + " CountryName: " + countryName + " CountryAbbrev: " + countryAbbrev);
            }
            System.out.println("All values successfully inserted into the table.");
        }
        catch (Exception ex)
        {
            System.out.println("ERROR: " + ex.getMessage());
        }

        database.endConn();
    }
}
