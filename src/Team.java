public class Team{
	//private members
	private String teamName;
	private Participants players;
	
	
	public Team(String country) {
		teamName = country;
		init();
	}
	
	public void init() {
		players = new Participants();
	}
	
	public void newTeam(String country, Team team) { //setting up the new team
		Team newTeam = new Team(country);
		team = newTeam;
	}
	
	public void teamList() { //list all the players in the team
		System.out.println("\n  Team " + teamName);
	}
	
	public String getTeamName() {
		return teamName.toUpperCase();
	}

	
}
