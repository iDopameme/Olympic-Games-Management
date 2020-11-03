import Database.Connect;

public class OlympicGames {
    public static void main(String[] args) {
        // Class Instances
        Countries country = new Countries();
        Tournament game = new Tournament();
        Sports sport = new Sports();
        
        //country testing
        //System.out.println(country.getCountryID("Belgium"));
        
        //setting sports
    	sport.setSports("Volleyball");
    	sport.setSports("Swimming");
    	
        //put sport into tournament so each sport has details
    	//only 2 for now since i set only 2 sports
    	for(int i = 0; i<2; i++) {
            game.tournamentDetails(sport.getSportName(i));
    	}
    	
    	/* the problem is:
    	 * how do you set different participants for each sport?
    	 * */

    }
}
