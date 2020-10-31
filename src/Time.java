
import java.util.Date;
import java.util.Calendar;
import java.util.Random;
import Database.Connect;

//// wip
///// NEED TO FIX THE HOURS + make it cleaner coz it doesnt randomize all the hours between now and the end of the day

public class Time {
	//constants
	private final int DEFAULT_SECONDS = 0;
	private final int[] DEFAULT_MINUTES = {0, 30};
	
	//private members
    private int year;
    private int month;
    private int day;
    private int highDay;
    private int lowDay;
    private static int hourHigh;
    private  static int hourLow;
    private int hour;
    private int minutes;
    private int seconds;
    private Calendar calendar;
    private Random random;
    private Date startTime;
    private Date endTime;
    
    public Time() {
    	init();
    }
    
	public void init() {
    	calendar = Calendar.getInstance();
    	random = new Random();
    	year = calendar.get(Calendar.YEAR);
    	month = calendar.get(Calendar.MONTH);
    	highDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH); //get the maximum of days of current month
    	lowDay = calendar.get(Calendar.DAY_OF_MONTH); //get todays date
    	day = random.nextInt(highDay-lowDay) + lowDay; // this will print out a random date between today and the end of this month
    	hourHigh = calendar.getActualMaximum(Calendar.HOUR); // max hours in a day
    	hourLow = calendar.get(Calendar.HOUR); // get hour right now
    	hour = random.nextInt(hourHigh - hourLow) + hourHigh;
    	minutes = DEFAULT_MINUTES[(random.nextInt(DEFAULT_MINUTES.length))];
    	seconds = DEFAULT_SECONDS;
	}
	
	public void startTime() {
    	//getting the random start time
		if(hour > 12) { // if the time is more than 12AM/PM eg. 13:00
			hour = hour - 12;
		}
    	calendar.set(year, month, day, hour, minutes, seconds);
    	startTime = calendar.getTime();
	}
	
	public void endTime() {
		if(calendar.get(Calendar.HOUR_OF_DAY) + 2 > 12) { // if the time is more than 12AM/PM eg. 13:00
			calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) + 2 - 12);
		}
		else {
			calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) + 2);
		} 
    	minutes = DEFAULT_MINUTES[(random.nextInt(DEFAULT_MINUTES.length))]; //randomizing minutes again...
    	calendar.set(Calendar.MINUTE, minutes);
    	endTime = calendar.getTime();
	}
	
	@Override
	public String toString() {
		return "DATE AND TIME\n"+
				"START TIME: " + startTime +
				"\nEND TIME: " + endTime;
	}
	
	/////////
	//TESTING
	////////
	
//	public static void main(String[] args) {
//		Time time = new Time();
//		time.startTime();
//		time.endTime();
//		System.out.print(time);
//	}
//	
}
