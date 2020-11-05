import Database.Connect;
import java.beans.XMLEncoder;
import java.util.Scanner;

public class OlympicGames {
	// Private variables
	private static String[] teams = {"USA", "JAPAN"};
	private static String[] teams2 = {"ITALY", "KOREA"};
	private boolean menuActive = true;
	private int menuChoice = 0;


    public static void main(String[] args) {
        // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        // Class Instances
        OlympicGames games = new OlympicGames();
        Countries country = new Countries();
        Tournament game = new Tournament();
        Sports sport = new Sports();
        Participants players = new Participants();
        Scanner input = new Scanner(System.in);
        MedalsWon medal = new MedalsWon();
        // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

        // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        // Local main variables

        
        while (games.getMenuActive() == true){

            System.out.println("****************************************************");
            System.out.println("************* Olympic Game Management **************");
            System.out.println("+++ 1. Create Tournament"); // work in progress
            System.out.println("+++ 2. Modify Tournament"); // work in progress
            System.out.println("+++ 3. Delete Tournament"); // work in progress
            System.out.println("+++ 4. View/Edit list of sports"); //
            System.out.println("+++ 5. View/Edit list of participants"); // 80% completed
            System.out.println("+++ 6. View all countries"); // 95% completed
            System.out.println("+++ 7. View medal leaderboard"); // work in progress
            System.out.println("+++ 0. Quit Olympic Game Management"); // Completed
            System.out.println("****************************************************");
            System.out.println("****************************************************");
            System.out.print("Please select a command: ");
            int userInput = input.nextInt();

            switch(userInput) {
                case 1:
                    game.main(new String[0]);
                    break;
                case 2:
                    sport.main(new String[2]);
                    break;
                case 3:


                case 4:
                    country.outputTable();
                    break;
                case 5:
                    medal.displayLeaderBoard();
                    break;
                case 6:
                    games.setMenuActive(false);
                    break;
            }

        }
        //country testing
        // System.out.println(country.getCountryID("UNITED STATES OF AMERICA"));
        
        //setting sports
//    	sport.setSports("Volleyball");
//    	sport.setSports("Swimming");
//    	System.out.println("============");
//    	//setting participants
//    	players.addParticipants("Mary", "Jane", 23, "PH");
//    	players.addParticipants("McRoy", "Roy", 23, "CH");
//
//    	//this is details teams
//        game.tournamentDetails(sport.getSportName(0), teams);
//
//    	//this is details head to head sports
//    	game.tournamentDetails(sport.getSportName(1), players);
//
//        players.listParticipants();

    }


    public boolean getMenuActive(){
        return menuActive;
    }

    public void setMenuActive(boolean b) {
        menuActive = b;
    }
}
