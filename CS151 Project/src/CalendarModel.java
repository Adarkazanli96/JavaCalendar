import java.io.*;
import java.time.*;
import java.time.temporal.*;
import java.util.*;

// Contains all the data
public class CalendarModel {

	GregorianCalendar cal;
	ArrayList<RecurringEvent> recurringEventsList = new ArrayList<>();
	ArrayList<DayEvent> dayEventsList = new ArrayList<>();
	int currentDay;
	int currentMonth;
	int currentYear;

	public CalendarModel() throws IOException {

		cal = new GregorianCalendar();

		// Retrieve the current month, day and year
		currentMonth = cal.get(Calendar.MONTH);
		currentDay = cal.get(Calendar.DAY_OF_MONTH);
		currentYear = cal.get(Calendar.YEAR);

		// Read the recurring events text file and store each event in the recurring
		// events ArrayList
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

		// Read the day events text file and store each event in the day events
		// ArrayList
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

		// convert the recurring events to day events
		convertFromRecToDay();

		// sort the events from events.txt by date then time
		sortByDateTime();

	}

	// returns a list of day events based on a given day
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

	// returns a list of week events based on a given week
	public ArrayList<DayEvent> getWeekEvents() {
		ArrayList<DayEvent> weekEvents = new ArrayList<>();
		for (DayEvent e : dayEventsList) {
			if (e.getWeek() == cal.get(Calendar.WEEK_OF_YEAR)) {
				weekEvents.add(e);
			}
		}
		return weekEvents;
	}

	// returns a list of month events based on a given month
	public ArrayList<DayEvent> getMonthEvents() {
		ArrayList<DayEvent> monthEvents = new ArrayList<>();
		for (DayEvent e : dayEventsList) {
			if (e.getYear() == cal.get(Calendar.YEAR) && e.getMonth() == (cal.get(Calendar.MONTH) + 1)) {
				monthEvents.add(e);
			}
		}
		return monthEvents;
	}

	// returns all of the recurring events
	public ArrayList<RecurringEvent> getAllRecurringEvents() {
		return recurringEventsList;
	}

	// returns all of the day events
	public ArrayList<DayEvent> getAllDayEvents() {
		return dayEventsList;
	}

	public void addDayEvent(DayEvent e) {
		dayEventsList.add(e);
	}

	public void addRecurringEvent(RecurringEvent e) {
		recurringEventsList.add(e);
	}

	public void convertFromRecToDay() {

		// store each recurring event as a single day into an ArrayList
		ArrayList<RecurringEvent> tempList = new ArrayList<>();

		for (RecurringEvent e : recurringEventsList) {
			String[] allDays = e.getDays().split("");
			for (int i = 0; i < allDays.length; i++) {
				tempList.add(new RecurringEvent(e.getEventName(), e.getYear(), e.getStartingMonth(), e.getEndingMonth(),
						allDays[i], e.getStartingTime(), e.getEndingTime()));
			}
		}

		// store each recurring event as a day event into an ArrayList
		ArrayList<DayEvent> tempList2 = new ArrayList<>();

		for (RecurringEvent e : tempList) {
			TemporalAdjuster myDay = null;
			Month myStartingMonth = null;
			Month myEndingMonth = null;
			if (e.getDays().equals("S")) {
				myDay = DayOfWeek.SUNDAY;
			}
			if (e.getDays().equals("M")) {
				myDay = DayOfWeek.MONDAY;
			}
			if (e.getDays().equals("T")) {
				myDay = DayOfWeek.TUESDAY;
			}
			if (e.getDays().equals("W")) {
				myDay = DayOfWeek.WEDNESDAY;
			}
			if (e.getDays().equals("H")) {
				myDay = DayOfWeek.THURSDAY;
			}
			if (e.getDays().equals("F")) {
				myDay = DayOfWeek.FRIDAY;
			}
			if (e.getDays().equals("A")) {
				myDay = DayOfWeek.SATURDAY;
			}
			myStartingMonth = Month.of(e.getStartingMonth());
			myEndingMonth = Month.of((e.getEndingMonth()) + 1);
			LocalDate date = LocalDate.of(e.getYear(), myStartingMonth, 15);
			LocalDate date2 = LocalDate.of(e.getYear(), myEndingMonth, 15);
			LocalDate dateOfFirstDay = date.with(TemporalAdjusters.firstInMonth((DayOfWeek) myDay));
			LocalDate dateOfLastDay = date2.with(TemporalAdjusters.firstInMonth((DayOfWeek) myDay));
			GregorianCalendar myCal = new GregorianCalendar();
			myCal.set(dateOfFirstDay.getYear(), (dateOfFirstDay.getMonthValue()) - 1, dateOfFirstDay.getDayOfMonth());

			boolean stop = false;
			while (stop == false) {
				int calYear = myCal.get(Calendar.YEAR);
				int calMonth = (myCal.get(Calendar.MONTH)) + 1;
				int calDay = myCal.get(Calendar.DAY_OF_MONTH);
				int calYear2 = dateOfLastDay.getYear();
				int calMonth2 = dateOfLastDay.getMonthValue();
				int calDay2 = dateOfLastDay.getDayOfMonth();
				if (calYear == calYear2 && calMonth == calMonth2 && calDay == calDay2) {
					stop = true;
					break;
				}
				tempList2.add(new DayEvent(e.getEventName(), calYear, calMonth, calDay, e.getStartingTime(),
						e.getEndingTime()));
				myCal.set(myCal.get(Calendar.YEAR), (myCal.get(Calendar.MONTH)),
						(myCal.get(Calendar.DAY_OF_MONTH)) + 7);
			}
		}

		dayEventsList.addAll(tempList2);
	}

