import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TreeMap;

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
		
		convertFromRecToDay();

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

			if (e.getWeek() == cal.get(Calendar.WEEK_OF_YEAR)) {
				weekEvents.add(e);
			}
		}

		for (DayEvent d : weekEvents) {
			System.out.println(d);
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

	public void convertFromRecToDay() {

		System.out.println("entered method");

		ArrayList<RecurringEvent> tempList = new ArrayList<>(); // list of RecurringEvents in single day form

		for (RecurringEvent e : recurringEventsList) {

			String[] allDays = e.getDays().split("");

			for (int i = 0; i < allDays.length; i++) {

				tempList.add(new RecurringEvent(e.getEventName(), e.getYear(), e.getStartingMonth(), e.getEndingMonth(),
						allDays[i], e.getStartingTime(), e.getEndingTime()));
			}
		}
		/*
		 * for(RecurringEvent e:tempList) {
		 * 
		 * System.out.println(e); }
		 * 
		 * System.out.println(cal.get(Calendar.DAY_OF_WEEK));
		 */

		ArrayList<DayEvent> tempList2 = new ArrayList<>(); // list of recurring events in DayEvent form.

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

			System.out.println("first " + dateOfFirstDay);

			System.out.println("last " + dateOfLastDay);

			GregorianCalendar myCal = new GregorianCalendar();

			myCal.set(dateOfFirstDay.getYear(), (dateOfFirstDay.getMonthValue()) - 1, dateOfFirstDay.getDayOfMonth());

			// System.out.println(myCal.get(Calendar.YEAR)+"
			// "+((myCal.get(Calendar.MONTH))-1)+" "+myCal.get(Calendar.DAY_OF_MONTH));

			// System.out.println(dateOfLastDay.getYear()+"
			// "+dateOfLastDay.getMonthValue()+" "+dateOfLastDay.getDayOfMonth());

			boolean stop = false;

			while (stop == false) {

				// System.out.println(myCal.get(Calendar.YEAR)+" "+myCal.get(Calendar.MONTH)+"
				// "+myCal.get(Calendar.DAY_OF_MONTH));

				int calYear = myCal.get(Calendar.YEAR);

				int calMonth = (myCal.get(Calendar.MONTH)) + 1;

				int calDay = myCal.get(Calendar.DAY_OF_MONTH);

				int calYear2 = dateOfLastDay.getYear();

				int calMonth2 = dateOfLastDay.getMonthValue();

				int calDay2 = dateOfLastDay.getDayOfMonth();

				if (calYear == calYear2 && calMonth == calMonth2 && calDay == calDay2) {

					// System.out.println("entered break");

					// System.out.println("Calendar "+calYear+" "+" "+calMonth+" "+calDay);

					// System.out.println("Compare "+dateOfLastDay.getYear()+"
					// "+dateOfLastDay.getMonthValue()+" "+dateOfLastDay.getDayOfMonth());

					stop = true;

					break;
				}

				// System.out.println("Calendar "+calYear+" "+calMonth+" "+calDay);

				// System.out.println("Compare "+calYear2+" "+calMonth2+" "+calDay2);

				tempList2.add(new DayEvent(e.getEventName(), calYear, calMonth, calDay, e.getStartingTime(),
						e.getEndingTime()));

				myCal.set(myCal.get(Calendar.YEAR), (myCal.get(Calendar.MONTH)),
						(myCal.get(Calendar.DAY_OF_MONTH)) + 7);

			}
		}

		dayEventsList.addAll(tempList2);
	}

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
