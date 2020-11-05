//////////////////////////////////////////////////////////////////////////////////////////
// List of sports available in the Olympics
//////////////////////////////////////////////////////////////////////////////////////////
import Database.Connect;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Sports extends Tournament {
    Connect database;

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
        database = new Connect();
    }

    //////////////////////////////////////
    // Accessor Functions
    public int getSportID(String s) {
        int j = 0;
        for (int i = 0; i < 10; i++) {
            if (sports[i].equals(s)) {
                j = (i + 1);
            }
        }
        return j;
    }


    public String getSportName(int id){
        return sports[id];
    }


    //////////////////////////////////////
    // Mutator Functions
    public void setSports(String s , String t){
        try {
            database.startConn();
            String sql = "INSERT INTO olympics.sports" + "(sName, sportType) VALUES" + "(?, ?);";
            PreparedStatement pstmt = database.getConn().prepareStatement(sql);

            pstmt.setString(1, s);
            pstmt.setString(2, t);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("SQL exception occurred" + e);
        }
        database.endConn();
    } // FUNCTION NOT TESTED YET. FUNCTIONALITY NOT CERTAIN | 11/05 04:10 AM
    
    public boolean sportCheck (String s) {
        boolean check = false;
        String name = null;
        try {
            database.startConn();
            String sql = "SELECT sName FROM olympics.sports WHERE sName = ?";
            PreparedStatement pstmt = database.getConn().prepareStatement(sql);
            
            pstmt.setString(1, s);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                name = rs.getString("sName");
            }

            if (name == s) {
                check = true;
            }
            
        } catch (SQLException e) {
            System.out.println("SQL exeception occurred" + e);
        }
        database.endConn();
        return check;
    } // FUNCTION NOT TESTED YET. FUNCTIONALITY NOT CERTAIN | 11/05 04:10 AM

    public void outputAllSports(){
        for(int i = 0; i < 10; i++) {
            System.out.println("ID: " + i + " | SportName: " + sports[i]);
        }
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
