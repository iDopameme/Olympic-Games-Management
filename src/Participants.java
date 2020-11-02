//
//	W.I.P

import java.util.Arrays;
import java.util.ArrayList;
import Database.Connect;


public class Participants extends Countries {

	//const variables
	private final int MAX_PARTICIPANTS = 3; // just for testing
    private String[] header = {"ID", "FIRST NAME", "LAST NAME", "AGE"}; // ???
	
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
		pCountry = new String[TOTAL_COUNTRIES];
		count = 0;
    }

    public void listParticipants(){ //list in order
		for (String s : header) {
			System.out.printf("%-12s ", s);
		}
		for (int j = 0; j < MAX_PARTICIPANTS; j++) {
			System.out.printf("\n%-12s ",participantID[j]);
			System.out.printf("%-12s ", firstName[j]);
			System.out.printf("%-12s ",lastName[j]);
			System.out.printf("%-12s ", age[j]);
		}
    }

    public void addParticipants(String lastN, String firstN, int ageN){
    	participantID[count] = count + 1;
    	firstName[count] = firstN;
    	lastName[count] = lastN; 
    	age[count] = ageN;
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
//	public static void main(String[] args) {
//		Participants obj = new Participants();
//		obj.addParticipants("susan", "marry", 23);
//		obj.addParticipants("doe", "john", 21);
//		obj.addParticipants("rey", "adam", 11);
//		obj.listParticipants();
//	}

}
