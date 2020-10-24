import java.util.Date;
import java.util.Calendar;
import java.util.Random;

public class Tournament {
	//constants
	private final int DEFAULT_SECONDS = 0;
	private final int[] DEFAULT_MINUTES = {0, 30};
	
	//private members
    private int year;
    private int month;
    private int day;
    private int highDay;
    private int lowDay;
    private int hour;
    private int minutes;
    private int seconds;
    private Calendar calendar;
    private Random random;
    private Participants players;
    private Date startTime;
    private Date endTime;
    private String firstPlace;
    private String secondPlace;
    private String thirdPlace;
    private int[] scores;

	public Tournament() {
		init();
	}
	
	public void init() {
		//setting objects
    	calendar = Calendar.getInstance();
    	random = new Random();
    	players = new Participants();

    	//setting variables
    	year = calendar.get(Calendar.YEAR);
    	month = calendar.get(Calendar.MONTH);
    	highDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH); //get the maximum of days of current month
    	lowDay = calendar.get(Calendar.DAY_OF_MONTH); //get todays date
    	day = random.nextInt(highDay-lowDay) + lowDay; // this will print out a random date between today and the end of this month
    	hour = 4; //i realized i did not randomize the hour :(
    	minutes = DEFAULT_MINUTES[(random.nextInt(DEFAULT_MINUTES.length))];
    	seconds = DEFAULT_SECONDS;
	}
	
    public void tournamentDetails(){
    	System.out.print("PLAYERS\n");
    	players.listParticipants(); //participants test
    	
    	System.out.print("\n\nDATE AND TIME\n");
    	//getting the random start time
    	calendar.set(year, month, day, hour, minutes, seconds);
    	startTime = calendar.getTime();
    	//getting the random end time
    	calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) + 2); // setting the duration of game to 2 or 2 1/2 hrs for now to test
    	minutes = DEFAULT_MINUTES[(random.nextInt(DEFAULT_MINUTES.length))]; //randomizing minutes again...
    	calendar.set(Calendar.MINUTE, minutes);
    	endTime = calendar.getTime();
    	
    	System.out.print("Start time: " + startTime);
    	System.out.print("\nEnd time: " + endTime);
    	
    	
    }

    public void playTournament(){

    }

    public void results(){

    }

    public int getSportName(int id){ // Needs work
        return getSportName(id);
    }


}
