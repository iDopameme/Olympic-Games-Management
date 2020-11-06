//////////////////////////////////////////////////////////////////////////////////////////
// List of sports available in the Olympics
//////////////////////////////////////////////////////////////////////////////////////////
import Database.Connect;
import com.mysql.cj.protocol.Resultset;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

public class Sports{
    Connect database;
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

    public void outputAllSports(){
        try {
            database.startConn();
            Statement stmt = database.getConn().createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM olympics.sports");
            System.out.println("ID     Sport     Type");

            while (rs.next()) {
                int id = rs.getInt("sportID");
                String sport = rs.getString("sName");
                String type = rs.getString("sportType");
                System.out.println(id + "     " + sport + "     " + type);
            }
        } catch(SQLException e) {
            System.out.println("SQL exception occurred" + e);
        }
        database.endConn();
    }

    public String selectSport(int pick) {
        String name = null;
        try {
            database.startConn();
            String sql = "SELECT sName FROM olympics.sports WHERE sportID = ?";
            PreparedStatement pstmt = database.getConn().prepareStatement(sql);
            pstmt.setInt(1, pick);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                name = rs.getString("sName");
            }
        } catch (SQLException e) {
            System.out.println("SQL exception occurred" + e);
        }
        database.endConn();
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
