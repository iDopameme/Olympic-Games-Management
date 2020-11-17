import Database.Connect;

import java.io.File;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

public class Tournament {
	//constants
	private final int MAX_TEAMS = 32;

	//private members
	private String userSport;
	private String type;
	private int numOfTeams;

	//instances
	Sports sport;
	Tournament game;
	String[] countries;
	String[] playerNames;
	Countries country;
	Time time;
	Timestamp timestamp;
	Participants players;
	Team team;
	List<Team> teams = new ArrayList<>();
	Team gold;
	Team silver;
	Team bronze;

	public void createTournament(Connect conn) throws IOException {
		//new instances
		userSport = "";
		sport = new Sports();
		game = new Tournament();
		countries = new String[MAX_TEAMS];
		playerNames = new String[MAX_TEAMS];
		country = new Countries();
		time = new Time();
		players = new Participants();
		Scanner input = new Scanner(System.in);

		//menu start
		System.out.println("****************************************************");
		System.out.println("************* Creating Tournament Menu *************");

		//setting sports
		sport.outputAllSports(conn);
		System.out.println("+++ What sport will be played in the tournament?");
		int countryChoice = input.nextInt();
		userSport = sport.selectSport(countryChoice, conn);
		type = sport.getSportType(countryChoice, conn);
		System.out.println(userSport + " " + type);
		System.out.println("How many teams will be in this tournament? (Max 32)");
		int numOfTeams = input.nextInt();
		this.numOfTeams = numOfTeams;
		if (numOfTeams > MAX_TEAMS) {
			System.out.println("Error! Team limit was exceeded. Tournament creation terminated...");
			System.exit(1);
		}
		timestamp = time.setTime(); //setting time


		//setting participants based on sport type (WIP)
		if (type.equals("Team")) {
			country.participatingCountries(conn);
			System.out.println("+++ What countries are participating? [Select by ID]\n(Select " + numOfTeams + " amount of teams)");
			for (int i = 0; i < numOfTeams; i++) {
				int playersInput = input.nextInt();
				countries[i] = country.getCountries(playersInput, conn);
			}
			
			//setting teams
			for(int i = 0; i < numOfTeams; i++) {
					team = new Team(countries[i]);
					team.newTeam(countries[i], team);
					teams.add(team);
				}
			//end creating tournament
			addNewTournament(conn, numOfTeams, type, timestamp, userSport, countries);
			System.out.println("+++ TOURNAMENT SUCCESSFULLY CREATED! +++");
		} else if (type.equals("Individual")) {
			players.listParticipants(type, conn);
			System.out.println("+++ Who is participating?");
			for (int i = 0; i < numOfTeams; i++)
			{
				int playersInput = input.nextInt();
				playerNames[i] = players.getPlayerName(playersInput, conn);
			}
			addNewTournament(conn, numOfTeams, type, timestamp, userSport, playerNames);
			System.out.println("+++ TOURNAMENT SUCCESSFULLY CREATED! +++");
		}

	}

	public void modifyTournament(Connect conn) {
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
				if(teams.size() == numOfTeams) {
					System.out.println("Cannot add team: Maxed sized reached! please delete a team first to add another team.");
				}
				else {
					country.participatingCountries(conn);
					System.out.println("Which team do you want to add?");	
					int playersInput = input.nextInt();
					String c;
					try {
						c = country.getCountries(playersInput, conn);
						team = new Team(c);
						team.newTeam(c, team);
						teams.add(team);
					} catch (IOException e) {
						System.out.println("Could not add team.");
					}
				}

			}
			else {
				System.out.println("Which participant do you want to delete?");
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
				System.out.println("Which participant do you want to delete?");
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
    	else if(type.equals("Individual")) { //for head to head sports
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

    public void playTournament(){

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


    public boolean addNewTournament(Connect conn, int numOfteams, String sportType, Timestamp gameTime, String sportName, String[] listOfTeams) {
		boolean validation = false;
		int gameID = 0;
		try {
			String query = "INSERT INTO olympics.games" + "(type, time, sport, numOfTeams) VALUES" + "(?, ?, ?, ?);"; // SQL Query
			PreparedStatement pstmt = conn.getConn().prepareStatement(query); // Prepared Statement in order to pass values through SQL statements

			pstmt.setString(1, sportType);
			pstmt.setTimestamp(2, gameTime);
			pstmt.setString(3, sportName);
			pstmt.setInt(4, numOfteams);
			pstmt.executeUpdate();

			System.out.println("All values successfully inserted into the table.");
			validation = true;

			String sql = "SELECT tournamentID FROM olympics.games WHERE time = ?";
			pstmt = conn.getConn().prepareStatement(sql);
			pstmt.setTimestamp(1, gameTime);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				gameID = rs.getInt("tournamentID");
			}
			rs.close();
		}
		catch (Exception ex) {
			System.out.println("ERROR: " + ex.getMessage());
		}

		try {
			String query = "INSERT INTO olympics.countryInGames" + "(foreignTourID, cName) VALUES" + "(?, ?);"; // SQL Query
			PreparedStatement pstmt = conn.getConn().prepareStatement(query); // Prepared Statement in order to pass values through SQL statements

			for (int i = 0; i < listOfTeams.length; i++) {
				pstmt.setInt(1, gameID);
				pstmt.setString(2, listOfTeams[i]);
			}
		}
		catch (Exception ex) {
			System.out.println("ERROR: " + ex.getMessage());
			validation = false;
		}

		return validation;
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
