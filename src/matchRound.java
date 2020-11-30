import Database.Connect;
import java.sql.*;

public class matchRound {
    // private members
    private int id_match;
    private int id_a_team;
    private int id_b_team;

    matchRound(){
        init();
    }

    public void init() {
        id_match = 0;
        id_a_team = 0;
        id_b_team = 0;
    }

    public void insertMatchRound(Connect conn, int id, int a_team, int b_team) {
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
    }

}
