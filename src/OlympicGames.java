import Database.Connect;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class OlympicGames {
    // Private variables
    private static boolean validation = false;
    private static boolean menuActive = true;
    private static String modify = null;
    private static String tourneyStatus = null;
    private static int menu2Input = 0;

    public static void main(String[] args) throws IOException {
        // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        // Class Instances
        Countries country = new Countries();
        Tournament tournaments = new Tournament();
        Participants players = new Participants();
        Scanner input = new Scanner(System.in);
        MedalsWon medal = new MedalsWon();
        Sports sport = new Sports();
        Connect database = new Connect();
        Team team = new Team();
        Time time = new Time();
        // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

        validation = database.startConn(); // Checks if the user login was successful.

        printMenu(); // Prints the menu before while loop
        while ((getMenuActive()) && (validation)) {
            System.out.print("Please select a menu command: ");
            int userInput = input.nextInt(); // User input for menu
            System.out.println();
            //@TODO Complete tournament functions and games database
            //@TODO Add a output gameResults function to Tournament.java
            switch (userInput) {
                case 1 -> tournaments.createTournament(database);
                case 2 -> {
                    tournaments.viewTournamentTable(database);
                    System.out.println("Which tournament do you want to view and/or modify? (Type the name)");
                    System.out.println("***Only Tournaments in [Pending] status can be modified!+++");
                    modify = input.next();

                    tourneyStatus = tournaments.getTournament_status(database, modify);

                    if (tourneyStatus.equals("Pending")) {
                        System.out.println("+++ 1. View Tournament");
                        System.out.println("+++ 2. Modify Tournament");
                        System.out.println("Please select to view or modify the tournament.");
                        menu2Input = input.nextInt();
                        switch (menu2Input) {
                            case 1 -> tournaments.viewTournament(database, modify);
                            case 2 -> tournaments.modifyTournament(database, modify);
                        }
                    } else
                        tournaments.viewTournament(database, modify);
                }
                case 3 -> {
                    boolean deleteValidation;
                    tournaments.viewTournamentTable(database);
                    System.out.println("Which tournament do you want to delete? (Type the ID)");
                    int delete_id = input.nextInt();

                    deleteValidation = tournaments.deleteTournament(database, delete_id);
                    if (deleteValidation) {
                        System.out.println("Tournament and all teams playing in tournament was successfully deleted.");
                    } else if (!deleteValidation) {
                        System.out.println("Tournament deletion failed! Please try again and check your inputs.");
                    }
                }
                case 4 -> sport.outputAllSports(database);
                case 5 -> players.listParticipants(database);
                case 6 -> country.participatingCountries(database);
                case 7 -> medal.displayLeaderBoard(database);
                case 8 -> {
//                	for(Tournament g :game) {
//                		g.results();
//                	}
                }
                case 9 -> printMenu();
                case 0 -> {
                    database.removeCredentials();
                    database.endConn();
                    setMenuActive(false);
                }
                case 11 -> {
                    System.out.println("DEBUG: ");
                    time.setTime();
                }
            }
        }
        if (!validation)
            System.out.println("Login attempt failed access denied...");

    }

	public static void printMenu() {
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

    public static boolean getMenuActive() {
        return menuActive;
    }

    public static void setMenuActive(boolean b) {
        menuActive = b;
    }
}
