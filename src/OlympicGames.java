import Database.Connect;
import java.beans.XMLEncoder;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class OlympicGames {
    // Private variables
    private boolean menuActive = true;
    private int menuChoice = 0;


    public static void main(String[] args) throws IOException {
        // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        // Class Instances
        OlympicGames games = new OlympicGames();
        Countries country = new Countries();
        Tournament tournaments;
        ArrayList<Tournament> game = new ArrayList<>();
        Participants players = new Participants();
        Scanner input = new Scanner(System.in);
        MedalsWon medal = new MedalsWon();
        Sports sport = new Sports();
        Connect database = new Connect();
        // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

        //database.startConn();
        games.printMenu();
        while (games.getMenuActive() == true) {
            System.out.print("Please select a menu command: ");
            int userInput = input.nextInt();
            System.out.println();
            switch (userInput) {
                case 1:
                	tournaments = new Tournament();
                	tournaments.createTournament();
                	game.add(tournaments);
                	games.displayTournaments(game);
                    break;
                case 2:
                		games.displayTournaments(game);
                    	System.out.println("Which tournament do you want to modify? (Type the sport)");
                    	String userInput2 = input.next().toUpperCase();
                    	for(Tournament g : game) {
                    		if(g.getUserSport().equals(userInput2)) {
                    			g.modifyTournament();
                    			g.tournamentDetails();
                    		}
                    	}
                    break;
                case 3:
                    //@TODO Complete tournament functions and games database
            			games.displayTournaments(game);
                    	System.out.println("Which tournament do you want to delete? (Type the sport)");
                    	String userInput3 = input.next().toUpperCase();
                    	game.removeIf(g-> g.getUserSport().equals(userInput3));
                		games.displayTournaments(game);
                	break;
                case 4:
                    sport.outputAllSports();
                    break;
                case 5:
                    players.outputTable();
                    break;
                case 6:
                    country.outputTable();
                    break;
                case 7:
                    medal.displayLeaderBoard();
                    break;
                case 8:
                    //@TODO Add a output gameResults function to Tournament.java
                case 9:
                    games.printMenu();
                    break;
                case 0:
                	database.removeCredentials();
					database.endConn();
                    games.setMenuActive(false);
                    break;
            }
        }
    }

	public void printMenu() {
        System.out.println("****************************************************");
        System.out.println("************* Olympic Game Management **************");
        System.out.println("+++ 1. Create Tournament"); // 60% Completed
        System.out.println("+++ 2. Modify Tournament"); // 40% Completed
        System.out.println("+++ 3. Delete Tournament"); // 10% Completed
        System.out.println("+++ 4. View list of sports"); // Completed
        System.out.println("+++ 5. View/Edit list of participants"); // 80% completed
        System.out.println("+++ 6. View all countries"); // 95% completed
        System.out.println("+++ 7. View medal leaderboard"); // work in progress
        System.out.println("+++ 8. View tournament results"); // work in progress
        System.out.println("+++ 9. Print Main Menu again");
        System.out.println("+++ 0. Quit Olympic Game Management"); // Completed
        System.out.println("****************************************************");
        System.out.println("****************************************************");
    }
	
	public void displayTournaments(ArrayList<Tournament> game) {
		if(game == null) {
			System.out.println("There are no tournaments currently...");
		}
		else {
			for(Tournament g : game) { 
				g.tournamentDetails();
    		}
		}

	}

    public boolean getMenuActive() {
        return this.menuActive;
    }

    public void setMenuActive(boolean b) {
        this.menuActive = b;
    }
}
