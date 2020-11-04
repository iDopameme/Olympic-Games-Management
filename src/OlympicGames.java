import Database.Connect;

public class OlympicGames {
	//arrays of teams for testing
	private static String[] teams = {"USA", "JAPAN"};
	private static String[] teams2 = {"ITALY", "KOREA"};
	
    public static void main(String[] args) {
        // Class Instances
        Countries country = new Countries();
        Tournament game = new Tournament();
        Sports sport = new Sports();
        Participants players = new Participants();
        
        //country testing
        // System.out.println(country.getCountryID("UNITED STATES OF AMERICA"));
        
        //setting sports
    	sport.setSports("Volleyball");
    	sport.setSports("Swimming");
    	System.out.println("============");
    	//setting participants
    	players.addParticipants("Mary", "Jane", 23, "PH");
    	players.addParticipants("McRoy", "Roy", 23, "CH");
    	
    	//this is details teams
        game.tournamentDetails(sport.getSportName(0), teams);
        
    	//this is details head to head sports
    	game.tournamentDetails(sport.getSportName(1), players);

    }
}
