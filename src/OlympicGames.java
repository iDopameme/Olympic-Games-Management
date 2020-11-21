import Database.Connect;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class OlympicGames {
    // Private variables
    private static boolean validation = false;
    private boolean menuActive = true;


    public static void main(String[] args) throws IOException {
        // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        // Class Instances
        OlympicGames games = new OlympicGames();
        Countries country = new Countries();
        Tournament tournaments = new Tournament();
        ArrayList<Tournament> game = new ArrayList<>();
        Participants players = new Participants();
        Scanner input = new Scanner(System.in);
        MedalsWon medal = new MedalsWon();
        Sports sport = new Sports();
        Connect database = new Connect();
        // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

        validation = database.startConn(); // Checks if the user login was successful.

        games.printMenu(); // Prints the menu before while loop
        while ((games.getMenuActive()) && (validation)) {
            System.out.print("Please select a menu command: ");
            int userInput = input.nextInt(); // User input for menu
            System.out.println();
            //@TODO Complete tournament functions and games database
            //@TODO Add a output gameResults function to Tournament.java
            switch (userInput) {
                case 1 -> {
                    tournaments.createTournament(database);
                }
                case 2 -> {
                    tournaments.viewTournamentTable(database);
                    System.out.println("Which tournament do you want to modify? (Type the name)");
                    String modify = input.next();
                    tournaments.viewTournament(database, modify);
                    tournaments.modifyTournament(database, modify);
                }
                case 3 -> {
                    tournaments.viewTournamentTable(database);
                    System.out.println("Which tournament do you want to delete? (Type the ID)");
                    int delete_id = input.nextInt();
                    tournaments.deleteTournament(database, delete_id);
                }
                case 4 -> sport.outputAllSports(database);
                case 5 -> players.listParticipants(database);
                case 6 -> country.participatingCountries(database);
                case 7 -> medal.displayLeaderBoard(database);
                case 8 -> {
                	for(Tournament g :game) {
                		g.results();
                	}
                }
                case 9 -> games.printMenu();
                case 0 -> {
                    database.removeCredentials();
                    database.endConn();
                    games.setMenuActive(false);
                }
            }
        }
        if (!validation)
            System.out.println("Login attempt failed access denied...");

    }

	public void printMenu() {
        System.out.println("****************************************************");
        System.out.println("************* Olympic Game Management **************");
        System.out.println("+++ 1. Create Tournament"); // Completed
        System.out.println("+++ 2. View/Modify Tournament"); // 50% Completed
        System.out.println("+++ 3. Delete Tournament"); // Completed
        System.out.println("+++ 4. View list of sports"); // Completed
        System.out.println("+++ 5. View/Edit list of participants"); // 80% completed
        System.out.println("+++ 6. View all countries"); // 95% completed
        System.out.println("+++ 7. View medal leaderboard"); // work in progress
        System.out.println("+++ 8. Play and view tournament results"); // work in progress
        System.out.println("+++ 9. Print Main Menu again");
        System.out.println("+++ 0. Quit Olympic Game Management"); // Completed
        System.out.println("****************************************************");
        System.out.println("****************************************************");
    }
	
	public void displayTournaments(ArrayList<Tournament> game) {

	}

    public boolean getMenuActive() {
        return this.menuActive;
    }

    public void setMenuActive(boolean b) {
        this.menuActive = b;
    }

    public void setValidation(boolean v) {
        validation = v;
    }

    public boolean getValidation() {
        return validation;
    }
}
