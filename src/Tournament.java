import Database.Connect;

import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

public class Tournament {
	//private members
	private String userSport;
	private String type;
	private boolean addTeam = true;
	private Team gold;
	private Team silver;
	private Team bronze;
	
	//instances
	Sports sport;
	Tournament game;
	String[] countries;
	Countries country;
	Time time;
	Team team;
	List<Team> teams = new ArrayList<>();

	public void createTournament(Connect conn) throws IOException {
		//new instances
		userSport = new String();
		sport = new Sports();
		game = new Tournament();
		country = new Countries();
		time = new Time();
		Scanner input = new Scanner(System.in);


		//menu start
		System.out.println("****************************************************");
		System.out.println("************* Creating Tournament Menu *************");

		//setting sports
		sport.outputAllSports(conn);
		System.out.println("+++ What sport will be played in the tournament?");
		int c = input.nextInt();
		userSport = sport.selectSport(c, conn);
		type = sport.getSportType(c, conn);
		System.out.println(userSport + " " + type);

		//setting time
		time.startTime();
		time.endTime();

		//setting participants based on sport type (WIP)
				if(type.equals("Team")) {
					System.out.println("+++ What countries are participating? Type 0 to finish");
					
					//setting teams
					while(addTeam == true) {
						String participatingCountry = new String();
						participatingCountry = input.next();
						if(participatingCountry.equals("0") == false && addTeam == true) {
							int userInput1 = country.getCountryID(participatingCountry, conn);
							team = new Team(country.getCountries(userInput1, conn));
							team.newTeam(country.getCountries(userInput1, conn), team);
							teams.add(team);
						}
						else { 
							addTeam = false; 
						}

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
	
	public void modifyTournament(Connect conn) throws IOException {
		Scanner input = new Scanner(System.in);
		System.out.println("What do you want to modify?");
		System.out.println("+++ 1. Date and time");
		System.out.println("+++ 2. Add Teams or Participants");
		System.out.println("+++ 3. Delete Teams or Participants");
		int userInput3 = input.nextInt();
		switch(userInput3) {
		case 1:
			time = new Time();
			time.startTime();
			time.endTime();
			game.updatedTournament();
			break;
		case 2:
			if(type.equals("Team")) {
				System.out.println("Which team do you want to add");	
				String userInput = input.next().toUpperCase();
				int countryID = country.getCountryID(userInput, conn);
				team = new Team(country.getCountries(countryID, conn));
				team.newTeam(country.getCountries(countryID, conn), team);
				teams.add(team);
			}
			else {
				System.out.println("Which particiapant do you want to delete?");
			}
			game.updatedTournament();
			break;
		case 3:
			if(type.equals("Team")) {
				System.out.println("Which team do you want to delete?");	
				String userInput = input.next().toUpperCase();
				teams.removeIf(t-> t.getTeamName().equals(userInput));
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
	
    public void tournamentDetails(){
    	System.out.println("\n==============");
    	System.out.println(userSport.toUpperCase() + "\nTOURNAMENT DETAILS");
    	System.out.println("==============");
    	if(type.equals("Team")) { //for team sports
	    	for (Team t : teams) {
	    			t.teamList();
	    	}
    	}
    	else { //for head to head sports
//        	players.listParticipants();
    	}
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
    
    public void results(){ //randomized result
    	if(type.equals("Team")){ //for teams
    		if(bronze == null && silver == null && gold == null) {
	    		Collections.shuffle(teams);
	    		bronze = teams.get(0);
	    		silver = teams.get(1);
	    		gold = teams.get(2);
	    		System.out.println("Tournament results for " + getUserSport() + " finished.");
	    		displayResults();
    		}
    		else {
    			System.out.println("Tournament for "+ getUserSport() + " has been played already!");
    			displayResults();
    		}
    	}
    	else { //for head to head sports
    		
    	}
    }

    public void displayResults() {
    	System.out.println("\n==========================");
		System.out.println(getUserSport() + "\nTOURNAMENT RESULTS");
		System.out.println("==========================");
		System.out.println("First place: " + gold.getTeamName());
		System.out.println("Second place: " + silver.getTeamName());
		System.out.println("Third place: " + bronze.getTeamName());
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
