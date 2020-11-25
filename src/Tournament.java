import Database.Connect;
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
	private final int MIN_TEAMS = 2;

	////private members
	//--String
	private String userSport;
	private String tournament_sport;
	private String tournament_name;
	private String sportType;
	String[] countries;
	String[] playerNames;
	//--Int
	private int tournament_random_id;
	private int team_random_id;
	private int player_random_id;
	private int SportID;
	private int teamCount;
	private int playerCount;
	private int totalTeamAmount;
	private int teamOne;
	private int teamTwo;
	int[] playersArr;
	int[] teams;
	//--Boolean
	private Boolean TourValidation;
	private Boolean InvidValidation;
	private Boolean teamValidation;
	//--Timestamp
	private Timestamp matchTime;

	//instances
	private Sports sport;
	private Countries country;
	private Scanner input;
	private Time time;
	private Timestamp timestamp;
	private Random random;
	Participants players;
	Team team;
	List<Team> Arrteams;
	Team gold;
	Team silver;
	Team bronze;

	public void init(){
		//Initialize Classes
		sport = new Sports();
		team = new Team();
		countries = new String[MAX_TEAMS];
		playerNames = new String[MAX_TEAMS];
		country = new Countries();
		time = new Time();
		players = new Participants();
		input = new Scanner(System.in);
		random = new Random();

		//Initialize Variables
		userSport = null;
		tournament_name = null;
		tournament_sport = null;
		sportType = null;

		tournament_random_id = 0;
		SportID = 0;
		teamCount = 0;
		team_random_id = 0;
		playerCount = 0;
		player_random_id = 0;
		totalTeamAmount = 0;
		teamOne = 0;
		teamTwo = 0;

		TourValidation = false;
		InvidValidation = false;
		teamValidation = false;

		matchTime = null;

	}

	public void createTournament(Connect conn) throws IOException {
		init();

		//menu start
		System.out.println("****************************************************");
		System.out.println("************* Creating Tournament Menu *************");
		System.out.print("Tournament Name: ");
		tournament_name = input.nextLine();
		sport.outputAllSports(conn);
		System.out.println("Which sport will be played in this tournament? ");
		tournament_sport = input.nextLine();

		do {
			tournament_random_id = random.nextInt(1000);
			TourValidation = createTournamentTable(conn, tournament_random_id, tournament_name, tournament_sport);
		} while (!TourValidation);

		SportID = sport.getSportID(conn, tournament_sport);
		sportType = sport.getSportType(SportID, conn);
		if (sportType.equals("Team")){
			System.out.println("How many countries will be in this tournament? -- Must be an even amount between " +  MIN_TEAMS + " and " + MAX_TEAMS);
			teamCount = input.nextInt();
			if ((teamCount >= MIN_TEAMS) && (teamCount <= MAX_TEAMS) && (teamCount % 2 == 0)) {
			country.participatingCountries(conn);
			System.out.println("Select " + teamCount + " countries by ID");
			teams = new int[teamCount];
			for (int i = 0; i < teamCount; i++) {
				do {
					teams[i] = input.nextInt();
					team_random_id = random.nextInt(9999);
					teamValidation = createTeamTable(conn, team_random_id, country.getCountries(teams[i], conn), tournament_random_id);
				} while (!teamValidation);
			}
			} else {
				System.out.println("teamCount is out of bounds!!! tournament creation stopped");
				deleteTournament(conn, tournament_random_id);
				return;
			}
		} else if (sportType.equals("Individual")){
			System.out.println("How many players will be in this tournament? -- Must be an even amount between " +  MIN_TEAMS + " and " + MAX_TEAMS);
			playerCount = input.nextInt();
			if ((playerCount >= MIN_TEAMS) && (playerCount <= MAX_TEAMS) && (playerCount % 2 == 0)) {
				players.listParticipants(conn);
				System.out.println("Select " + playerCount + " players by ID");
				playersArr = new int[playerCount];
				for (int i = 0; i < playerCount; i++) {
					InvidValidation = false; //Required -- not redundant. Do not remove
					do {
						playersArr[i] = input.nextInt();
						player_random_id = random.nextInt(9999);
						InvidValidation = createTeamTable(conn, player_random_id, players.getPlayerName(playersArr[i], conn), tournament_random_id);
					} while (!InvidValidation);
				}
			} else {
				System.out.println("playerCount is out of bounds!!! tournament creation stopped");
				deleteTournament(conn, tournament_random_id);
				return;
			}
		}

		totalTeamAmount = team.getNumOfTeams(conn, tournament_random_id);
		System.out.println("****************************************************");
		System.out.println("************* Match/Bracket Menu *************");
		System.out.println("Please select which two teams will face each other: ");
		team.outputAllTeams(conn, tournament_random_id);
		for (int i = 0; i < (totalTeamAmount / 2); i++){
			System.out.print("Team_A:  ");
			teamOne = input.nextInt();
			System.out.print("Team_B:  ");
			teamTwo = input.nextInt();
			time = new Time();
			matchTime = time.setTime();
			createMatch(conn, tournament_random_id, teamOne, teamTwo, matchTime);
		}
		System.out.println("Tournament successfully created!!!");
	}

	public void modifyTournament(Connect conn, String tournament_name) {
		//new instances
		Scanner input = new Scanner(System.in);
		players = new Participants();
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
		System.out.println("+++ 1. Match Date and time");
		System.out.println("+++ 2. Add Teams or Participants");
		System.out.println("+++ 3. Delete Teams or Participants");
		int userInput = input.nextInt();
		switch(userInput) {
			case 1:
//				try {
//				timestamp = time.setTime();
//				String timeQuery = "UPDATE olympics.Match SET date = ? WHERE touranment_id = ?";
//				PreparedStatement pstmt = conn.getConn().prepareStatement(timeQuery);
//				pstmt.setTimestamp(1, timestamp);
//				pstmt.setInt(2, tournamentID);
//				pstmt.executeUpdate();
//				
//			} catch (Exception e) {
//				System.out.print("SQL exception occurred: " + e);
//			}
			viewTournament(conn, tournament_name);
				break;
			case 2:
				try {
					if(sportType.equals("Team")){
						country.participatingCountries(conn);
						System.out.println("Choose the ID of the team to add: ");
						int id = input.nextInt();
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

	public boolean createMatch(Connect conn, int id_tournament, int id_teamA, int id_teamB, Timestamp gameTime) {
		boolean validation = false;
		try {
			String query = "INSERT INTO olympics.Match" + "(tournament_id, team_a_id, team_b_id, date, status) VALUES" + "(?, ?, ?, ?, ?);";
			PreparedStatement pstmt = conn.getConn().prepareStatement(query);

			pstmt.setInt(1, id_tournament);
			pstmt.setInt(2, id_teamA);
			pstmt.setInt(3, id_teamB);
			pstmt.setTimestamp(4, gameTime);
			pstmt.setString(5, "Pending");
			pstmt.executeUpdate();

			validation = true;
		} catch (Exception ex) {
			System.out.println("ERROR: " + ex.getMessage());
			validation = false;
		}


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

    public void results(Connect conn, int tournamentID){
    	int matchID = returnMatchID(conn, tournamentID);
    	String winner_A_name = new String();
    	String winner_B_name = new String();
    	try {
    		String query = "SELECT winner_a_of, winner_b_of FROM olympics.Match_Knockout WHERE match_id = ?";
    		PreparedStatement pstmt = conn.getConn().prepareStatement(query);
    		pstmt.setInt(1, matchID);
    		ResultSet rs = pstmt.executeQuery();
    		while(rs.next()) {
    			int winnerA = rs.getInt("winner_a_of");
    			int winnerB = rs.getInt("winner_b_of");
    			winner_A_name = returnTeamName(conn, winnerA, tournamentID);
    			winner_B_name = returnTeamName(conn, winnerB, tournamentID);
    			//print winner
    			System.out.println("Winner A: " + winner_A_name);
    			System.out.println("Winner B: " + winner_B_name);
    		}
    		rs.close();
    	} catch (Exception e) {
    		System.out.println("SQL exception occured" + e);
    	}
    }
    
    public void displayResults(Connect conn, String tournament_name) {
    	//local members
		int tournamentID = returnTournamentID(conn, tournament_name);
		int matchID = returnMatchID(conn, tournamentID);
		int teamA = 0;
		int teamB = 0;
		
		//get teams id
		try {
			String query = "SELECT team_a_id, team_b_id FROM olympics.Match_Round WHERE match_id = ?";
			PreparedStatement pstmt = conn.getConn().prepareStatement(query);
			pstmt.setInt(1, matchID);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				teamA = rs.getInt("team_a_id");
				teamB = rs.getInt("team_b_id");
			}
			rs.close();
		} catch (Exception e) {
			System.out.println("SQL exception occured" + e);
		}
		
		//get team names
		String team_A_name = returnTeamName(conn, teamA, tournamentID);
		String team_B_name = returnTeamName(conn, teamB, tournamentID);
		
		//get details of the match and display them
    	try {
    		String query = "SELECT id, date, team_a_score, team_b_score, status FROM olympics.Match WHERE tournament_id = ?";
            PreparedStatement pstmt = conn.getConn().prepareStatement(query);
            pstmt.setInt(1, tournamentID);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()) {
            	//get values
            	int id = rs.getInt("id");
            	Timestamp time = rs.getTimestamp("date");
            	int A_Score = rs.getInt("team_a_score");
            	int B_Score = rs.getInt("team_b_score");
            	String status = rs.getString("status");
            	//display
                System.out.println("\n==============");
            	System.out.println("MATCH ID: " + id);
            	System.out.println(tournament_name + "MATCH");
            	System.out.println("==============");
            	System.out.println("Time: " + time);
            	System.out.println("Team " + team_A_name + " Score: " + A_Score);
            	System.out.println("Team" + team_B_name + " Score: " + B_Score);
            	System.out.println("Status: " + status);
            }
            rs.close();
    	} catch (Exception e) {
            System.out.println("SQL exception occured" + e);
    	}
    }
    
    public int returnMatchID(Connect conn, int tournamentID) {
    	int match_id = 0;
		try {
			String query = "SELECT id FROM olympics.Match WHERE tournament_id = ?";
			PreparedStatement pstmt = conn.getConn().prepareStatement(query);
			pstmt.setInt(1, tournamentID);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				match_id = rs.getInt("id");
			}
			rs.close();
		} catch (Exception e) {
			System.out.println("SQL exception occured" + e);
		}
    	return match_id;
    }
    
    public String returnTeamName(Connect conn, int teamID, int tournamentID) {
    	String name = new String();
		try {
			String query = "SELECT team_name FROM olympics.Team WHERE id = ? && tournament_id = ?";
			PreparedStatement pstmt = conn.getConn().prepareStatement(query);
			pstmt.setInt(1, teamID);
			pstmt.setInt(2, tournamentID);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				name = rs.getString("team_name");
			}
			rs.close();
		} catch (Exception e) {
			System.out.println("SQL exception occured" + e);
		}
    	return name;
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
