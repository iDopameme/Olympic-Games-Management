import java.util.*;

// WIP
//each new team will be its own instance


public class Team extends Participants{
	//private members
	private String teamName;
	private static int teamID = 0;
	private Participants players;
	private static int count1 = 0; //count for setting the country of each player
	
	
	public Team(String country) {
		teamName = country;
		init();
	}
	
	public void init() {
		participantID = new int[MAX_PARTICIPANTS];
		players = new Participants();
	}
	
	public void newTeam(String country, Team team) { //
		Team newTeam = new Team(country);
		count1 = 0;
		teamID = teamID + 1;
		team = newTeam;
	}
	
	public void addToTeam(String lastN, String firstN, int ageN) {
		players.addParticipants(lastN, firstN, ageN);
		players.pCountry[count1] = teamName;
		count1++;
	}
	
	public void teamList() {
		System.out.println("\n\n" + (teamID) + " TEAM " + teamName);
		players.listParticipants();
	}
	
	public void listAllTeams() { // just an idea
		
	}
	
}
