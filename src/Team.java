import Database.Connect;
import com.mysql.cj.protocol.Resultset;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Team{
	//private members
	private int teamID;
	private String teamName;
	private int tournament_id;
	
	Team(){
		init();
	}

	public Team(String country) {
		teamName = country;
		init();
	}
	
	public void init() {
		teamID = 0;
		teamName = null;
		tournament_id = 0;
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
	}

	public int getTeamID(Connect conn, String teamName){
		teamID = 0;
		try {
			String sql = "SELECT id FROM olympics.Team WHERE team_name = ?";
			PreparedStatement pstmt = conn.getConn().prepareStatement(sql);
			pstmt.setString(1, teamName);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				teamID = rs.getInt("id");
			}
		} catch (SQLException e) {
			System.out.println("SQL exception occurred" + e);
		}
		return teamID;
	}

	public String getTeamName(Connect conn, int team_id) {
		teamName = null;
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
	}

	public int getNumOfTeams(Connect conn, int tournamentID){
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
	}


	
}
