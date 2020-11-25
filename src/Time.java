import java.io.File;
import java.sql.PreparedStatement;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Scanner;
import java.util.Calendar;
import java.sql.Timestamp;
import Database.Connect;

import javax.swing.plaf.synth.SynthTextAreaUI;

public class Time {
	//constants
	private final int DEFAULT_SECONDS = 0;
	
	//private members
    private int year;
    private int month;
    private int day;
    private int hour;
    private int minutes;
    private int seconds;
    private boolean canSet;
    
    //class instances
    private Calendar calendar;
    private Date startTime;
    private Date endTime;
	Timestamp timestamp;
	Scanner input = new Scanner(System.in);
    
    public Time() {
    	init();
    }
    
	public void init() {
    	calendar = new GregorianCalendar();
    	year = 0;
    	month = 0;
    	day = 0;
    	hour = 0;
    	minutes = 0;
    	seconds = 0;
    	canSet = true;
	}

	public Timestamp setTime() {
    	boolean check = false;
    	boolean finalDate;

    	do {
			System.out.println("========================");
			System.out.println("Enter the year: (yyyy)");
			year = input.nextInt();
			System.out.println("Enter the month: (mm)");
			month = input.nextInt();
			System.out.println("Enter the day: (dd)");
			day = input.nextInt();
			System.out.println("Enter the hour: (00)\n24 Hour format:");
			hour = input.nextInt();
			System.out.println("Enter the minutes: (00):");
			minutes = input.nextInt();
			System.out.println("Enter the seconds: (00):");
			seconds = input.nextInt();
			System.out.println("========================");
			System.out.println("Here's the date & time you selected: ");
			System.out.println(year + "-" + month + "-" + day + " " + hour + ":" + minutes + ":" + seconds);
			System.out.println("Submit this time?\n [false = No, true = yes]");
			finalDate = input.hasNext();
		} while (!finalDate);

		month -= 1;
		hour -= 5;
    	calendar.set(year, month, day, hour, minutes, seconds);
		long userTime = calendar.getTimeInMillis();
		timestamp = new Timestamp(userTime);

    	return timestamp;
	}

	public void startTime() {
		canSet = false;
		System.out.println("=====================");
		System.out.println("Please print out the month and date the tournament will start:");
		
			System.out.print("Month:");
			int monthInput = input.nextInt();
			if(monthInput > calendar.getActualMaximum(Calendar.MONTH)) {
				this.month = monthInput - 1;
			}
			else {
				this.month = monthInput - 1;
				year = calendar.get(Calendar.YEAR) + 1;
			}
		
		System.out.print("Day:");
		int dayInput = input.nextInt();
		this.day = dayInput;
		System.out.println("=====================");
		while(canSet == false) {
			System.out.println("START TIME");
			System.out.print("Enter an hour:");
			int hourInput = input.nextInt();
			this.hour = hourInput;
			
			System.out.print("Enter minutes:");
			int minuteInput = input.nextInt();
			this.minutes = minuteInput;

			if(day >= this.day && month >= this.month && hourInput >= this.hour && minuteInput >= this.minutes) {
				canSet = true;
			}
			else {
				System.out.println("Entered an invalid date. Please try again.");
			}
		}
		
    	calendar.set(year, this.month, this.day, this.hour, this.minutes, DEFAULT_SECONDS);
    	startTime = calendar.getTime();
	}

	public void endTime() {
		//set class members
		canSet = false;
		//local variables
		int hour = 0;
		int minute = 0;
		
		while(canSet == false) {
			System.out.println("END TIME");
			System.out.print("Enter an hour:");
			int hourInput = input.nextInt();
			hour = hourInput;
	
			System.out.print("Enter minutes:");
			int minuteInput = input.nextInt();
			minute = minuteInput;
			
			if(hour >= this.hour) {
				canSet = true;
			}
			else {
				System.out.println("Entered an invalid time. Time can't be set before the start time. Please try again.");
			}
		}
			if(canSet == true) {
			calendar.set(Calendar.HOUR, hour);
	    	calendar.set(Calendar.MINUTE, minute);
	    	endTime = calendar.getTime();
		}
	}
	
	@Override
	public String toString() {
		return "\nDATE AND TIME\n"+
				"START TIME: " + startTime +
				"\nEND TIME: " + endTime;
	}
}
