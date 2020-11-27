import Database.Connect;
import com.mysql.cj.protocol.Resultset;

import java.sql.*;

public class Match {

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
            System.out.println("SQL exception occured: " + e);
        }
        return match_id;
    }

    public void returnAllMatches(Connect conn, int tournamentID) {
        int[] matchID = new int[2], team_aID = new int[2], team_bID = new int[2];
        int wLoop = 0;
        String[] teamA_name = new String[2], teamB_name = new String[2];
        Timestamp[] matchTime = new Timestamp[2];
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

            for (int i = 0; i < 2; i++) {
                pstmt.setInt(1, team_aID[i]);
                rs = pstmt.executeQuery();
                while (rs.next())
                    teamA_name[i] = rs.getString("team_name");
                pstmt.setInt(1, team_bID[i]);
                rs = pstmt.executeQuery();
                while (rs.next())
                    teamB_name[i] = rs.getString("team_name");
            }


            for (int i = 0; i < 2; i++) {
                System.out.println("ID: " + matchID[i] + " Time: " + matchTime[i]);
                System.out.println("Team_ID: " + team_aID[i] + " Name: " + teamA_name[i]);
                System.out.println("Team_ID: " + team_bID[i] + " Name: " + teamB_name[i]);
            }

        } catch (Exception e) {
            System.out.println("SQL exception occurred: " + e);
        }
    }

}
