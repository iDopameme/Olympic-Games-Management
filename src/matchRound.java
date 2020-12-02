import Database.Connect;
import java.sql.*;

public class matchRound {
    // private members
    private int id_match;
    private int id_a_team;
    private int id_b_team;
    private int[] IDs;

    matchRound(){
        init();
    }

    public void init() {
        id_match = 0;
        id_a_team = 0;
        id_b_team = 0;
        IDs = new int[0];
    }

    public void setMatchRound(Connect conn, int id, int a_team, int b_team) {
        try {
            String query = "INSERT INTO olympics.Match_Round" + "(match_id, team_a_id, team_b_id) VALUES" + "(?, ?, ?)";
            PreparedStatement pstmt = conn.getConn().prepareStatement(query);

            pstmt.setInt(1, id);
            pstmt.setInt(2, a_team);
            pstmt.setInt(3, b_team);
            pstmt.executeUpdate();

        } catch (Exception ex) {
            System.out.println("ERROR: " + ex.getMessage());
        }
    } // Completed -- Tested & Verified
    //Sets all values in a Match_Round Table

    public int getMatchRoundID(Connect conn, int a_team, int b_team) {
        init();
        try {
            String query = "SELECT match_id FROM olympics.Match_Round WHERE team_a_id = ? && team_b_id = ?";
            PreparedStatement pstmt = conn.getConn().prepareStatement(query);
            pstmt.setInt(1, a_team);
            pstmt.setInt(2, b_team);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                id_match = rs.getInt("match_id");
            }
            rs.close();
        } catch (Exception ex) {
            System.out.println("ERROR: " + ex.getMessage());
        }
        return id_match;
    } // Completed -- Needs Testing
    // Returns the Match Id in Match_Round table from passed int values of both team IDs

    public int[] getTeamIDs(Connect conn, int matchID) {
        init();
        IDs = new int[2];
        try {
            String query = "SELECT team_a_id, team_b_id FROM olympics.Match_Round WHERE match_id = ?";
            PreparedStatement pstmt = conn.getConn().prepareStatement(query);
            pstmt.setInt(1, matchID);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                IDs[0] = rs.getInt("team_a_id");
                IDs[1] = rs.getInt("team_b_id");
            }
            rs.close();
        } catch (Exception ex) {
            System.out.println("ERROR: " + ex.getMessage());
        }
        return IDs;
    } // Completed -- Needs Testing
    // Returns all the Team IDs in a single Match_Round Table from the pass Match ID value

}
