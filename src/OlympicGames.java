import Database.Connect;
import java.beans.XMLEncoder;
import java.util.Scanner;

public class OlympicGames {
	private final int MAX_TOURNAMENTS = 10;
	
    // Private variables
    private boolean menuActive = true;
    private int menuChoice = 0;
    private static int count = 0;


    public static void main(String[] args) {
        // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        // Class Instances
        OlympicGames games = new OlympicGames();
        Countries country = new Countries();
        Tournament[] game = new Tournament[games.getMAX_TOURNAMENTS()];
        Participants players = new Participants();
        Scanner input = new Scanner(System.in);
        MedalsWon medal = new MedalsWon();
        Sports sport = new Sports();
        Connect database = new Connect();
        // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

//        database.startConn();
        while (games.getMenuActive() == true) {
            games.printMenu();
            System.out.print("Please select a command: ");
            int userInput = input.nextInt();
            System.out.println();
            switch (userInput) {
                case 1:
                	if(count != games.getMAX_TOURNAMENTS()) {
                		game[count] = new Tournament();
                        game[count].createTournament();
                        game[count].tournamentDetails();
                        count++;
                	}
                	else {
                		System.out.println("Cannot create anymore tournaments.");
                	}
                    break;
                case 2:
                    //@TODO Fix Sports instance error
                    break;
                case 3:
                    //@TODO Complete tournament functions and games database
                	for(Tournament g : game) {
                		if(g != null) {
                    		g.tournamentDetails();
                		}
                	}
                	System.out.println("Which tournament do you want to delete?"); //WIP
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
                    games.setMenuActive(false);
                    break;
            }
        }
    }

    public void printMenu() {
        System.out.println("****************************************************");
        System.out.println("************* Olympic Game Management **************");
        System.out.println("+++ 1. Create Tournament"); // work in progress
        System.out.println("+++ 2. Modify Tournament"); // work in progress
        System.out.println("+++ 3. Delete Tournament"); // work in progress
        System.out.println("+++ 4. View list of sports"); //
        System.out.println("+++ 5. View/Edit list of participants"); // 80% completed
        System.out.println("+++ 6. View all countries"); // 95% completed
        System.out.println("+++ 7. View medal leaderboard"); // work in progress
        System.out.println("+++ 8. View tournament results"); // work in progress
        System.out.println("+++ 9. Print Main Menu again");
        System.out.println("+++ 0. Quit Olympic Game Management"); // Completed
        System.out.println("****************************************************");
        System.out.println("****************************************************");
    }

    public boolean getMenuActive() {
        return this.menuActive;
    }

    public void setMenuActive(boolean b) {
        this.menuActive = b;
    }

	public int getMAX_TOURNAMENTS() {
		return MAX_TOURNAMENTS;
	}

}
