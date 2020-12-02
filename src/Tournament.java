import Database.Connect;
import java.io.IOException;
import java.sql.*;
import java.util.Scanner;
import java.util.List;
import java.util.Random;

public class Tournament {
	//constants
	private final int MAX_TEAMS = 32;
	private final int MIN_TEAMS = 4;

	////private members
	//--String
	private String userSport;
	private String tournament_sport;
	private String tournament_name;
	private String sportType;
	private String tournament_status;
	String[] countries;
	String[] playerNames;
	String[] arrTeams;
	//--Int
	private int tournament_id;
	private int team_id;
	private int player_id;
	private int SportID;
	private int teamCount;
	private int playerCount;
	private int totalTeamAmount;
	private int teamOne;
	private int teamTwo;
	private int participantsNum;
	private int userInput;
	private int modifyMatchTimeInput;
	private int submitScoreA, submitScoreB;
	int[] playersArr;
	int[] teams;
	int[] checkParticipantCap;
	//--Boolean
	private Boolean TourValidation;
	private Boolean InvidValidation;
	private boolean playValidation;
	private Boolean teamValidation;
	private Boolean modifyTimeBool;
	//--Timestamp
	private Timestamp matchTime;
	private Timestamp modifyTime;

	//instances
	private Sports sport;
	private Countries country;
	private Scanner input;
	private Time time;
	private Timestamp timestamp;
	private Random random;
	private Participants players;
	private Team team;
	private Match match;
	private matchRound matchround;
	private matchKnockout matchknockout;
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
		arrTeams = new String[0];
		country = new Countries();
		time = new Time();
		players = new Participants();
		match = new Match();
		matchround = new matchRound();
		matchknockout = new matchKnockout();
		input = new Scanner(System.in);
		random = new Random();

		//Initialize Variables
		userSport = null;
		tournament_name = null;
		tournament_sport = null;
		sportType = null;
		tournament_status = null;

		tournament_id = 0;
		SportID = 0;
		teamCount = 0;
		team_id = 0;
		playerCount = 0;
		player_id = 0;
		totalTeamAmount = 0;
		teamOne = 0;
		teamTwo = 0;
		participantsNum = 0;
		userInput = 0;
		modifyMatchTimeInput = 0;
		submitScoreA = 0;
		submitScoreB = 0;
		checkParticipantCap = new int[]{4, 8, 16, 32, 64};

		TourValidation = false;
		InvidValidation = false;
		teamValidation = false;
		playValidation = false;

