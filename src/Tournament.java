import Database.Connect;
import java.util.Scanner;

public class Tournament {
	//constants
	private final int MAX_TEAMS = 2;
	
	//private members
	String userInput;
	String type;
	
	//private instances
	private Sports sport;
	private Tournament game;
	private String[] countries;
	private Countries country;
	private Time time;
	private Team[] team;

	public void createTournament() {
		//new instances
		userInput = new String();
		sport = new Sports();
		game = new Tournament();
		countries = new String[MAX_TEAMS];
		country = new Countries();
		time = new Time();
		team = new Team[MAX_TEAMS];
		
		Scanner input = new Scanner(System.in);
		
		//menu start
		System.out.println("****************************************************");
		System.out.println("************* Creating Tournament Menu *************");
		
		//setting sports
		sport.outputAllSports();
		System.out.println("+++ What sport will be played in the tournament?");
		int c = input.nextInt();
		userInput = sport.selectSport(c);
		type = sport.getSportType(c);
		System.out.println(userInput + " " + type);
		
		//setting time
		time.startTime();
		time.endTime();
		
		//setting participants based on sport type (WIP)
		if(type.equals("Team")) {
			System.out.println("+++ What countries are participating? Type at most 2 countries");
			for(int i = 0; i < MAX_TEAMS; i++) {
				String participatingCountry = new String();
				participatingCountry = input.next();
				int userInput1 = country.getCountryID(participatingCountry);
				countries[i] = country.getCountries(userInput1);
			}
			
			//setting teams
			for(int i = 0; i < MAX_TEAMS; i++) {
				String temp = countries[i];
				team[i] = new Team(temp);
				team[i].newTeam(temp, team[i]);
				team[i].teamList();
			}
			
			//end creating tournament
			System.out.println("TOURNAMENT SUCCESSFULLY CREATED!");
		}
		else {
			System.out.println("+++ Who is participating?");
			//end creating tournament
			System.out.println("TOURNAMENT SUCCESSFULLY CREATED!");
		}


	} // Still a work in progress did not finish yet | 11/08 11:40AM
	
    public void tournamentDetails(){ //for team sports
    	System.out.println("\n==============");
    	System.out.println(userInput.toUpperCase() + "\nTOURNAMENT DETAILS");
    	System.out.println("==============");
    	for (Team t : team) {
    		t.teamList();
    	}
    	System.out.println(); 
    	System.out.println(time);
    	System.out.println();    	
    }
    
    public void tournamentDetails(String sport, Participants players) { //for head to head sports
    	System.out.println("\n==============");
    	System.out.println(sport.toUpperCase() + "\nTOURNAMENT DETAILS");
    	System.out.println("==============");
    	players.listParticipants();
    	System.out.println(); 
    	System.out.println(time);
    	System.out.println(); 
    }
    
    public void playTournament(){

    }
    
    public void results(){

    }
    public static void main(String[] args) {

	}
}
