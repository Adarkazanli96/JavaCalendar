import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class CalendarModel {
	Calendar cal = new GregorianCalendar();
	ArrayList<Event> events = new ArrayList<Event>();
	
	
	public ArrayList<Event> getDayEvents(){
		return events;
	}
	
	public ArrayList<Event> getWeekEvents(){
		return events;
	}
	
	public ArrayList<Event> getMonthEvents(){
		return events;
	}
	
	public ArrayList<Event> getAllEvents(){
		return events;
	}
	
	public Calendar getCal() {
		return cal;
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
