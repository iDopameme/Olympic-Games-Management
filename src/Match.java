import Database.Connect;

import javax.xml.transform.Result;
import java.sql.*;



public class Match {

    //private members
    private int[] matchIDs;
    private int tournamentID, teamAID, teamBID;
    private Timestamp dateTime;
    private String state;

    Match(){
        init();
    }

    public void init() {
        matchIDs = new int[0];
        tournamentID = 0;
        teamAID = 0;
        teamBID = 0;
        dateTime = null;
        state = "";
    }

    public void createMatch(Connect conn, int id_tournament, int id_teamA, int id_teamB, Timestamp gameTime, String state) {
        try {
            String query = "INSERT INTO olympics.Match" + "(tournament_id, team_a_id, team_b_id, date, status) VALUES" + "(?, ?, ?, ?, ?);";
            PreparedStatement pstmt = conn.getConn().prepareStatement(query);

            pstmt.setInt(1, id_tournament);
            pstmt.setInt(2, id_teamA);
            pstmt.setInt(3, id_teamB);
            pstmt.setTimestamp(4, gameTime);
            pstmt.setString(5, state);
            pstmt.executeUpdate();

        } catch (Exception ex) {
            System.out.println("ERROR: " + ex.getMessage());
        }
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
    } // Completed -- Tested & Confirmed
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
    } // Completed -- Tested & Confirmed
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
    } // Completed -- Tested & Confirmed
    // Changes match status

    public void deleteMatch(Connect conn, int id_match) {
        try {
            String query = "DELETE FROM olympics.Team WHERE id = ?";
            PreparedStatement pstmt = conn.getConn().prepareStatement(query);

            pstmt.setInt(1, id_match);
            pstmt.executeUpdate();
        } catch (Exception ex) {
            System.out.println("ERROR: "  + ex.getMessage());
        }
    }

    public void deleteAllMatches(Connect conn, int id_tournament){
        try {
            String query = "DELETE FROM olympics.Team WHERE tournament_id = ?";
            PreparedStatement pstmt = conn.getConn().prepareStatement(query);

            pstmt.setInt(1, id_tournament);
            pstmt.executeUpdate();
        } catch (Exception ex) {
            System.out.println("ERROR: " + ex.getMessage());
        }
    }

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
    } // WIP 80% -- Tested & Confirmed
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
    } // Completed -- Tested & Confirmed
    // Returns an int array of all the Match IDs associated with a tournament

    public int getTournamentIDFromMatch(Connect conn, int matchID) {
        init();
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
    } // Completed -- Tested & Confirmed
    // Returns the tournament associated with a passed MatchID

    public Timestamp getDate(Connect conn, int matchID){
        init();
        try {
            String query = "SELECT date FROM olympics.`Match` WHERE id = ?";
            PreparedStatement pstmt = conn.getConn().prepareStatement(query);
            pstmt.setInt(1, matchID);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()) {
                dateTime = rs.getTimestamp("date");
            }
        } catch (Exception e) {
            System.out.println("SQL Exception occurred: " + e);
        }
        return dateTime;
    } // Completed -- Needs Testing
    // Returns the date of the match

    public String getStatus(Connect conn, int matchID){
        init();
        try {
            String query = "SELECT status FROM olympics.`Match` WHERE id = ?";
            PreparedStatement pstmt = conn.getConn().prepareStatement(query);
            pstmt.setInt(1, matchID);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()) {
                state = rs.getString("status");
            }
        } catch (Exception e) {
            System.out.println("SQL Exception occurred: " + e);
        }
        return state;
    } // Completed -- Needs Testing
    // Returns the status of the match

    public int getTeam_a_id(Connect conn, int matchID){
        init();
        try {
            String query = "SELECT team_a_id FROM olympics.`Match` WHERE id = ?";
            PreparedStatement pstmt = conn.getConn().prepareStatement(query);
            pstmt.setInt(1, matchID);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()) {
                teamAID = rs.getInt("team_a_id");
            }
        } catch (Exception e) {
            System.out.println("SQL Exception occurred: " + e);
        }
        return teamAID;
    } // Completed
    // Returns the ID of team A

    public int getTeam_b_id(Connect conn, int matchID){
        init();
        try {
            String query = "SELECT team_b_id FROM olympics.`Match` WHERE id = ?";
            PreparedStatement pstmt = conn.getConn().prepareStatement(query);
            pstmt.setInt(1, matchID);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()) {
                teamBID = rs.getInt("team_b_id");
            }
        } catch (Exception e) {
            System.out.println("SQL Exception occurred: " + e);
        }
        return teamBID;
    } // Completed
    // Returns the ID of team B

    public int getMatchCount(Connect conn, int tournamentID) {
        init();
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

    public boolean preventDuplicate(Connect conn, int tournamentID, int duplicateID) {
        init();
        try {
            String sql = "SELECT team_a_id, team_b_id FROM olympics.`Match` WHERE tournament_id = ?";
            PreparedStatement pstmt = conn.getConn().prepareStatement(sql);
            pstmt.setInt(1, tournamentID);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                teamAID = rs.getInt("team_a_id");
                teamBID = rs.getInt("team_b_id");
                if ((duplicateID == teamAID) || (duplicateID == teamBID)){
                    return true;
                }
            }
        } catch (SQLException e) {
            System.out.println("SQL exception occurred" + e);
        }
        return false;
    } // Completed -- Testing...
    // Checks if the user submitted a duplicate team that is another in a different match
    // This function is only used during match creation & modification

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
    } // Completed  -- Tested & Confirmed
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





}
