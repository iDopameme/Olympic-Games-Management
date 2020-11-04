import Database.Connect;

import java.io.File;
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

    public void listParticipants(){ //list in order
    	for (String s : header) {
    		System.out.printf("%-12s ", s);
    	}
		System.out.println();
		try {
			database.startConn();
			Statement stmt = database.getConn().createStatement();
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
		database.endConn();
		
    }

//    public void addParticipants(String lastN, String firstN, int ageN, String countryN){
//    	participantID[count] = count + 1;
//    	firstName[count] = firstN;
//    	lastName[count] = lastN;
//    	age[count] = ageN;
//    	pCountry[count] = countryN;
//    	count++;
//    }

	public void addAllParticipants() {
    	try {
    		database.startConn();
    		String query = "INSERT INTO olympics.participants" + "(playerID, firstName, lastName, age, country) VALUES" + "(?, ?, ?, ?, ?);";
			PreparedStatement pstmt = database.getConn().prepareStatement(query);

			Scanner sc = new Scanner(new File("C:\\Olympic-Games-Management\\src\\csvFiles\\players.csv"));
			sc.useDelimiter("[,\\n]"); // Delimiter skips comma and new line

			int id, playerAge;
			String first, last, c;

			while (sc.hasNext()) {
				id = sc.nextInt();
				first = sc.next();
				last = sc.next();
				playerAge = sc.nextInt();
				c = sc.next();

				pstmt.setInt(1, id);
				pstmt.setString(2, first);
				pstmt.setString(3, last);
				pstmt.setInt(4, playerAge);
				pstmt.setString(5, c);
				pstmt.executeUpdate();
				System.out.println("ID: " + id + " First Name: " + first + " Last Name: " + last + " Age: " + playerAge + " Country: " + c);
			}
			System.out.println("All values successfully inserted into the table.");
		} catch (Exception ex)
		{
			System.out.println("ERROR: " + ex.getMessage());
		}
    	database.endConn();
	}
    
    public void addParticipants(String lastN, String firstN, int ageN){ //overload for teams
    	participantID[count] = count + 1;
    	firstName[count] = firstN;
    	lastName[count] = lastN; 
    	age[count] = ageN;
    	count++;  	
    }
    
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
