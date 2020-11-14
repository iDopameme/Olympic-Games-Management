import java.io.*;
import java.sql.*;
import java.util.Scanner;
import Database.Connect;

public class Countries {
    protected final static int TOTAL_COUNTRIES = 193;


    ///////////////////////////////////////////////////////
    // Accessor Functions
    public int getCountryID(String n, Connect conn) throws IOException {
        int id = 0;
        try {
            String sql = "SELECT * FROM olympics.countries WHERE cName = ?";
            PreparedStatement pstmt = conn.getConn().prepareStatement(sql);
            pstmt.setString(1, n);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                id = rs.getInt("ID");
//                String name = rs.getString("cName");
//                String abbrev = rs.getString("cAbbrev");
                System.out.println(n + "'s ID is: " + id); // TEMPORARY
            }
            rs.close();
        } catch (SQLException e) {
            System.out.println("SQL exception occurred" + e);
        }

        return id;
    } // Obtaining the ID of a country name you pass through

    public String getCountries(int i, Connect conn) throws IOException {
        String name = null;
        String ab = null;
        try {
            String sql = "SELECT * FROM olympics.countries WHERE ID = ?";
            PreparedStatement pstmt = conn.getConn().prepareStatement(sql);
            pstmt.setInt(1, i);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                name = rs.getString("cName");
                ab = rs.getString("cAbbrev");

                System.out.println("The country with ID " + i + " is: " + name +", " + ab); // TEMPORARY
            }
            rs.close();
        } catch (SQLException e) {
            System.out.println("SQL exception occurred" + e);
        }

        return name + ab;
    } // Obtaining the name of the country by passing the ID

    public void outputTable(Connect conn) throws IOException {
        try {
            Statement stmt = conn.getConn().createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM olympics.countries");
            System.out.println("ID     Name           Abbreviation");

            while (rs.next()) {
                int id = rs.getInt("ID");
                String name = rs.getString("cName");
                String abbrev = rs.getString("cAbbrev");
                System.out.println(id + "     " + name + "     " + abbrev);
            }
        } catch(SQLException e) {
            System.out.println("SQL exception occurred" + e);
        }
    }

    /////////////////////////////////////////////////////
    // Mutator Functions
    public void setAllValues(Connect conn) throws IOException {
        try {
            String query = "INSERT INTO olympics.countries" + "(ID, cName, cAbbrev) VALUES" + "(?, ?, ?);"; // SQL Query
            PreparedStatement pstmt = conn.getConn().prepareStatement(query); // Prepared Statement in order to pass values through SQL statements

            Scanner sc = new Scanner(new File("C:\\Olympic-Games-Management\\src\\csvFiles\\countries.csv")); // Opening csv file
            sc.useDelimiter("[,\\n]"); // Delimiter skips comma and new line


            //////////////////////////////////////
            // Variables required to read in from csv file to pass through the query
            int countryID;
            String countryName;
            String countryAbbrev;
            //////////////////////////////////////

            while (sc.hasNext()) {
                countryID = sc.nextInt();
                countryName = sc.next();
                countryAbbrev = sc.next();

                pstmt.setInt(1, countryID);
                pstmt.setString(2, countryName);
                pstmt.setString(3, countryAbbrev);
                pstmt.executeUpdate();
                System.out.println("ID: " + countryID + " Name: " + countryName + " Abbrev: " + countryAbbrev);
            }
            System.out.println("All values successfully inserted into the table.");
        }
        catch (Exception ex)
        {
            System.out.println("ERROR: " + ex.getMessage());
        }

    } // This function will not run if data already exists in the table

    public void removeAllValues(Connect conn) throws IOException {
        try {
            conn.startConn();
            Statement stmt = conn.getConn().createStatement();
            String sql = "DELETE FROM olympics.countries";
            stmt.executeUpdate(sql);
            System.out.println("Countries table is now deleted");

        } catch(SQLException | IOException e) {
            System.out.println("SQL exception occurred" + e);
        }
    } //
}

