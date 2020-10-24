public class OlympicGames {
    public static void main(String[] args) {


        //////////////////////////////////////////////
        // Testing Sports.java
        Sports mySport = new Sports(); // Declaring instance of Sports.java in OlympicGames
        mySport.outputAllSports();
//        System.out.println(mySport.getSportID("Football"));
        
        //////////////////////////////////////////////
        // Testing Tournament.java
        Tournament game = new Tournament();
        System.out.print("\n");
        game.tournamentDetails();
    }
}
