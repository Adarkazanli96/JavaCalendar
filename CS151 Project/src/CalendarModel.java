import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class CalendarModel {

	GregorianCalendar cal;
	ArrayList<RecurringEvent> recurringEventsList = new ArrayList<>();
	ArrayList<DayEvent> dayEventsList = new ArrayList<>();
	int currentDay;
	int currentMonth;
	int currentYear;

	public CalendarModel() throws IOException {

		cal = new GregorianCalendar();

		currentMonth = cal.get(Calendar.MONTH);
		currentDay = cal.get(Calendar.DAY_OF_MONTH);
		currentYear = cal.get(Calendar.YEAR);

		File recurringEventFile = new File("input.txt");

		FileReader fr = new FileReader(recurringEventFile);
		BufferedReader br = new BufferedReader(fr);

		boolean endFile = false;

		while (endFile == false) {

			String line = br.readLine();

			if (line == null) {

				endFile = true;

				break;
			}

			String[] pieces = line.split(";");

			String eventName = pieces[0];

			int year = Integer.parseInt(pieces[1]);

			int startingMonth = Integer.parseInt(pieces[2]);

			int endingMonth = Integer.parseInt(pieces[3]);

			String days = pieces[4];

			int startingTime = Integer.parseInt(pieces[5]);

			int endingTime = Integer.parseInt(pieces[6]);

			recurringEventsList.add(
					new RecurringEvent(eventName, year, startingMonth, endingMonth, days, startingTime, endingTime));
		}

		br.close();

		File dayEventFile = new File("events.txt");

		FileReader frr = new FileReader(dayEventFile);
		BufferedReader brr = new BufferedReader(frr);

		boolean endingFile = false;

		while (endingFile == false) {

			String line2 = brr.readLine();

			if (line2 == null) {

				endingFile = true;

				break;
			}

			String[] pieces2 = line2.split(";");

			String eventName2 = pieces2[0];

			int year2 = Integer.parseInt(pieces2[1]);

			int month2 = Integer.parseInt(pieces2[2]);

			int day2 = Integer.parseInt(pieces2[3]);

			int startingTime2 = Integer.parseInt(pieces2[4]);

			int endingTime2 = Integer.parseInt(pieces2[5]);

			dayEventsList.add(new DayEvent(eventName2, year2, month2, day2, startingTime2, endingTime2));
		}

		brr.close();

	}

	public ArrayList<DayEvent> getDayEvents() {

		ArrayList<DayEvent> dayEvents = new ArrayList<>();

		for (DayEvent e : dayEventsList) {

			if (e.getYear() == cal.get(Calendar.YEAR) && e.getMonth() == (cal.get(Calendar.MONTH) + 1)
					&& e.getDay() == cal.get(Calendar.DAY_OF_MONTH)) {
				dayEvents.add(e);
			}
		}
		return dayEvents;
	}

	public ArrayList<DayEvent> getWeekEvents() {
		ArrayList<DayEvent> weekEvents = new ArrayList<>();

		for (DayEvent e : dayEventsList) {

			if (e.getYear() == cal.get(Calendar.YEAR) && e.getMonth() == (cal.get(Calendar.MONTH) + 1)
					&& e.getDay() == cal.get(Calendar.DAY_OF_MONTH)) {
				weekEvents.add(e);
			}
		}
		return weekEvents;
	}

	public ArrayList<DayEvent> getMonthEvents() {

		ArrayList<DayEvent> monthEvents = new ArrayList<>();

		for (DayEvent e : dayEventsList) {

			if (e.getYear() == cal.get(Calendar.YEAR) && e.getMonth() == (cal.get(Calendar.MONTH) + 1)) {

				monthEvents.add(e);
			}

		}
		return monthEvents;
	}

	public ArrayList<RecurringEvent> getAllRecurringEvents() {

		return recurringEventsList;
	}

	/*
	 * public ArrayList<DayEvent> AddRecurringToDay(ArrayList<RecurringEvent>
	 * recurringEventList){
	 * 
	 * 
	 * 
	 * }
	 */

	public GregorianCalendar getCal() {
		return cal;
	}

	public void updateCalendar(GregorianCalendar cal) {

		System.out.println("entered update");
		this.cal = cal;

	}

	public void setDay(int day) {

		System.out.println("entered setDay");

		cal.set(Calendar.DAY_OF_MONTH, day);
	}

	public void resetCalendar() {

		System.out.println("entered reset");

		cal.set(currentYear, currentMonth, currentDay);
	}
}
