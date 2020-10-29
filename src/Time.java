import java.util.Date;
import java.util.Calendar;
import java.util.Random;

//// wip
public class Time {
	//constants
	private final int DEFAULT_SECONDS = 0;
	
	//private members
    private int year;
    private int month;
    private int day;
    private int highDay;
    private int lowDay;
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
    	highDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH) + 1; //get the maximum of days of current month
    	lowDay = calendar.get(Calendar.DAY_OF_MONTH); //get todays date
    	day = random.nextInt(highDay-lowDay) + lowDay; // this will print out a random date between today and the end of this month
    	seconds = DEFAULT_SECONDS;
	}
	
	public void startTime(int startHour, int startMinutes) {
    	calendar.set(year, month, day, startHour, startMinutes, seconds);
    	startTime = calendar.getTime();
	}
	
	public void endTime(int endHour, int endMinutes) {
		calendar.set(Calendar.HOUR_OF_DAY, endHour);
    	calendar.set(Calendar.MINUTE, endMinutes);
    	endTime = calendar.getTime();
	}
	
	@Override
	public String toString() {
		return "\n\nDATE AND TIME\n"+
				"START TIME: " + startTime +
				"\nEND TIME: " + endTime;
	}
	
	/////////
	//TESTING
	////////
	
//	public static void main(String[] args) {
//		Time time = new Time();
//		time.startTime(6, 30);
//		time.endTime(6, 40);
//		System.out.print(time);
//	}
	
}
