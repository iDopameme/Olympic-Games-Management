import Database.Connect;
import java.sql.*;

public class matchKnockout {
    // private members
    private int id_match;
    private int a_winner_of;
    private int b_winner_of;

    matchKnockout(){
        init();
    }

    public void init(){
        id_match = 0;
        a_winner_of = 0;
        b_winner_of = 0;
    }

    public void insertMatchKnockout(Connect conn, int id, int a_winner_match_id, int b_winner_match_id){
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
    }

}
