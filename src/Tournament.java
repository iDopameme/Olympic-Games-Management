import java.util.Date;
import java.util.Calendar;
import java.util.Random;
import java.util.ArrayList;
import Database.Connect;

public class Tournament {

	//private members
    private Participants players;
    private Time time;
    private String firstPlace;
    private String secondPlace;
    private String thirdPlace;
    private int[] scores;

	public Tournament() {
		init();
	}
	
	public void init() {
    	players = new Participants();
    	time = new Time();
	}
	
    public void tournamentDetails(){
    	//participants test
    	System.out.print("PLAYERS\n");
    	players.listParticipants();
    	
    	//time test
    	time.startTime(); 
    	time.endTime();
    	System.out.print(time);
    }

    public void playTournament(){

    }

    public void results(){

    }


}
