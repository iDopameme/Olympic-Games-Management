import Database.Connect;
import java.sql.*;



public class Match {

    //private members
    private int[] matchIDs;
    private String teamName;
    private int tournamentID;

    Match(){
        init();
    }

    public void init() {
        matchIDs = new int[0];
        teamName = null;
        tournamentID = 0;
    }

    public boolean createMatch(Connect conn, int id_tournament, int id_teamA, int id_teamB, Timestamp gameTime, String state) {
        boolean validation = false;
        try {
            String query = "INSERT INTO olympics.Match" + "(tournament_id, team_a_id, team_b_id, date, status) VALUES" + "(?, ?, ?, ?, ?);";
            PreparedStatement pstmt = conn.getConn().prepareStatement(query);

            pstmt.setInt(1, id_tournament);
            pstmt.setInt(2, id_teamA);
            pstmt.setInt(3, id_teamB);
            pstmt.setTimestamp(4, gameTime);
            pstmt.setString(5, state);
            pstmt.executeUpdate();

            validation = true;
        } catch (Exception ex) {
            System.out.println("ERROR: " + ex.getMessage());
            validation = false;
        }
        return validation;
    } // Completed -- Tested & Confirmed!
    // creates a match from scratch

    public void setMatchScores(Connect conn, int id_match, int a_score, int b_score){
        try {
            String query = "UPDATE olympics.Match SET team_a_score = ?, team_b_score = ? WHERE id = ?";
            PreparedStatement pstmt = conn.getConn().prepareStatement(query);

            pstmt.setInt(1, a_score);
            pstmt.setInt(2, b_score);
            pstmt.setInt(3, id_match);
            pstmt.executeUpdate();

        } catch (Exception ex) {
            System.out.println("ERROR: " + ex.getMessage());
        }
    } // Completed -- Needs testing
    // Sets the scores the user submitted to the table

    public void setDate(Connect conn, int id_match, Timestamp dateTime) {
        try {
            String query = "UPDATE olympics.`Match` SET date = ? WHERE id = ?";
            PreparedStatement pstmt = conn.getConn().prepareStatement(query);

            pstmt.setTimestamp(1, dateTime);
            pstmt.setInt(2, id_match);
            pstmt.executeUpdate();

        } catch (Exception ex) {
            System.out.println("ERROR: " + ex.getMessage());
        }
    } // Completed -- Needs testing
    // Sets the Timestamp the user submitted

    public void setMatchStatus(Connect conn, int id, String state) {
        try {
            String query = "UPDATE olympics.`Match` SET status = ? WHERE id = ?";
            PreparedStatement pstmt = conn.getConn().prepareStatement(query);

            pstmt.setString(1, state);
            pstmt.setInt(2, id);
            pstmt.executeUpdate();

        } catch (Exception ex) {
            System.out.println("ERROR: " + ex.getMessage());
        }
    } // Completed -- Needs testing
    // Changes match status

    public int determineWinner(Connect conn, int id_match) {
        int aTeam = 0, bTeam = 0, a_id = 0, b_id = 0;
        int winner = 0;
        try {
            String query = "SELECT team_a_score, team_b_score, team_a_id, team_b_id FROM olympics.`Match` WHERE id = ?";
            PreparedStatement pstmt = conn.getConn().prepareStatement(query);
            pstmt.setInt(1, id_match);
            ResultSet rs = pstmt.executeQuery();

            while(rs.next()) {
                aTeam = rs.getInt("team_a_score");
                bTeam = rs.getInt("team_b_score");
                a_id = rs.getInt("team_a_id");
                b_id = rs.getInt("team_b_id");
            }

            if (aTeam > bTeam)
                winner = a_id;
            else if (aTeam < bTeam)
                winner = b_id;
            else
                System.out.println("Draw...No winner determined");



        } catch (Exception ex) {
            System.out.println("ERROR: " + ex.getMessage());
        }
        return winner;
    } // WIP 80% -- Needs testing
    // Checks the match scores to determine who the winner is and returns the ID of the winner.

    public int[] getMatchIDs(Connect conn, int tournamentID) {
        matchIDs = new int[getMatchCount(conn, tournamentID)];
        int i = 0;
        try {
            String query = "SELECT id FROM olympics.Match WHERE tournament_id = ?";
            PreparedStatement pstmt = conn.getConn().prepareStatement(query);
            pstmt.setInt(1, tournamentID);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()) {
                matchIDs[i] = rs.getInt("id");
                i++;
            }
            rs.close();
        } catch (Exception e) {
            System.out.println("SQL exception occurred: " + e);
        }
        return matchIDs;
    } // Completed -- Tested!
    // Returns an int array of all the Match IDs associated with a tournament

    public int getTournamentIDFromMatch(Connect conn, int matchID) {
        tournamentID = 0;
        try {
            String query = "SELECT tournament_id FROM olympics.Match WHERE id = ?";
            PreparedStatement pstmt = conn.getConn().prepareStatement(query);
            pstmt.setInt(1, matchID);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()) {
                tournamentID = rs.getInt("tournament_id");
            }
            rs.close();
        } catch (Exception e) {
            System.out.println("SQL Exception occurred: " + e);
        }
        return tournamentID;
    } // Completed -- Tested!
    // Returns the tournament associated with a passed MatchID

    public void outputAllMatchesFromTournament(Connect conn, int tournamentID) {
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
    } // Completed
    // Outputs all matches in a tournament based on passed tournamentID value

    public void outputExactMatch(Connect conn, int matchID) {
        int match_ID = 0, tournament_ID = 0, team_aID = 0, team_bID = 0, team_aScore = 0, team_bScore = 0;
        String team_a_name = "", team_b_name = "", tName = "";
        Timestamp matchTime = null;
        String state = null;
        try {
            String query = "SELECT * FROM olympics.Match WHERE id= ?";
            String query2 = "SELECT team_name FROM olympics.Team WHERE id = ?";
            String query3 = "SELECT tournament_name FROM olympics.Tournament WHERE id = ?";

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

            pstmt = conn.getConn().prepareStatement(query2);
            pstmt.setInt(1, team_aID);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                team_a_name = rs.getString("team_name");
            }

            pstmt.setInt(1, team_bID);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                team_b_name = rs.getString("team_name");
            }

            pstmt = conn.getConn().prepareStatement(query3);
            pstmt.setInt(1, tournament_ID);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                tName = rs.getString("tournament_name");
            }


            System.out.printf("%-20s%-15s%-25s%-20s\n", "Tournament", "Match ID", "Date", "Status");
            System.out.printf("%-20s%-15s%-25s%-20s\n", tName, match_ID, matchTime, state);
            System.out.println("--------------------------------------------------------------------------");
            System.out.println("--------------------------------------------------------------------------");
            System.out.printf("%-12s%-30s%-7s\n", "Side", "Team", "Score");
            System.out.printf("%-12s%-30s%-7s\n", "[A]", team_a_name, team_aScore);
            System.out.printf("%-7s\n", "vs");
            System.out.printf("%-12s%-30s%-7s\n", "[B]", team_b_name, team_bScore);

        } catch (Exception e) {
            System.out.println("SQL exception occurred: " + e);
        }
    } // Completed -- Do not Test, ignore...
    // Outputs full details of a match

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
    } // Completed -- Tested & Confirmed!
    // Returns how many matches are currently scheduled in a specific tournament



}
