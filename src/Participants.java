import java.util.Arrays;

import OlymGamesLocal.Countries;
import OlymGamesLocal.Participants;



public class Participants extends Countries {

	//const variables
	private final int MAX_PARTICIPANTS = 20;
    private final String[] header = {"ID", "FIRST NAME", "LAST NAME", "AGE", "COUNTRY"};
	
	//variables
	private int[] participantID;
    private String[] firstName;
    private String[] lastName;
    private int[] age;
    private String[] participants;
    private static int count;
    private String[] pCountry;
    
    public Participants() { //default constructor
    	init();
    }
    
    public void init() { //setting variables when the constructor is made
    	participantID = new int[MAX_PARTICIPANTS];
    	firstName = new String[MAX_PARTICIPANTS];
    	lastName = new String[MAX_PARTICIPANTS];
    	age = new int[MAX_PARTICIPANTS];
		participants = new String[MAX_PARTICIPANTS];
		pCountry = new String[TOTAL_COUNTRIES]; // 193 is temporary. Should be a final variable
		count = 0;
    }

    public void listParticipants(){ //list in order
    	for (String s : header) {
    		System.out.printf("%-12s ", s);
    	}
    	
		for (int j = 0; j < MAX_PARTICIPANTS; j++) { //probably not efficient way to code this
			if (participantID[j] != 0) {
				System.out.printf("\n%-12s ",participantID[j]);
				System.out.printf("%-12s ", firstName[j]);
				System.out.printf("%-12s ",lastName[j]);
				System.out.printf("%-12s ", age[j]);
				System.out.printf("%-12s", pCountry[j]);
			}
			else {
				j = MAX_PARTICIPANTS;
			}
		}
		
    }

    public void addParticipants(String lastN, String firstN, int ageN, String countryN){
    	participantID[count] = count + 1;
    	firstName[count] = firstN;
    	lastName[count] = lastN; 
    	age[count] = ageN;
    	pCountry[count] = countryN;
    	count++;  	
    }
    
    //GETTERS AND SETTERS
	public String[] getFirstName() {
		return firstName;
	}


	public void setFirstName(String[] firstName) {
		this.firstName = firstName;
	}

	public String[] getLastName() {
		return lastName;
	}

	public void setLastName(String[] lastName) {
		this.lastName = lastName;
	}

	public int[] getAge() {
		return age;
	}

	public void setAge(int[] age) {
		this.age = age;
	}

	public String[] getParticipants() {
		return participants;
	}

	public void setParticipants(String[] participants) {
		this.participants = participants;
	}
	//GETTERS AND SETTERS END
	
// testing
	public static void main(String[] args) {
		Participants obj = new Participants();
		obj.addParticipants("Susan", "Marry", 23, "PH");
		obj.addParticipants("Doe", "John", 21, "US");
		obj.addParticipants("Rey", "Adam", 11, "CA");
		obj.addParticipants("Boy", "Roy", 11, "JP");
		obj.listParticipants();
	}

}

// possible extra classes needed
// team(), for when the sport requires teams
// headToHead(), for when the sport only has 2 players