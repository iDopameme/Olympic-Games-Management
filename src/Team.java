import Database.Connect;

import javax.xml.transform.Result;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Team{
	//private members
	private int teamID;
	private String teamName;
	private int tournamentID;

	
	Team(){
		init();
	}
	
	public void init() {
		teamID = 0;
		teamName = null;
		tournamentID = 0;
	}


	public boolean createTeamTable(Connect conn, int id, String team_name, int tournament_id) {
		boolean validation;
		if (preventDuplicate(conn, team_name, tournament_id))
			return false;
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
	} // Completed -- Tested & Confirmed!

	public void setTeamName(Connect conn, int id_team, String name) {
		try {
			String query = "UPDATE olympics.Team SET team_name = ? WHERE id = ?";
			PreparedStatement pstmt = conn.getConn().prepareStatement(query);

			pstmt.setString(1, name);
			pstmt.setInt(2, id_team);
			pstmt.executeUpdate();

		} catch (Exception ex) {
			System.out.println("ERROR: " + ex.getMessage());
		}
	} // Completed -- Tested & Confirmed!
	// Changes the team name if need be.

	public void deleteTeam(Connect conn, int id_team){
		try {
			String query = "DELETE FROM olympics.Team WHERE id = ?";
			PreparedStatement pstmt = conn.getConn().prepareStatement(query);

			pstmt.setInt(1, id_team);
			pstmt.executeUpdate();
		} catch (Exception ex) {
			System.out.println("ERROR: " + ex.getMessage());
		}
	}

	public void deleteAllTeams(Connect conn, int id_tournament){
		try {
			String query = "DELETE FROM olympics.Team WHERE tournament_id = ?";
			PreparedStatement pstmt = conn.getConn().prepareStatement(query);

			pstmt.setInt(1, id_tournament);
			pstmt.executeUpdate();
		} catch (Exception ex) {
			System.out.println("ERROR: " + ex.getMessage());
		}
	}

	public int getTeamID(Connect conn, String name_team) {
		init();
		try {
			String query = "SELECT id FROM olympics.Team WHERE team_name = ?";
			PreparedStatement pstmt = conn.getConn().prepareStatement(query);
			pstmt.setString(1, name_team);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				teamID = rs.getInt("id");
			}
			rs.close();
		} catch (SQLException e) {
			System.out.println("SQL exception occurred" + e);
		}
		return teamID;
	} // Completed -- Tested & Confirmed!
	// Returns an ID from just one team using a passed string variable

	public String getTeamName(Connect conn, int team_id) {
		init();
		try {
			String sql = "SELECT team_name FROM olympics.Team WHERE id = ?";
			PreparedStatement pstmt = conn.getConn().prepareStatement(sql);
			pstmt.setInt(1, team_id);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				teamName = rs.getString("team_name");
			}
			rs.close();
		} catch (SQLException e) {
			System.out.println("SQL exception occurred" + e);
		}
		return teamName;
	} // Completed -- Tested & Confirmed!
	// Returns a Name from just one tean using a passed int variable

	public String getTeamName(Connect conn, int teamID, int id_tournament) {
		String name = "";
		try {
			String query = "SELECT team_name FROM olympics.Team WHERE id = ? && tournament_id = ?";
			PreparedStatement pstmt = conn.getConn().prepareStatement(query);
			pstmt.setInt(1, teamID);
			pstmt.setInt(2, id_tournament);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				name = rs.getString("team_name");
			}
			rs.close();
		} catch (Exception e) {
			System.out.println("SQL exception occured" + e);
		}
		return name;
	} // Completed -- Tested & Confirmed!
	// Overloaded Function accepting an additional parameter which is the tournament ID

	public int getTournamentID(Connect conn, int team_id) {
		init();
		try {
			String query = "SELECT tournament_id FROM olympics.Team WHERE id = ?";
			PreparedStatement pstmt = conn.getConn().prepareStatement(query);
			pstmt.setInt(1, team_id);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				tournamentID = rs.getInt("tournament_id");
			}
			rs.close();
		} catch (SQLException e) {
			System.out.println("SQL exception occurred" + e);
		}
		return tournamentID;
	} // Completed -- Tested & Confirmed!
	// Returns the tournament ID based off the team_id that's passed.

	public int[] getTeamIDs(Connect conn, int id_tournament) {
		int count = getTeamCount(conn, id_tournament);
		int[] teams = new int[count];

		try {
			String sql = "SELECT id FROM olympics.Team WHERE tournament_id = ?";
			PreparedStatement pstmt = conn.getConn().prepareStatement(sql);
			int i = 0;
			pstmt.setInt(1, id_tournament);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				teams[i] = rs.getInt("id");
				i++;
			}
			rs.close();
		} catch (SQLException e) {
			System.out.println("SQL exception occurred" + e);
		}
		return teams;
	} // Completed -- Tested & Confirmed!
	// Returns all the team IDS associated with the tournament ID passed

	public String[] getAllNames(Connect conn, int id_tournament) {
		int count = getTeamCount(conn, id_tournament);
		String[] listOfTeam = new String[count];
		try {
			String sql = "SELECT team_name FROM olympics.Team WHERE tournament_id = ?";
			PreparedStatement pstmt = conn.getConn().prepareStatement(sql);
			pstmt.setInt(1, id_tournament);
			ResultSet rs = pstmt.executeQuery();
			int i=0;
			while (rs.next()) {
				listOfTeam[i] = rs.getString("team_name");
				i++;
			}
			rs.close();
		} catch (SQLException e) {
			System.out.println("SQL exception occurred" + e);
		}
		return listOfTeam;
	} // Completed -- Tested & Confirmed!
	// Returns all the team Names associated with the tournament ID passed

	public boolean preventDuplicate(Connect conn, String name, int id_tournament) {
		init();
		try {
			String sql = "SELECT team_name FROM olympics.Team WHERE team_name = ? && tournament_id = ?";
			PreparedStatement pstmt = conn.getConn().prepareStatement(sql);
			pstmt.setString(1, name);
			pstmt.setInt(2, id_tournament);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				teamName = rs.getString("team_name");
				if (name.equals(teamName))
					return true;
			}
		} catch (SQLException e) {
			System.out.println("SQL exception occurred" + e);
		}
		return false;
	}

	public boolean verifyID(Connect conn, int ID, int id_tournament) {
		init();
		try {
			String sql = "SELECT id FROM olympics.Team WHERE tournament_id = ?";
			PreparedStatement pstmt = conn.getConn().prepareStatement(sql);

			pstmt.setInt(1, id_tournament);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				teamID = rs.getInt("id");
				if (ID == teamID)
					return true;
			}

		} catch (SQLException e) {
			System.out.println("SQL Exception occurred." + e);
		}
		return false;
	}

	public void outputAllTeams(Connect conn, int tournamentID) {
		try {
			String sql = "SELECT id, team_name FROM olympics.Team WHERE tournament_id = ?";
			PreparedStatement pstmt = conn.getConn().prepareStatement(sql);
			pstmt.setInt(1, tournamentID);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				int idTeam = rs.getInt("id");
				String nameTeam = rs.getString("team_name");

				System.out.println("ID: " + idTeam + " Name: " + nameTeam);
			}
			rs.close();
		} catch (SQLException e) {
			System.out.println("SQL exception occurred" + e);
		}
	} // Completed -- Tested & Confirmed!
	// Outputs all the Teams that are associated with the tournament ID passed

	public int getTeamCount(Connect conn, int tournamentID){
		int teamCOUNT = 0;
		try {
			String sql = "SELECT COUNT(tournament_id) FROM olympics.Team WHERE tournament_id = ?";
			PreparedStatement pstmt = conn.getConn().prepareStatement(sql);
			pstmt.setInt(1, tournamentID);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				teamCOUNT = rs.getInt("COUNT(tournament_id)");
			}
		} catch (SQLException e) {
			System.out.println("SQL exeception occurred" + e);
		}
		return teamCOUNT;
	} // Completed -- Tested & Confirmed!
	// Returns the exact number of teams currently in the tournament based off the tournament ID


	
}
