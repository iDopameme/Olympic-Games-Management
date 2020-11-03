import Database.Connect;
public class Tournament {
	//private members
    private Team usa;
    private Team jpn;
    private Time time;

	public Tournament() {
		init();
	}
	
	public void init() {
		usa = new Team("USA");
		jpn = new Team("JPN");
    	time = new Time();
	}
	
    public void tournamentDetails(String sport){
    	System.out.println("\n==============");
    	System.out.println("TOURNAMENT DETAILS");
    	System.out.println("==============");
    	
    	//sport
    	System.out.println("SPORT");
    	System.out.println(sport);
    	
    	//team
    	System.out.print("\nPLAYERS");
    	jpn.newTeam("JPN", jpn);
    	jpn.addToTeam("Thomson", "Jane", 54);
    	jpn.addToTeam("Don", "Ron", 12);
    	jpn.teamList();
    	usa.newTeam("USA", usa);
    	usa.addToTeam("Xu", "Perry", 23);
    	usa.teamList();
    	
    	//time
    	time.startTime(3,00); 
    	time.endTime(4,00);
    	System.out.print(time);
 
    	
    }
    public void playTournament(){

    }

    public void results(){

    }


}
