//each new team will be its own instance

//import Local.Participants;
//import Local.Team;

public class Team{
	//private members
	private String teamName;
	private Participants players;
	private static int count1 = 0; //count for setting the country of each player
	private static int teamID = 0;
	
	
	public Team(String country) {
		teamName = country;
		init();
	}
	
	public void init() {
		players = new Participants();
	}
	
	public void newTeam(String country, Team team) { //setting up the new team
		Team newTeam = new Team(country);
		count1 = 0;
		teamID = teamID + 1;
		team = newTeam;
	}
	
	public void addToTeam(String lastN, String firstN, int ageN) { //adding players to the team
		players.addParticipants(lastN, firstN, ageN);
		players.pCountry[count1] = teamName;
		count1++;
	}
	
	public void teamList() { //list all the players in the team
		System.out.println("\n" + (teamID) + " TEAM " + teamName);
//		players.listParticipants();
	}
	
	public String getTeamName() {
		return teamName.toUpperCase();
	}

	
}
