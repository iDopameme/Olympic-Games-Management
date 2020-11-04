import Database.Connect;
public class Tournament {
	//private members
	private Team[] team;
	private Time time;
	
    public void tournamentDetails(String sport, String[] teams){ //for team sports
    	time = new Time();
    	System.out.println("\n==============");
    	System.out.println(sport.toUpperCase() + "\nTOURNAMENT DETAILS");
    	System.out.println("==============");
    	team = new Team[teams.length];
		for (int i = 0; i<teams.length; i++) {
			team[i] = new Team(teams[i]);
		}
    	for(int i = 0; i<2; i++) {
    		team[i].newTeam(team[i].getTeamName(), team[i]);
    		team[i].teamList();
    	}
    	System.out.println(); 
    	time.startTime(5, 30);
    	time.endTime(6, 30);
    	System.out.println(time);
    	System.out.println();    	
    }
    
    public void tournamentDetails(String sport, Participants players) { //for head to head sports
    	System.out.println("\n==============");
    	System.out.println(sport.toUpperCase() + "\nTOURNAMENT DETAILS");
    	System.out.println("==============");
    	players.listParticipants();
    	System.out.println(); 
    	time.startTime(5, 30);
    	time.endTime(6, 30);
    	System.out.println(time);
    	System.out.println(); 
    }
    
    public void playTournament(){

    }
    
    public void results(){

    }
}
