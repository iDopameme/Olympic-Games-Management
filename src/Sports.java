//////////////////////////////////////////////////////////////////////////////////////////
// List of sports available in the Olympics
//////////////////////////////////////////////////////////////////////////////////////////
import Database.Connect;
import java.util.ArrayList;

public class Sports extends Tournament {
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
    public void setSports(String s){
        sports[idCount] = s;
        System.out.println(s + " is in array index " + idCount);
        idCount++;
        sportsList.add(s); // ArrayList
    }

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
