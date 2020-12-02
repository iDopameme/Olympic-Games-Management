import Database.Connect;
import java.sql.*;

public class matchKnockout {
    // private members
    private int id_match;
    private int a_winner_of;
    private int b_winner_of;
    private int[] IDs;

    matchKnockout(){
        init();
    }

    public void init(){
        id_match = 0;
        a_winner_of = 0;
        b_winner_of = 0;
        IDs = new int[0];
    }

    public void setMatchKnockout(Connect conn, int id, int a_winner_match_id, int b_winner_match_id){
        try {
            String query = "INSERT INTO olympics.Match_Knockout" + "(match_id, winner_a_of, winner_b_of) VALUES" + "(?, ?, ?)";
            PreparedStatement pstmt = conn.getConn().prepareStatement(query);

            pstmt.setInt(1, id);
            pstmt.setInt(2, a_winner_match_id);
            pstmt.setInt(3, b_winner_match_id);
            pstmt.executeUpdate();

        } catch (Exception ex) {
            System.out.println("ERROR: " + ex.getMessage());
        }
    } // Completed -- Needs Testing
    // Sets all values in Match_Knockout table

    public int getKnockoutID(Connect conn, int a_winner_of, int b_winner_of) {
        init();
        try {
            String query = "SELECT match_id FROM olympics.Match_Knockout WHERE winner_a_of = ? && winner_b_of = ?";
            PreparedStatement pstmt = conn.getConn().prepareStatement(query);
            pstmt.setInt(1, a_winner_of);
            pstmt.setInt(2, b_winner_of);

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
    // Returns the ID of Match_Knockout

    public int[] getWinnerIDs(Connect conn, int knockoutID) {
        init();
        IDs = new int[2];
        try {
            String query = "SELECT winner_a_of, winner_b_of FROM olympics.Match_Knockout WHERE match_id = ?";
            PreparedStatement pstmt = conn.getConn().prepareStatement(query);
            pstmt.setInt(1, knockoutID);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                IDs[0] = rs.getInt("winner_a_of");
                IDs[1] = rs.getInt("winner_b_of");
            }
            rs.close();
        } catch (Exception ex) {
            System.out.println("ERROR :" + ex.getMessage());
        }
        return IDs;
    } // Completed -- Needs Testing
    // Returns all the Winner IDs in a single Match_Knockout Table from the passed match ID value.


}
