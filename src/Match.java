import Database.Connect;
import com.mysql.cj.protocol.Resultset;
import com.mysql.cj.x.protobuf.MysqlxPrepare;

import java.sql.*;



public class Match {

    Tournament game = new Tournament();
    Team team = new Team();

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

    public void setMatchScores(Connect conn, int id_match, int a_score, int b_score){
        try {
            team.get

            String query = "INSERT INTO olympics.Match" + "(team_a_score, team_b_score) VALUES" + "(?, ?)";
            PreparedStatement pstmt = conn.getConn().prepareStatement(query);

            pstmt.setInt(1, a_score);
            pstmt.setInt(2, b_score);
            pstmt.executeUpdate();

        } catch (Exception ex) {
            System.out.println("ERROR: " + ex.getMessage());
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
            System.out.println("SQL exception occurred: " + e);
        }
        return match_id;
    }

    public void outputAllMatches(Connect conn, int tournamentID) {
        int matchAmount = getMatchCount(conn, tournamentID);
        int[] matchID = new int[matchAmount], team_aID = new int[matchAmount], team_bID = new int[matchAmount];
        int wLoop = 0;
        String[] teamA_name = new String[matchAmount], teamB_name = new String[matchAmount];
        Timestamp[] matchTime = new Timestamp[matchAmount];
        try {
            String query = "SELECT id, date, team_a_id, team_b_id FROM olympics.Match WHERE tournament_id = ?";
            String teamNameQuery = "SELECT team_name FROM olympics.Team WHERE id = ?";

            PreparedStatement pstmt = conn.getConn().prepareStatement(query);
            pstmt.setInt(1, tournamentID);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                matchID[wLoop] = rs.getInt("id");
                matchTime[wLoop] = rs.getTimestamp("date");
                team_aID[wLoop] = rs.getInt("team_a_id");
                team_bID[wLoop] = rs.getInt("team_b_id");
                wLoop++;
            }

            pstmt = conn.getConn().prepareStatement(teamNameQuery);

            for (int i = 0; i < matchAmount; i++) {
                pstmt.setInt(1, team_aID[i]);
                rs = pstmt.executeQuery();
                while (rs.next())
                    teamA_name[i] = rs.getString("team_name");
                pstmt.setInt(1, team_bID[i]);
                rs = pstmt.executeQuery();
                while (rs.next())
                    teamB_name[i] = rs.getString("team_name");
            }

            System.out.printf("%-7s%-25s%-7s%-20s%-7s%-20s\n", "ID", "Date", "ID", "Team_A", "ID", "Team_B");

            for (int i = 0; i < matchAmount; i++) {
                System.out.printf("%-7s%-25s%-7d%-20s%-7d%-20s\n", matchID[i], matchTime[i], team_aID[i], teamA_name[i], team_bID[i], teamB_name[i]);
            }

        } catch (Exception e) {
            System.out.println("SQL exception occurred: " + e);
        }
    }

    public void outputExactMatch(Connect conn, int matchID) {
        int match_ID = 0, tournament_ID = 0, team_aID = 0, team_bID = 0, team_aScore = 0, team_bScore = 0;
        Timestamp matchTime = null;
        String state = null;
        try {
            String query = "SELECT * FROM olympics.Match WHERE id= ?";

            PreparedStatement pstmt = conn.getConn().prepareStatement(query);
            pstmt.setInt(1, matchID);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                match_ID = rs.getInt("id");
                tournament_ID = rs.getInt("tournament_id");
                team_aScore = rs.getInt("team_a_score");
                team_bScore = rs.getInt("team_b_score");
                matchTime = rs.getTimestamp("date");
                state = rs.getString("status");
                team_aID = rs.getInt("team_a_id");
                team_bID = rs.getInt("team_b_id");
            }
            String aTeamName = team.getTeamName(conn, team_aID);
            String bTeamName = team.getTeamName(conn, team_bID);
            String tName = game.returnTournamentName(conn, tournament_ID);

            System.out.printf("%-20s%-15s%-25s%-20s\n", "Tournament", "Match ID", "Date", "Status");
            System.out.printf("%-20s%-15s%-25s%-20s\n", tName, match_ID, matchTime, state);
            System.out.println("--------------------------------------------------------------------------");
            System.out.println("--------------------------------------------------------------------------");
            System.out.printf("%-20s%-7s\n", "Team", "Score");
            System.out.printf("%-20s%-7s\n", aTeamName, team_aScore);
            System.out.printf("%-7s\n", "vs");
            System.out.printf("%-20s%-7s\n", bTeamName, team_bScore);

        } catch (Exception e) {
            System.out.println("SQL exception occurred: " + e);
        }
    }

    public int getMatchCount(Connect conn, int tournamentID) {
        int matchCOUNT = 0;
        try {
            String sql = "SELECT COUNT(tournament_id) FROM olympics.Match WHERE tournament_id = ?";
            PreparedStatement pstmt = conn.getConn().prepareStatement(sql);
            pstmt.setInt(1, tournamentID);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                matchCOUNT = rs.getInt("COUNT(tournament_id)");
            }
        } catch (SQLException e) {
            System.out.println("SQL exeception occurred" + e);
        }
        return matchCOUNT;
    } // Returns how many matches are currently scheduled in a specific tournament

}
