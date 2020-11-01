import java.io.*;
import java.sql.*;
import java.util.Scanner;
import Database.Connect;

public class Countries {
    private int[] countryID;
    private String[] countryName;
    private String[] countryAbbreviations;
    private String[] participatingCountries;
    protected final static int TOTAL_COUNTRIES = 193;
    Connect database;

    Countries(){
        init();
    }

    public void init(){
        countryID = new int[TOTAL_COUNTRIES];
        countryName = new String[TOTAL_COUNTRIES];
        countryAbbreviations = new String[TOTAL_COUNTRIES];
        database = new Connect();
    }

    ///////////////////////////////////////////////////////
    // Accessor Functions
    public int getCountryID(String n){
        int id = 0;
        try {
            database.startConn();
            String sql = "SELECT * FROM olympics.countries WHERE cName = ?";
            PreparedStatement pstmt = database.getConn().prepareStatement(sql);
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

        database.endConn();
        return id;
    } // Obtaining the ID of a country name you pass through

    public String getCountries(int i){
        String name = "";
        String ab = "";
        try {
            database.startConn();
            String sql = "SELECT * FROM olympics.countries WHERE ID = ?";
            PreparedStatement pstmt = database.getConn().prepareStatement(sql);
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

        database.endConn();
        return name + ab;
    } // Obtaining the name of the country by passing the ID

    public void outputTable(){
        try {
            database.startConn();
            Statement stmt = database.getConn().createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM countries");
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
        database.endConn();
    }

    /////////////////////////////////////////////////////
    // Mutator Functions
    public void setAllValues() {
        try {
            database.startConn();
            String query = "INSERT INTO countries" + "(ID, cName, cAbbrev) VALUES" + "(?, ?, ?);"; // SQL Query
            PreparedStatement pstmt = database.getConn().prepareStatement(query); // Prepared Statement in order to pass values through SQL statements

            Scanner sc = new Scanner(new File("C:\\Users\\Dopam\\IdeaProjects\\Olympic-Games-Management\\src\\csvFiles\\countries.csv")); // Opening csv file
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
                System.out.println("ID: " + countryID + " Name: " + countryName + " Abbrev: " + countryAbbrev);
            }
            System.out.println("All values successfully inserted into the table.");
        }
        catch (Exception ex)
        {
            System.out.println("ERROR: " + ex.getMessage());
            database.endConn();
        }

        database.endConn();
    } // WARNING! DO NOT RUN THIS FUNCTION IF ALL COUNTRIES ARE ALREADY POPULATED IN THE TABLE

    public void setCountryID(int id, int index){
        countryID[index] = id;
    } // WIP

    public void setCountryName(String name, int index){
        countryName[index] = name;
    } // WIP

    public void setCountryAbbreviations(String n, int index){
        countryAbbreviations[index] = n;
    } // WIP

}

