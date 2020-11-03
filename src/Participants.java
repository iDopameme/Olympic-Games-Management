import java.util.*;

public class Participants extends Countries {

	//const variables
	protected final int MAX_PARTICIPANTS = 20;
    private final String[] header = {"ID", "FIRST NAME", "LAST NAME", "AGE", "COUNTRY"};
	
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
    }

    public void listParticipants(){ //list in order
    	for (String s : header) {
    		System.out.printf("%-12s ", s);
    	}
    	
		for (int j = 0; j < MAX_PARTICIPANTS; j++) {
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
