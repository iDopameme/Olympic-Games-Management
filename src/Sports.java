//////////////////////////////////////////////////////////////////////////////////////////
// List of sports available in the Olympics
// Currently working out the concept of linking Sports with the rest of the java classes
//////////////////////////////////////////////////////////////////////////////////////////

public class Sports {
    // Constants
    private final static int SPORTS_CAPACITY = 10;
    // Private & Protected Variables
    protected int[] sportID;
    private String[] sports;

    // Default Constructor -- Default Sports
    Sports(){
        // sports = new String[]{"Vollyeball", "Basketball", "Football", "Boxing", "Tennis", "Swimming", "Archery", "Judo", "Baseball", "Table Tennis"};
        sports = new String[SPORTS_CAPACITY];
        sportID = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
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
    public void setSports(){
        //unsure if this is necessary for this class
    }

    public void outputAllSports(){
        for(int i = 0; i < 10; i++) {
            System.out.println("ID: " + i + " | SportName: " + sports[i]);
        }
    }



}
