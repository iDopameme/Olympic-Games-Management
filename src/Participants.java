//
//	W.I.P
//	probably need to work out on how to output it not in an array
//

import java.util.Arrays;

public class Participants {
	//const variables just to test
	private final int MAX_PARTICIPANTS = 3;
	
	//variables
    private String firstName;
    private String lastName;
    private int age;
    private String[] participants;
    private static int count = 0;
    
    public Participants() { //default constructor
    	init();
    }
    
    public void init() { //setting variables when the constructor is made
		participants = new String[MAX_PARTICIPANTS];
    }

    public String listParticipants(){ //list the array if needed
		return Arrays.toString(participants);
    }

    public void addParticipants(String last, String first, int num){
    	firstName = first;
    	lastName = last; 
    	age = num;
    	if (participants[0] == null) { //if the first item is empty, then put the first participant in the first item
    		participants[0] = "FIRST NAME=" + firstName + " " + "LAST NAME=" + lastName + " AGE=" + age; 
    		count++; // helps changes to the next item in the array
    	}
    	else if (count < MAX_PARTICIPANTS){ // adding participants to the rest of the array 
    		participants[count] = "FIRST NAME=" + firstName + " " + "LAST NAME=" + lastName + " AGE=" + age;
    		count++;
    	}

    }
    
    //GETTERS AND SETTERS
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String[] getParticipants() {
		return participants;
	}

	public void setParticipants(String[] participants) {
		this.participants = participants;
	}
	//GETTERS AND SETTERS END
	
@Override
public String toString() {
	return Arrays.toString(participants); //outputs the participants in an array
}
	
// EXAMPLE OF IT WORKING IN THIS CLASS
//		this probably needs work so that we can put it into arguments instead?
//	public static void main(String[] args) {
//		Participants obj = new Participants();
//		obj.addParticipants("susan", "marry", 23);
//		obj.addParticipants("doe", "john", 21);
//		obj.addParticipants("rey", "adam", 11);
//		System.out.print(obj);
//	}

}
