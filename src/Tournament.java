import Database.Connect;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class Tournament {
	//constants
	private final int MAX_TEAMS = 3;
	
	//private members
	private String userSport;
	private String type;
	
	//instances
	Sports sport;
	Tournament game;
	String[] countries;
	Countries country;
	Time time;
	Team[] team;

	public void createTournament() {
		//new instances
		userSport = new String();
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
		userSport = sport.selectSport(c);
		type = sport.getSportType(c);
		System.out.println(userSport + " " + type);
		
		//setting time
		time.startTime();
		time.endTime();
		
		//setting participants based on sport type (WIP)
		if(type.equals("Team")) {
			System.out.println("+++ What countries are participating?");
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
			}
			
			//end creating tournament
			System.out.println("+++ TOURNAMENT SUCCESSFULLY CREATED! +++");
		}
		else {
			System.out.println("+++ Who is participating?");
			//end creating tournament
			System.out.println("+++ TOURNAMENT SUCCESSFULLY CREATED! +++");
		}


	} // Still a work in progress did not finish yet | 11/10 1:55AM
	
	public void modifyTournament() {
		Scanner input = new Scanner(System.in);
		System.out.println("What do you want to modify?");
		System.out.println("+++ 1. Date and time");
		System.out.println("+++ 2. Delete Teams or Participants");
		int userInput3 = input.nextInt();
		switch(userInput3) {
		case 1:
			time.startTime();
			time.endTime();
			game.updatedTournament();
			break;
		case 2:
			if(type.equals("Team")) {
				System.out.println("Which team do you want to delete?");	
				String userInput = input.next();
            	for(int i = 0; i < game.MAX_TEAMS; i++) {
    				if(userInput.toUpperCase().equals(team[i].getTeamName().toUpperCase())) {
    					team[i]=null;
    				}
            	}
			}
			else {
				System.out.println("Which particiapant do you want to delete?");
			}
			game.updatedTournament();
			break;
		}
	}

	
	public void deleteTournament() {
		
	}
	
    public void tournamentDetails(){ //for team sports
    	System.out.println("\n==============");
    	System.out.println(userSport.toUpperCase() + "\nTOURNAMENT DETAILS");
    	System.out.println("==============");
    	for (Team t : team) {
    		if(t != null) {
    			t.teamList();
    		}
    	}
    	System.out.println(); 
    	System.out.println(time);
    	System.out.println();    	
    }
    
    public void tournamentDetails(String sport, Participants players) { //for head to head sports
    	System.out.println("\n==============");
    	System.out.println(userSport.toUpperCase() + "\nTOURNAMENT DETAILS");
    	System.out.println("==============");
    	players.listParticipants();
    	System.out.println(); 
    	System.out.println(time);
    	System.out.println(); 
    }
    
    public void updatedTournament() {
		System.out.println();
		System.out.println("==========================");
		System.out.println("UPDATED TOURNAMENT DETAILS");
		System.out.println("==========================");
    }
    
    public void playTournament(){

    }
    
    public void results(){
    	
    }

	public String getUserSport() {
		return userSport.toUpperCase();
	}

	public Time getTime() {
		return time;
	}

	public void setTime(Time time) {
		this.time = time;
	}    
	
    
}
