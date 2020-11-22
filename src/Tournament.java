import Database.Connect;
import com.mysql.cj.protocol.Resultset;
import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.util.Random;

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
	Random random;
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
		random = new Random();

		//menu start
		System.out.println("****************************************************");
		System.out.println("************* Creating Tournament Menu *************");
		System.out.print("Tournament Name: ");
		String tournament_name = input.nextLine();
		sport.outputAllSports(conn);
		System.out.println("Which sport will be played in this tournament? ");
		String tournament_sport = input.nextLine();
		Boolean validation = false;
		do {
			int random_id = random.nextInt(1000);
			validation = createTournamentTable(conn, random_id, tournament_name, tournament_sport);
		} while (!validation);

		int tempSportID = sport.getSportID(conn, tournament_sport);
		String sportType = sport.getSportType(tempSportID, conn);
		if (sportType.equals("Team")){
			System.out.print("How many countries will be in this tournament? ");
			int teamCount = input.nextInt();
			country.participatingCountries(conn);
			System.out.print("Select " + teamCount + " countries by ID");
			int[] teams = new int[teamCount];
			for (int i = 0; i < teamCount; i++) {
				validation = false; //Required -- not redundant. Do not remove
				do {
					teams[i] = input.nextInt();
					int random_id = random.nextInt(9999);
					validation = createTeamTable(conn, random_id, country.getCountries(teams[i], conn), returnTournamentID(conn, tournament_name));
			} while (!validation);
			}
		} else if (sportType.equals("Individual")){
			System.out.print("How many players will be in this tournament? ");
			int playerCount = input.nextInt();
			players.listParticipants(conn);
			System.out.print("Select " + playerCount + " players by ID");
			int[] playersArr = new int[playerCount];
			for (int i = 0; i < playerCount; i++) {
				validation = false; //Required -- not redundant. Do not remove
				do {
					playersArr[i] = input.nextInt();
					int random_id = random.nextInt(9999);
					validation = createTeamTable(conn, random_id, players.getPlayerName(playersArr[i], conn), returnTournamentID(conn, tournament_name));
				} while (!validation);
			}
		}

		int totalTeam = team.getNumOfTeams(conn, returnTournamentID(conn, tournament_name));
		System.out.println("****************************************************");
		System.out.println("************* Match/Bracket Menu *************");
		System.out.println("Please select which two teams will face each other: ");
		team.outputAllTeams(conn, returnTournamentID(conn, tournament_name));
		for (int i = 0; i < totalTeam; i++){
			// Work in Progress
		}
	}

	public void modifyTournament(Connect conn, String tournament_name) {
		//new instances
		Scanner input = new Scanner(System.in);
		players = new Participants();
		game = new Tournament();
		random = new Random();
		sport = new Sports();
		country = new Countries();

		//new members
		int tournamentID = returnTournamentID(conn, tournament_name);
		String tournamentSport = returnTournamentSport(conn, tournamentID);
		int sportID = sport.getSportID(conn, tournamentSport);
		String sportType = sport.getSportType(sportID, conn);
		boolean validation;

		System.out.println("What do you want to modify?");
		System.out.println("+++ 1. Date and time");
		System.out.println("+++ 2. Add Teams or Participants");
		System.out.println("+++ 3. Delete Teams or Participants");
		int userInput = input.nextInt();
		switch(userInput) {
			case 1:
//			time = new Time();
//			time.startTime();
//			time.endTime();
//			game.updatedTournament();
				break;
			case 2:
				try {
					if(sportType.equals("Team")){
						country.participatingCountries(conn);
						System.out.println("Choose the ID of the team to add: ");
						int id = input.nextInt();
						validation = false; //Required -- not redundant. Do not remove
						do {
							int random_id = random.nextInt(9999);
							validation = createTeamTable(conn, random_id, country.getCountries(id, conn), tournamentID);
						} while (!validation);
						//end updating
						//updatedTournament();
						viewTournament(conn, tournament_name);

					}
					else if(sportType.equals("Individual")) {
						//get players
						players.listParticipants(conn);
						//select who to add
						System.out.println("Choose the ID of the participant to add: ");
						int id = input.nextInt();
						validation = false; //Required -- not redundant. Do not remove
						do {
							int random_id = random.nextInt(9999);
							validation = createTeamTable(conn, random_id, players.getPlayerName(id, conn), tournamentID);
						} while (!validation);
						//end updating
						//updatedTournament();
						viewTournament(conn, tournament_name);
					}
				} catch (Exception e) {
					System.out.println("SQL exception occured" + e);
				}
				break;
			case 3:
				try {
					//deleting teams or participant
					String deleteTeam = "DELETE FROM olympics.Team WHERE (id, tournament_id) = (?, ?)";
					PreparedStatement pstmt2 = conn.getConn().prepareStatement(deleteTeam);
					System.out.println("Choose the ID of the team or participant to delete: ");
					int id = input.nextInt();
					pstmt2.setInt(1, id);
					pstmt2.setInt(2, tournamentID);
					pstmt2.executeUpdate();
					// updatedTournament();
					viewTournament(conn, tournament_name);
				} catch (Exception e) {
					System.out.println("SQL exception occured" + e);
				}
				break;
		}
	}

	public boolean createTournamentTable(Connect conn, int id, String t_name, String t_type) {
		boolean validation;
		try {
			String query = "INSERT INTO olympics.Tournament" + "(id, tournament_name, tournament_type) VALUES" + "(?, ?, ?);"; // SQL Query
			PreparedStatement pstmt = conn.getConn().prepareStatement(query); // Prepared Statement in order to pass values through SQL statements

			pstmt.setInt(1, id);
			pstmt.setString(2, t_name);
			pstmt.setString(3, t_type);
			pstmt.executeUpdate();
			validation = true;

		} catch (Exception ex) {
			System.out.println("ERROR: " + ex.getMessage());
			validation = false;
		}
		return validation;
	}

	public int returnTournamentID(Connect conn, String tournament_name) {
		int tournament_ID = 0;
		try {
			String query = "SELECT id FROM olympics.Tournament WHERE tournament_name = ?";
			PreparedStatement pstmt = conn.getConn().prepareStatement(query);
			pstmt.setString(1, tournament_name);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				tournament_ID = rs.getInt("id");
			}
			rs.close();

		} catch (SQLException ex) {
			System.out.println("SQL exception occurred" + ex);
		}
		return tournament_ID;
	}
	
	public String returnTournamentSport(Connect conn, int ID) {
		String tournamentSport = new String();
		
		try {
			String query = "SELECT tournament_type FROM olympics.Tournament where id = ?";
			PreparedStatement pstmt = conn.getConn().prepareStatement(query);
	        pstmt.setInt(1, ID);
	        pstmt.executeQuery();
	        ResultSet rs = pstmt.executeQuery();
	        while (rs.next()) {
				tournamentSport = rs.getString("tournament_type");
			}
			rs.close();

		} catch(Exception e) {
			System.out.println("SQL exception occured" + e);
		}
		return tournamentSport;
	}
	
	public boolean createTeamTable(Connect conn, int id, String team_name, int tournament_id) {
		boolean validation;
		try {
			String query = "INSERT INTO olympics.Team" + "(id, team_name, tournament_id) VALUES" + "(?, ?, ?);";
			PreparedStatement pstmt = conn.getConn().prepareStatement(query);

			pstmt.setInt(1, id);
			pstmt.setString(2, team_name);
			pstmt.setInt(3, tournament_id);
			pstmt.executeUpdate();
			validation = true;

		} catch (Exception ex) {
			System.out.println("ERROR: " + ex.getMessage());
			validation = false;
		}
		return validation;
	}

	public boolean createMatch(Connect conn) {
		boolean validation = false;



		return validation;
	}



	public boolean deleteTournament(Connect conn, int tournament_id) {
		boolean validation;
		try {
			String deleteTeams = "DELETE FROM olympics.Team WHERE tournament_id = ?";
			String deleteTournament = "DELETE FROM olympics.Tournament WHERE id = ?";

			PreparedStatement pstmt = conn.getConn().prepareStatement(deleteTeams);
			pstmt.setInt(1, tournament_id);
			pstmt.executeUpdate();

			pstmt = conn.getConn().prepareStatement(deleteTournament);
			pstmt.setInt(1, tournament_id);
			pstmt.executeUpdate();

			validation = true;
		} catch (Exception ex) {
			System.out.println("ERROR: " + ex.getMessage());
			validation = false;
		}
		return validation;
	}

	public void viewTournamentTable(Connect conn) {
		try {
			Statement stmt = conn.getConn().createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM olympics.Tournament");
			System.out.println("ID     Name           Type");

			while (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("tournament_name");
				String type = rs.getString("tournament_type");
				System.out.println(id + "     " + name + "     " + type);
			}
		} catch(SQLException e) {
			System.out.println("SQL exception occurred" + e);
		}
	}

	public void viewTournament(Connect conn, String tournament_name){
  	  try {
            String query = "SELECT id, tournament_name, tournament_type FROM olympics.Tournament where tournament_name = ?";
            PreparedStatement pstmt = conn.getConn().prepareStatement(query);
            pstmt.setString(1, tournament_name);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()) {
                //get tournament
                int id = rs.getInt("id");
                String tournamentName = rs.getString("tournament_name");
                String tournamentType = rs.getString("tournament_type");
                System.out.println("\n==============");
                System.out.println("ID: "+ id);
                System.out.println(tournamentName);
                System.out.println(tournamentType);
                System.out.println("==============");
                
                //get the participants
                String query1 = "SELECT id, team_name FROM olympics.Team WHERE tournament_id = ?";
                PreparedStatement pstmt1 = conn.getConn().prepareStatement(query1);
                pstmt1.setInt(1, id);
                ResultSet rs1 = pstmt1.executeQuery();
                while(rs1.next()) {
                    int pID = rs1.getInt("id");
                    String pName = rs1.getString("team_name");
                    System.out.println(pID + "     " + pName);
                }
            }
        } catch (Exception e){
            System.out.println("SQL exception occured" + e);
        }     
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
		//System.out.println("First place: " + gold.getTeamName());
		//System.out.println("Second place: " + silver.getTeamName());
		//System.out.println("Third place: " + bronze.getTeamName());
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
