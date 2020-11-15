import Database.Connect;
import java.io.File;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class Participants extends Countries {

	//const variables
	protected final int MAX_PARTICIPANTS = 20;
    private final String[] header = {"ID", "FIRST NAME", "LAST NAME", "AGE", "COUNTRY"};
    Connect database;
	
	//variables
	protected int[] participantID;
    protected String[] firstName;
    protected String[] lastName;
    protected int[] age;
    protected static int count;
    protected String[] pCountry;
    
    public Participants() { //default constructor
    	init();
    }
    
    public void init() {
    	participantID = new int[MAX_PARTICIPANTS];
    	firstName = new String[MAX_PARTICIPANTS];
    	lastName = new String[MAX_PARTICIPANTS];
    	age = new int[MAX_PARTICIPANTS];
		pCountry = new String[TOTAL_COUNTRIES]; // 193 is temporary. Should be a final variable
		count = 0;
		database = new Connect();
    }

    public void listParticipants(Connect conn) throws IOException { //list in order
    	for (String s : header) {
    		System.out.printf("%-12s ", s);
    	}
		System.out.println();
		try {
			Statement stmt = conn.getConn().createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM olympics.participants");

			while (rs.next()) {
				int id = rs.getInt("playerID");
				String firstN = rs.getString("firstName");
				String lastN = rs.getString("lastName");
				int Age = rs.getInt("age");
				String Country = rs.getString("country");
				System.out.println(id + "             " + firstN + "     " + lastN + "    " + Age + "              " + Country);
			}
		} catch(SQLException e) {
			System.out.println("SQL exception occurred" + e);
		}
    }

    public void listParticipants(String sport, Connect conn) {
		for (String s : header) {
			System.out.printf("%-12s ", s);
		}
		System.out.println();
		try {
			String sql = "SELECT playerID, firstName, lastName, age, country FROM olympics.participants WHERE playerSport = ?";
			PreparedStatement pstmt = conn.getConn().prepareStatement(sql);
			pstmt.setString(1, sport);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				int id = rs.getInt("playerID");
				String firstN = rs.getString("firstName");
				String lastN = rs.getString("lastName");
				int Age = rs.getInt("age");
				String Country = rs.getString("country");
				System.out.println(id + "             " + firstN + "     " + lastN + "    " + Age + "              " + Country);
			}
		} catch(SQLException e) {
			System.out.println("SQL exception occurred" + e);
		}
	}

	public String[] getPlayerName(int pick, Connect conn) {
		String firstN = null;
		String lastN = null;
		try {
			String sql = "SELECT firstName, lastName FROM olympics.participants WHERE playerID = ?";
			PreparedStatement pstmt = conn.getConn().prepareStatement(sql);
			pstmt.setInt(1, pick);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				firstN = rs.getString("firstName");
				lastN = rs.getString("lastName");
			}
		} catch (SQLException e) {
			System.out.println("SQL exception occurred" + e);
		}
		return new String[] {firstN, lastN};
	}

	public String[] getPlayerName(int pick, String sport, Connect conn){
		String firstN = null;
		String lastN = null;
		try {
			String sql = "SELECT firstName, lastName FROM olympics.participants WHERE (playerID, playerSport) = (?, ?) ";
			PreparedStatement pstmt = conn.getConn().prepareStatement(sql);
			pstmt.setInt(1, pick);
			pstmt.setString(2, sport);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				firstN = rs.getString("firstName");
				lastN = rs.getString("lastName");
			}
		} catch (SQLException e) {
			System.out.println("SQL exception occurred" + e);
		}
		return new String[] {firstN, lastN};
	}

	public void addAllParticipants(Connect conn) throws IOException {
    	try {
    		String query = "INSERT INTO olympics.participants" + "(playerID, firstName, lastName, age, country, playerSport) VALUES" + "(?, ?, ?, ?, ?, ?);";
			PreparedStatement pstmt = conn.getConn().prepareStatement(query);

			Scanner sc = new Scanner(new File("C:\\Olympic-Games-Management\\src\\csvFiles\\players v2.csv"));
			sc.useDelimiter("[,\\n]"); // Delimiter skips comma and new line

			int id, playerAge;
			String first, last, c, sport;

			while (sc.hasNext()) {
				id = sc.nextInt();
				first = sc.next();
				last = sc.next();
				playerAge = sc.nextInt();
				c = sc.next();
				sport = sc.next();

				pstmt.setInt(1, id);
				pstmt.setString(2, first);
				pstmt.setString(3, last);
				pstmt.setInt(4, playerAge);
				pstmt.setString(5, c);
				pstmt.setString(6, sport);
				pstmt.executeUpdate();
				System.out.println("ID: " + id + " First Name: " + first + " Last Name: " + last + " Age: " + playerAge + " Country: " + c + "Sport: " + sport);
			}
			System.out.println("All values successfully inserted into the table.");
		} catch (Exception ex)
		{
			System.out.println("ERROR: " + ex.getMessage());
		}
	}
    
    public void addParticipants(String lastN, String firstN, int ageN){ //overload for teams
    	participantID[count] = count + 1;
    	firstName[count] = firstN;
    	lastName[count] = lastN; 
    	age[count] = ageN;
    	count++;  	
    }

	//    public void addParticipants(String lastN, String firstN, int ageN, String countryN){
//    	participantID[count] = count + 1;
//    	firstName[count] = firstN;
//    	lastName[count] = lastN;
//    	age[count] = ageN;
//    	pCountry[count] = countryN;
//    	count++;
//    }

// testing
//	public static void main(String[] args) {
//		Participants obj = new Participants();
//		obj.addParticipants("Susan", "Marry", 23, "PH");
//		obj.addParticipants("Doe", "John", 21, "US");
//		obj.addParticipants("Rey", "Adam", 11, "CA");
//		obj.addParticipants("Boy", "Roy", 11, "JP");
//		obj.listParticipants();
//	}

}