		matchTime = null;
		modifyTime = null;
	}

	public void createTournament(Connect conn) throws IOException {
		init();

		//menu start
		System.out.println("****************************************************");
		System.out.println("************* Creating Tournament Menu *************");
		System.out.print("Tournament Name: ");
		tournament_name = input.nextLine();
		sport.outputAllSports(conn);
		System.out.print("Which sport will be played in this tournament?  ");
		tournament_sport = input.nextLine();

		do {
			tournament_id = random.nextInt(1000);
			TourValidation = createTournamentTable(conn, tournament_id, tournament_name, tournament_sport);
		} while (!TourValidation);

		SportID = sport.getSportID(conn, tournament_sport);
		sportType = sport.getSportType(SportID, conn);
		if (sportType.equals("Team")){
			System.out.print("How many countries will be in this tournament? -- Must choose either: 4, 8, 16, 32, or 64  ");
			teamCount = input.nextInt();
			if (certifyTeamCount(teamCount)) {
				country.participatingCountries(conn);
				System.out.println("Select " + teamCount + " countries by ID");
				teams = new int[teamCount];
				for (int i = 0; i < teamCount; i++) {
					do {
						teams[i] = input.nextInt();
						team_id = random.nextInt(9999);
						teamValidation = team.createTeamTable(conn, team_id, country.getCountries(teams[i], conn), tournament_id);
						if (!teamValidation)
							System.out.println("Unable to use that team, please select a new one.");
					} while (!teamValidation);
				}
			} else {
				System.out.println("teamCount is out of bounds!!! tournament creation stopped");
				deleteTournament(conn, tournament_id);
				return;
			}
		} else if (sportType.equals("Individual")){
			System.out.println("How many players will be in this tournament? -- Must choose either: 4, 8, 16, 32, or 64  ");
			playerCount = input.nextInt();
			if (certifyTeamCount(playerCount)) {
				players.listParticipants(conn);
				System.out.println("Select " + playerCount + " players by ID");
				playersArr = new int[playerCount];
				for (int i = 0; i < playerCount; i++) {
					do {
						playersArr[i] = input.nextInt();
						player_id = random.nextInt(9999);
						InvidValidation = team.createTeamTable(conn, player_id, players.getPlayerName(playersArr[i], conn), tournament_id);
						if (!InvidValidation)
							System.out.println("Unable to use that player, please select a new one.");
					} while (!InvidValidation);
				}
			} else {
				System.out.println("playerCount is out of bounds!!! tournament creation stopped");
				deleteTournament(conn, tournament_id);
				return;
			}
		}

		totalTeamAmount = team.getTeamCount(conn, tournament_id);
		System.out.println("****************************************************");
		System.out.println("************* Match/Bracket Menu *************");
		System.out.println("Please select which two teams will face each other: ");
		boolean dupeCheck;
		boolean id_verify;
		for (int i = 0; i < (totalTeamAmount / 2); i++){
			team.outputAllTeams(conn, tournament_id);
			System.out.print("Team_A:  ");
			teamOne = input.nextInt();
			dupeCheck = match.preventDuplicate(conn, tournament_id, teamOne);
			id_verify = team.verifyID(conn, teamOne, tournament_id);
			if (dupeCheck || !id_verify) {
				i--;
				System.out.println("Invalid team selection, please try again.");
				continue;
			}
			System.out.print("Team_B:  ");
			teamTwo = input.nextInt();
			dupeCheck = match.preventDuplicate(conn, tournament_id, teamTwo);
			id_verify = team.verifyID(conn, teamTwo, tournament_id);
			if (dupeCheck || (teamTwo == teamOne) || !id_verify) {
				i--;
				System.out.println("Invalid team selection, please try again.");
				continue;
			}
			time = new Time();
			matchTime = time.setTime();
			match.createMatch(conn, tournament_id, teamOne, teamTwo, matchTime, "Pending");
			matchround.setMatchRound(conn, match.getMatchID(conn, tournament_id,  teamOne, teamTwo), teamOne, teamTwo);
		}
		for (int i = 0; i < (totalTeamAmount / 2); i++){
			if ((totalTeamAmount / 2) == 2) {
				System.out.println("Please set the time for the Finals: ");
				matchTime = time.setTime();
				match.createIncompleteMatch(conn, tournament_id, matchTime, "Incomplete");
				totalTeamAmount /= totalTeamAmount;
			} else if ((totalTeamAmount / 2) == 4) {
				System.out.println("Please set the time for the Semi-Finals: ");
				matchTime = time.setTime();
				match.createIncompleteMatch(conn, tournament_id, matchTime, "Incomplete");
				totalTeamAmount /= totalTeamAmount;
			} else if ((totalTeamAmount / 2) == 8) {
				System.out.println("Please set the time for the Quarter-Finals: ");
				matchTime = time.setTime();
				match.createIncompleteMatch(conn, tournament_id, matchTime, "Incomplete");
				totalTeamAmount /= totalTeamAmount;
			} else if ((totalTeamAmount / 2) == 16) {
				System.out.println("Please set the time for Round of 16: ");
				matchTime = time.setTime();
				match.createIncompleteMatch(conn, tournament_id, matchTime, "Incomplete");
				totalTeamAmount /= totalTeamAmount;
			} else if ((totalTeamAmount / 2) == 32) {
				System.out.println("Please set the time for Round of 32: ");
				matchTime = time.setTime();
				match.createIncompleteMatch(conn, tournament_id, matchTime, "Incomplete");
				totalTeamAmount /= totalTeamAmount;
			}
		}
		setTournamentStatus(conn, tournament_id, "Pending");
		System.out.println("Tournament successfully created!!!");
	}

	public void modifyTournament(Connect conn, int tournamentID) {
		init();

		//new instances
		Scanner input = new Scanner(System.in);
		players = new Participants();
		random = new Random();
		sport = new Sports();
		country = new Countries();

		//new members
		tournament_name = getTournamentName(conn, tournamentID);
		tournament_id = getTournamentID(conn, tournament_name);
		tournament_sport = getTournamentSport(conn, tournament_id);
		SportID = sport.getSportID(conn, tournament_sport);
		sportType = sport.getSportType(SportID, conn);
		boolean validation;

		System.out.println("What do you want to modify?");
		System.out.println("+++ 1. Match Date and time"); // 100% Completed
		System.out.println("+++ 2. Add Teams or Participants"); // 75% Completed
		System.out.println("+++ 3. Delete Teams or Participants"); // 25% Completed
		userInput = input.nextInt();
		switch(userInput) {
			case 1:
				match.outputAllMatchesFromTournament(conn, tournament_id);
				System.out.println("Which match's date would you like to change? [SELECT BY MATCH ID]");
				modifyMatchTimeInput = input.nextInt();
				modifyTime = time.setTime();
				modifyTimeBool = time.updateTime(conn, modifyMatchTimeInput, modifyTime);
				if (modifyTimeBool) {
					System.out.println("Time was successfully modified on " + tournament_name +"!");
				}
				else {
					System.out.println("Time was unsuccessfully changed...");
				}
				break;
			case 2:
				participantsNum = team.getTeamCount(conn, tournament_id);
				try {
					if(sportType.equals("Team")){
						System.out.println("Currently this tournament has " + participantsNum + " teams.");
						System.out.println("How many teams would you like to add? (Must be a factor of 2)");
						int addTeams = input.nextInt();
						int[] pickTeam = new int[addTeams];
						if ((addTeams % 2 == 0) && ((addTeams + participantsNum) >= 2) && ((addTeams + participantsNum) <= 32)) {
							country.participatingCountries(conn);
							System.out.println("Choose the ID of the team to add: ");
							for (int i = 0; i < addTeams; i++) {
								do {
									pickTeam[i] = input.nextInt();
									team_id = random.nextInt(9999);
									validation = team.createTeamTable(conn, team_id, country.getCountries(pickTeam[i], conn), tournament_id);
								} while (!validation);
							}
							viewTournament(conn, tournament_id);
						} else {
							System.out.println("Unable to add " + addTeams + " teams to the tournament");
						}
					}
					else if(sportType.equals("Individual")) {
						//get players
						System.out.println("Currently this tournament has " + participantsNum + " players.");
						System.out.println("How many players would you like to add? (Must be a factor of 2)");
						int addPlayers = input.nextInt();
						int[] pickPlayer = new int[addPlayers];
						if ((addPlayers % 2 == 0) && ((addPlayers + participantsNum) >= 2) && ((addPlayers + participantsNum) <= 32)) {
							players.listParticipants(conn);
							System.out.println("Choose the ID of the participant to add: ");
							for (int i = 0; i < addPlayers; i++) {
								do {
									pickPlayer[i] = input.nextInt();
									player_id = random.nextInt(9999);
									validation = team.createTeamTable(conn, player_id, players.getPlayerName(pickPlayer[i], conn), tournament_id);
								} while (!validation);
							}
							viewTournament(conn, tournament_id);
						} else {
							System.out.println("Unable to add " + addPlayers + " players to the tournament");
						}
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
					pstmt2.setInt(2, tournament_id);
					pstmt2.executeUpdate();

					viewTournament(conn, tournament_id);
				} catch (Exception e) {
					System.out.println("SQL exception occured" + e);
				}
				break;
		}
	}

	public void playTournament(Connect conn, int tournamentID){
		init();
		tournament_name = getTournamentName(conn, tournamentID);
		tournament_id = getTournamentID(conn, tournament_name);
		sportType = getTournamentSport(conn, tournament_id);
		tournament_status = getTournament_status(conn, tournament_id);
		teamCount = team.getTeamCount(conn, tournament_id);
		arrTeams = new String[teamCount];
		arrTeams = team.getAllNames(conn, tournament_id);
		int startInput = 0;


		for (int i = 0; i < teamCount; i++) {
			System.out.println(arrTeams[i]);
		}

		if (tournament_status.equals("Pending")) {
			System.out.println("There are currently " + teamCount + " teams in this tournament...");
			System.out.println("Here are the current matches scheduled:");
			match.outputAllMatchesFromTournament(conn, tournament_id);
			System.out.println("Would you like to officially start this tournament now? [Changes status to \"In Progress]\"\nSubmit 1 for yes or 2 for no");
			System.out.println("WARNING! -- If you revert tournament state back to Pending any match results or medals assigned will be permanently deleted."); // This doesn't actually work yet lol
			startInput = input.nextInt();
			if (startInput == 1){
				setTournamentStatus(conn, tournament_id, "In Progress");
				System.out.println("Which match would you like to start now? [Select by ID]");
				startInput = input.nextInt();
				match.outputExactMatch(conn, startInput);
				System.out.print("Submit the score for team A:  ");
				submitScoreA = input.nextInt();
				System.out.print("Submit the score for team B:  ");
				match.setMatchScores(conn, startInput, submitScoreA, submitScoreB);

			} else if (startInput == 2) {
				System.out.println("Tournament will remain in " + tournament_status + " status...");
			}
		} else {
			match.outputAllMatchesFromTournament(conn, tournament_id);
			System.out.println("Which match would you like to start now? [Select by ID]");
			startInput = input.nextInt();
			match.outputExactMatch(conn, startInput);
			System.out.print("Submit the score for team A:  ");
			submitScoreA = input.nextInt();
			System.out.print("Submit the score for team B:  ");
			match.setMatchScores(conn, startInput, submitScoreA, submitScoreB);
		}
	}

	public boolean deleteTournament(Connect conn, int tournament_id) {
		init();
		boolean validation;
		try {
			String query = "DELETE FROM olympics.Tournament WHERE id = ?";
			PreparedStatement pstmt = conn.getConn().prepareStatement(query);

			pstmt.setInt(1, tournament_id);
			pstmt.executeUpdate();

			validation = true;
		} catch (Exception ex) {
			System.out.println("ERROR: " + ex.getMessage());
			validation = false;
		}
		return validation;
	}

	public boolean createTournamentTable(Connect conn, int id, String t_name, String t_type) {
		boolean validation;
		try {
			String query = "INSERT INTO olympics.Tournament" + "(id, tournament_name, tournament_type, status) VALUES" + "(?, ?, ?, ?);"; // SQL Query
			PreparedStatement pstmt = conn.getConn().prepareStatement(query); // Prepared Statement in order to pass values through SQL statements

			pstmt.setInt(1, id);
			pstmt.setString(2, t_name);
			pstmt.setString(3, t_type);
			pstmt.setString(4, "Incomplete");
			pstmt.executeUpdate();
			validation = true;

		} catch (Exception ex) {
			System.out.println("ERROR: " + ex.getMessage());
			validation = false;
		}
		return validation;
	}

	public void setTournamentStatus(Connect conn, int tournamentID, String changeStatus) {
		try {
			String query = "UPDATE olympics.Tournament SET status = ? WHERE id = ?";
			PreparedStatement pstmt = conn.getConn().prepareStatement(query);

			pstmt.setString(1, changeStatus);
			pstmt.setInt(2, tournamentID);
			pstmt.executeUpdate();
		} catch (Exception e) {
			System.out.println("SQL exception occurred " + e);
		}
	}

	public String getTournament_status(Connect conn, int tournamentID){
		String statusReturned = null;
		try {
			String query = "SELECT status FROM olympics.Tournament WHERE id = ?";
			PreparedStatement pstmt = conn.getConn().prepareStatement(query);
			pstmt.setInt(1, tournamentID);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				statusReturned = rs.getString("status");
			}
			rs.close();
		} catch (Exception e) {
			System.out.println("SQL exception occurred " + e);
		}
		return statusReturned;
	}

	public int getTournamentID(Connect conn, String tournament_name) {
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

	public String getTournamentName(Connect conn, int id) {
		String name = "";
		try {
			String query = "SELECT tournament_name FROM olympics.Tournament where id = ?";
			PreparedStatement pstmt = conn.getConn().prepareStatement(query);
			pstmt.setInt(1, id);
			pstmt.executeQuery();
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				name = rs.getString("tournament_name");
			}
			rs.close();

		} catch(Exception e) {
			System.out.println("SQL exception occured" + e);
		}
		return name;
	}
	
	public String getTournamentSport(Connect conn, int ID) {
		String tournamentSport = "";
		
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

	public boolean verifyTournament(Connect conn, int tournament_id) {
		boolean verify;
		String state = getTournament_status(conn, tournament_id);

		verify = (!state.equals("Incomplete")) && (!state.equals("Completed"));

		return verify;
	} // WIP
	// Verifies if the tournament can be played or not

	public void viewTournamentTable(Connect conn) {
		try {
			Statement stmt = conn.getConn().createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM olympics.Tournament");
			System.out.printf("%-7s%-25s%-25s\n", "ID", "Name", "Type", "Status");

			while (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("tournament_name");
				String type = rs.getString("tournament_type");
				String state = rs.getString("status");

				System.out.printf("%-7d%-25s%-25s\n", id, name, type, state);
			}
		} catch(SQLException e) {
			System.out.println("SQL exception occurred" + e);
		}
	}

	public void viewTournament(Connect conn, int tournamentID){
  	  try {
            String query = "SELECT id, tournament_name, tournament_type FROM olympics.Tournament where id = ?";
            PreparedStatement pstmt = conn.getConn().prepareStatement(query);
            pstmt.setInt(1, tournamentID);
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

  	public boolean certifyTeamCount(int count) {
		for (int i = 0; i < 5; i++)
			if (count == checkParticipantCap[i])
				return true;

		return false;
	} // WIP
	// This function tests everything part of a tournament to make sure its has all the necessary parts in order to use from start to finish

    public void results(Connect conn, int tournamentID){
    	//int matchID = match.getMatchID(conn, tournamentID);
    	String winner_A_name = new String();
    	String winner_B_name = new String();
    	try {
    		String query = "SELECT winner_a_of, winner_b_of FROM olympics.Match_Knockout WHERE match_id = ?";
    		PreparedStatement pstmt = conn.getConn().prepareStatement(query);
    		//pstmt.setInt(1, matchID);
    		ResultSet rs = pstmt.executeQuery();
    		while(rs.next()) {
    			int winnerA = rs.getInt("winner_a_of");
    			int winnerB = rs.getInt("winner_b_of");
    			winner_A_name = team.getTeamName(conn, winnerA, tournamentID);
    			winner_B_name = team.getTeamName(conn, winnerB, tournamentID);
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
		int tournamentID = getTournamentID(conn, tournament_name);
		//int matchID = match.getMatchID(conn, tournamentID);
		int teamA = 0;
		int teamB = 0;
		
		//get teams id
		try {
			String query = "SELECT team_a_id, team_b_id FROM olympics.Match_Round WHERE match_id = ?";
			PreparedStatement pstmt = conn.getConn().prepareStatement(query);
			//pstmt.setInt(1, matchID);
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
		String team_A_name = team.getTeamName(conn, teamA, tournamentID);
		String team_B_name = team.getTeamName(conn, teamB, tournamentID);
		
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
}
