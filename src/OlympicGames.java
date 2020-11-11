import Database.Connect;
import java.beans.XMLEncoder;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class OlympicGames {
	private final int MAX_TOURNAMENTS = 2;
	
    // Private variables
    private boolean menuActive = true;
    private boolean deleteActive = true;
    private boolean tournamentIsNull = false;
    private int menuChoice = 0;


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
        games.printMenu();
        while (games.getMenuActive() == true) {
            System.out.print("Please select a menu command: ");
            int userInput = input.nextInt();
            System.out.println();
            switch (userInput) {
                case 1:
    				games.setTournamentIsNull(false);
                	for(int i = 0; i< games.getMAX_TOURNAMENTS(); i++) {
                		if(game[i] == null) {
                				games.setTournamentIsNull(true);
                				game[i] = new Tournament();
                                game[i].createTournament();
                                game[i].tournamentDetails();
                                i = 10;
                			}
                		}
            		if(games.isTournamentIsNull() == false) {
            			System.out.println("Cannot create anymore tournaments");
            		}
                    break;
                case 2:
                		games.displayTournaments(game);
                    	System.out.println("Which tournament do you want to modify?");
                    	String userInput2 = input.next();
                    	for(int i = 0; i < games.getMAX_TOURNAMENTS(); i++) {
                    		if(game[i] != null && game[i].getUserSport().equals(userInput2.toUpperCase())) {
                        			game[i].modifyTournament();
                        			game[i].tournamentDetails();
                    			}
                    		}
                    break;
                case 3:
                    //@TODO Complete tournament functions and games database
                	//ALSO WORK IN PROGRESS
            		games.displayTournaments(game);
                	while(games.isDeleteActive() == true) {
                    	System.out.println("Which tournament do you want to delete? \n Type 999 to go back to the menu."); //WIP
                    	String userInput4 = input.next();
                    	switch(userInput4.toUpperCase()) {
                    	case "FOOTBALL":
                    	case "VOLLEYBALL":
                    	case "TENNIS":
                    	case "BASKETBALL":
                    	case "FENCING":
                    	case "GOLF":
                    	case "BOXING":
                    	case "SWIMMING":
                    	case "BASEBALL":
                    	case "HANDBALL":
                    		for(int i = 0; i < games.getMAX_TOURNAMENTS(); i++) {
	                    		if(game[i] != null && game[i].getUserSport().equals(userInput4.toUpperCase())) {
	                    			game[i] = null;
	                    			}
                    		}
                		games.displayTournaments(game);
                    		break;
                    	case "999":            
                    		games.setDeleteActive(false); //temp code just coz it's not done
                    		break;	
                    	}
                	}
                	games.setDeleteActive(true);
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
	
	public void displayTournaments(Tournament[] game) {
		if(game == null) {
			System.out.println("There are no tournaments currently...");
		}
		else {
			for(Tournament g : game) { 
	    		if(g != null) {
	    			g.tournamentDetails();
	    			}
    		}
		}

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
	
    public boolean isDeleteActive() {
		return deleteActive;
	}

	public void setDeleteActive(boolean deleteActive) {
		this.deleteActive = deleteActive;
	}

	public boolean isTournamentIsNull() {
		return tournamentIsNull;
	}

	public void setTournamentIsNull(boolean tournamentIsNull) {
		this.tournamentIsNull = tournamentIsNull;
	}

}