	// receives a list of recurring events and returns it in the form of a day event
	public ArrayList<DayEvent> convert(ArrayList<RecurringEvent> list) {

		// store each recurring event as a single day into an ArrayList
		ArrayList<RecurringEvent> tempList = new ArrayList<>();
		for (RecurringEvent e : list) {
			String[] allDays = e.getDays().split("");
			for (int i = 0; i < allDays.length; i++) {
				tempList.add(new RecurringEvent(e.getEventName(), e.getYear(), e.getStartingMonth(), e.getEndingMonth(),
						allDays[i], e.getStartingTime(), e.getEndingTime()));
			}
		}

		// store each recurring event as a day event into an ArrayList
		ArrayList<DayEvent> tempList2 = new ArrayList<>(); // list of recurring events in DayEvent form.
		for (RecurringEvent e : tempList) {
			TemporalAdjuster myDay2 = null;
			Month myStartingMonth = null;
			Month myEndingMonth = null;
			if (e.getDays().equals("S")) {
				myDay2 = DayOfWeek.SUNDAY;
			}
			if (e.getDays().equals("M")) {
				myDay2 = DayOfWeek.MONDAY;
			}
			if (e.getDays().equals("T")) {
				myDay2 = DayOfWeek.TUESDAY;
			}
			if (e.getDays().equals("W")) {
				myDay2 = DayOfWeek.WEDNESDAY;
			}
			if (e.getDays().equals("H")) {
				myDay2 = DayOfWeek.THURSDAY;
			}
			if (e.getDays().equals("F")) {
				myDay2 = DayOfWeek.FRIDAY;
			}
			if (e.getDays().equals("A")) {
				myDay2 = DayOfWeek.SATURDAY;
			}

			myStartingMonth = Month.of(e.getStartingMonth());
			myEndingMonth = Month.of((e.getEndingMonth()) + 1);

			LocalDate date = LocalDate.of(e.getYear(), myStartingMonth, 15);
			LocalDate date2 = LocalDate.of(e.getYear(), myEndingMonth, 15);
			LocalDate dateOfFirstDay = date.with(TemporalAdjusters.firstInMonth((DayOfWeek) myDay2));
			LocalDate dateOfLastDay = date2.with(TemporalAdjusters.firstInMonth((DayOfWeek) myDay2));

			GregorianCalendar myCal = new GregorianCalendar();

			myCal.set(dateOfFirstDay.getYear(), (dateOfFirstDay.getMonthValue()) - 1, dateOfFirstDay.getDayOfMonth());

			boolean stop = false;
			while (stop == false) {
				int calYear = myCal.get(Calendar.YEAR);
				int calMonth = (myCal.get(Calendar.MONTH)) + 1;
				int calDay = myCal.get(Calendar.DAY_OF_MONTH);

				int calYear2 = dateOfLastDay.getYear();
				int calMonth2 = dateOfLastDay.getMonthValue();
				int calDay2 = dateOfLastDay.getDayOfMonth();

				if (calYear == calYear2 && calMonth == calMonth2 && calDay == calDay2) {
					stop = true;
					break;
				}

				tempList2.add(new DayEvent(e.getEventName(), calYear, calMonth, calDay, e.getStartingTime(),
						e.getEndingTime()));

				myCal.set(myCal.get(Calendar.YEAR), (myCal.get(Calendar.MONTH)),
						(myCal.get(Calendar.DAY_OF_MONTH)) + 7);
			}
		}
		return tempList2;
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

	// sort each day event in the list by date, then time
	public void sortByDateTime() {
		TreeSet<DayEvent> sorter = new TreeSet<>(dayEventsList);
		dayEventsList.removeAll(dayEventsList);
		dayEventsList.addAll(sorter);
	}
}
