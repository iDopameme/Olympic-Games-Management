import Database.Connect;

import java.io.File;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Scanner;

public class Tournament {
	//constants
	private final int MAX_TEAMS = 32;

	//private members
	private String userSport;
	private String type;

	//instances
	Sports sport;
	Tournament game;
	String[] countries;
	String[] playerNames;
	Countries country;
	Time time;
	Team[] team;
	Timestamp timestamp;
	Participants players;

	public void createTournament(Connect conn) throws IOException {
		//new instances
		userSport = "";
		sport = new Sports();
		game = new Tournament();
		countries = new String[MAX_TEAMS];
		playerNames = new String[MAX_TEAMS];
		country = new Countries();
		time = new Time();
		team = new Team[MAX_TEAMS];
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
		if (numOfTeams > MAX_TEAMS) {
			System.out.println("Error! Team limit was exceeded. Tournament creation terminated...");
			System.exit(1);
		}
		timestamp = time.setTime(); //setting time


		//setting participants based on sport type (WIP)
		if (type.equals("Team")) {
			country.participatingCountries(conn);
			System.out.println("+++ What countries are participating? [Select by ID]\n(Select " + numOfTeams + " amount of teams");
			for (int i = 0; i < numOfTeams; i++) {
				int playersInput = input.nextInt();
				countries[i] = country.getCountries(playersInput, conn);
			}

//			//setting teams
//			for (int i = 0; i < MAX_TEAMS; i++) {
//				String temp = countries[i];
//				team[i] = new Team(temp);
//				team[i].newTeam(temp, team[i]);
//			}
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

	} // Still a work in progress did not finish yet | 11/10 1:55AM

	public void modifyTournament() {
		Scanner input = new Scanner(System.in);
		System.out.println("What do you want to modify?");
		System.out.println("+++ 1. Date and time");
		System.out.println("+++ 2. Delete Teams or Participants");
		int userInput3 = input.nextInt();
		switch (userInput3) {
			case 1 -> {
				time.startTime();
				time.endTime();
				game.updatedTournament();
			}
			case 2 -> {
				if (type.equals("Team")) {
					System.out.println("Which team do you want to delete?");
					String userInput = input.next();
					for (int i = 0; i < game.MAX_TEAMS; i++) {
						if (userInput.toUpperCase().equals(team[i].getTeamName().toUpperCase())) {
							team[i] = null;
						}
					}
				} else {
					System.out.println("Which particiapant do you want to delete?");
				}
				game.updatedTournament();
			}
		}
	}

	public void deleteTournament() {

	}

    public void tournamentDetails(){
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
    } //for team sports

    public void tournamentDetails(String sport, Participants players) {
    	System.out.println("\n==============");
    	System.out.println(userSport.toUpperCase() + "\nTOURNAMENT DETAILS");
    	System.out.println("==============");
    	//players.listParticipants();
    	System.out.println();
    	System.out.println(time);
    	System.out.println();
    } //for head to head sports

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
