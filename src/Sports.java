//////////////////////////////////////////////////////////////////////////////////////////
// List of sports available in the Olympics
//////////////////////////////////////////////////////////////////////////////////////////
import Database.Connect;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

public class Sports{
    Scanner input = new Scanner(System.in);

    // Constants
    private final static int SPORTS_CAPACITY = 10;
    // Private & Protected Variables
    protected int[] sportID;
    private String[] sports;
    private int idCount;
    private ArrayList<String> sportsList; // ArrayList



    // Default Constructor -- Default Sports
    Sports(){
        init();
    }

    public void init(){
        sports = new String[SPORTS_CAPACITY];
        sportID = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        idCount = 0;
        sportsList = new ArrayList<String>(); // ArrayList
    }

    //////////////////////////////////////
    // Accessor Functions
    public int getSportID(Connect conn, String s) {
        int id = 0;
        try {
            String sql = "SELECT sportID FROM olympics.sports WHERE sName = ?";
            PreparedStatement pstmt = conn.getConn().prepareStatement(sql);
            pstmt.setString(1, s);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                id = rs.getInt("sportID");
                System.out.println(s + "'s ID is: " + id); // TEMPORARY
            }

            rs.close();
        } catch (SQLException e) {
            System.out.println("SQL exception occurred" + e);
        }
        return id;
    }

    public String getSportType(int id, Connect conn) {
        String type = null;
        try {
            String sql = "SELECT sportType FROM olympics.sports WHERE sportID = ?";
            PreparedStatement pstmt = conn.getConn().prepareStatement(sql);
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                type = rs.getString("sportType");
                System.out.println(id + "'s type is: " + type);
            }
            rs.close();
        } catch (SQLException e) {
            System.out.println("SQL exception occurred" + e);
        }
        return type;
    }


    public String getSportName(int id){
        return sports[id];
    }

    public void outputAllSports(Connect conn) throws IOException {
        try {
            Statement stmt = conn.getConn().createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM olympics.sports");
            System.out.printf("%-7s%-15s%-15s\n", "ID", "Sport", "Type");

            while (rs.next()) {
                int id = rs.getInt("sportID");
                String sport = rs.getString("sName");
                String type = rs.getString("sportType");
                System.out.printf("%-7d%-15s%-15s\n", id, sport, type);
            }
        } catch(SQLException e) {
            System.out.println("SQL exception occurred" + e);
        }
    }

    public String selectSport(int pick, Connect conn) throws IOException {
        String name = null;
        try {
            String sql = "SELECT sName FROM olympics.sports WHERE sportID = ?";
            PreparedStatement pstmt = conn.getConn().prepareStatement(sql);
            pstmt.setInt(1, pick);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                name = rs.getString("sName");
            }
        } catch (SQLException e) {
            System.out.println("SQL exception occurred" + e);
        }
        return name;
    }
    

    
    public void outputArrayList(){
        System.out.println(sportsList);
    }


    public boolean linkPlayerSport(String s, String player){ // Links what sport every player participates in...
        boolean bool = false;
        for(int i = 0; i < SPORTS_CAPACITY; i++)
            if(s == sports[i]) {
                //@TODO add the player to an arrayList of the sports
                bool = true;
            } else {
                bool = false;
            }
        return bool;
    }

    //////////////////////////////////////
    // Experimental ideas

    public void teamSport(){ // This class handles team sports which will have multiple players

    }

    public void headToHeadSport(){ // This class handles head to head sports which contains just 2 players

    }

    public static void main(String[] args) {

    }
}
