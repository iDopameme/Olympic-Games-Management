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
			finalDate = input.nextBoolean();
		} while (!finalDate);

		month -= 1;
		hour -= 5;
    	calendar.set(year, month, day, hour, minutes, seconds);
		long userTime = calendar.getTimeInMillis();
		timestamp = new Timestamp(userTime);

    	return timestamp;
	}

	public boolean updateTime(Connect conn, int match_id, Timestamp updatedTime ) {
    	boolean validation;
		try {
			String query = "UPDATE olympics.Match SET date = ? WHERE id = ?";
			PreparedStatement pstmt = conn.getConn().prepareStatement(query);

			pstmt.setTimestamp(1, updatedTime);
			pstmt.setInt(2, match_id);
			validation = true;
			pstmt.executeUpdate();

		} catch (Exception ex) {
			System.out.println("ERROR: " + ex.getMessage());
			validation = false;
		}
		return validation;
	}


}
