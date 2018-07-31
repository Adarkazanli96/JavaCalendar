import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class CalendarModel {
	GregorianCalendar cal;
	ArrayList<Event> events = new ArrayList<Event>();
	int currentDay;
	int currentMonth;
	int currentYear;

	public CalendarModel() {
		cal = new GregorianCalendar();
		currentMonth = cal.get(Calendar.MONTH);
		currentDay = cal.get(Calendar.DAY_OF_MONTH);
		currentYear = cal.get(Calendar.YEAR);
	}

	public ArrayList<Event> getDayEvents() {
		return events;
	}

	public ArrayList<Event> getWeekEvents() {
		return events;
	}

	public ArrayList<Event> getMonthEvents() {
		return events;
	}

	public ArrayList<Event> getAllEvents() {
		return events;
	}

	public GregorianCalendar getCal() {
		return cal;
	}

	public void updateCalendar(GregorianCalendar cal) {
		this.cal = cal;

	}

	public void setDay(int day) {
		cal.set(Calendar.DAY_OF_MONTH, day);
	}

	public void resetCalendar() {
		cal.set(currentYear, currentMonth, currentDay);
	}

	public class Event {
		private String name;
		private Calendar date;
		private String startTime;
		private String endTime;

		public Event(String name, Calendar date, String startTime, String endTime) {
			this.name = name;
			this.date = date;
			this.startTime = startTime;
			this.endTime = endTime;
		}

		public String getName() {
			return name;
		}

		public Calendar getDate() {
			return date;
		}

		public String getStartTime() {
			return startTime;
		}

		public String getEndTime() {
			return endTime;
		}

	}

}
