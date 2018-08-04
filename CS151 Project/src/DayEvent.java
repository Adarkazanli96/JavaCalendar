import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class DayEvent implements Comparable<DayEvent> {

	private String eventName;
	private int year;
	private int month;
	private int day;
	private int startingTime;
	private int endingTime;
	private int week;

	public DayEvent(String eventName, int year, int month, int day, int startingTime, int endingTime) {

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
		return getMonth() + "/" + getDay() + "/" + getYear() + ": " + eventName + " from " + getStartingTime() + " to "
				+ getEndingTime();
	}

	@Override
	public int compareTo(DayEvent that) {
		int yearCmp = this.year - that.year;
		int monthCmp = this.month - that.month;
		int dayCmp = this.day - that.day;

		int hourCmp = this.startingTime - that.startingTime;

		if (yearCmp != 0) {

			return yearCmp;
		}

		else if (monthCmp != 0) {

			return monthCmp;
		}

		else if (dayCmp != 0) {

			return dayCmp;
		}

		return hourCmp;
	}

	public boolean equals(Object x) {

		DayEvent that = (DayEvent) x;

		return this.compareTo(that) == 0;
	}

}
