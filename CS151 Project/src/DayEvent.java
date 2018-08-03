import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class DayEvent {
	
	private String eventName;
	private int year;
	private int month;
	private int day;
	private int startingTime;
	private int endingTime;
	private int week;
	
	public DayEvent(String eventName, int year, int month,int day,
					int startingTime,int endingTime) {	
	
	this.eventName = eventName;
	this.year = year;
	this.month = month;
	this.day = day;
	this.startingTime = startingTime;
	this.endingTime = endingTime;
	
	GregorianCalendar differentCal = new GregorianCalendar();
	differentCal.set(year, month - 1, day);
	this.week = differentCal.get(Calendar.WEEK_OF_YEAR);
	
	}
	
	public String getEventName() {
		
		return eventName;
	}	
	
	public int getYear() {
		
		return year;
	}	

	public int getMonth() {
		
		return month;
	}	

	public int getDay() {
		
		return day;
	}
	
	public int getWeek() {
		return week;
	}

	public int getStartingTime() {
		
		return startingTime;
	}	

	public int getEndingTime() {
		
		return endingTime;
	}
	
	public String toString() {
		return eventName + " " + getMonth() + " " + getDay() + ", " + getYear() + ": " + getStartingTime() + " - " + getEndingTime();
	}

}
