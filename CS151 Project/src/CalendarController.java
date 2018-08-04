import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

// provides action listeners for all of the buttons
public class CalendarController {
	private CalendarView view;
	private CalendarModel model;
	private GregorianCalendar cal;
	private File eventFile;
	private File inputFile;

	public CalendarController(CalendarModel model, CalendarView view) {
		this.model = model;
		this.view = view;
		cal = model.getCal();

		this.view.addTodayButton(new TodayListener());
		this.view.addNextButton(new NextListener());
		this.view.addPrevButton(new PrevListener());
		this.view.addDayButton(new DayListener());
		this.view.addWeekButton(new WeekListener());
		this.view.addMonthButton(new MonthListener());
		this.view.addAgendaButton(new AgendaListener());
		this.view.addCreateButton(new CreateListener());
		this.view.addDayEventButton(new DayEventListener());
		this.view.addRecurringEventButton(new RecurringEventListener());

		this.view.addOptionWindowCancelButton(new OptionWindowCancel());

		this.view.addDayEventWindowSaveButton(new DayEventWindowSave());
		this.view.addDayEventWindowCancelButton(new DayEventWindowCancel());

		this.view.addRecurringEventWindowSaveButton(new RecurringEventWindowSave());
		this.view.addRecurringEventWindowCancelButton(new RecurringEventWindowCancel());
	}

	// set the calendar to present time and day
	public class TodayListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			model.resetCalendar();
			cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
			view.repaint();
			view.printDayEventsText();
		}
	}

	// Switches to the next day, week or month, depending on the view selected
	public class NextListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (view.getViewBy().equals("Day")) {
				// set to next day and repaint
				cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH) + 1);
				model.updateCalendar(cal);
				view.repaint();
				view.printDayEventsText();

			}

			else if (view.getViewBy().equals("Week")) {
				// set to next week and repaint
				cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH) + 7);
				view.updateCalendar(cal);
				model.updateCalendar(cal);
				view.repaint();
				view.printWeekEventsText();

			}

			else if (view.getViewBy().equals("Month")) {
				// set to next month and repaint
				cal.set(cal.get(Calendar.YEAR), (cal.get(Calendar.MONTH)) + 1, 1);
				view.updateCalendar(cal);
				model.updateCalendar(cal);
				view.repaint();
				view.printMonthEventsText();
			}
		}
	}

	// Switches to the previous day, week or month, depending on the view selected
	public class PrevListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (view.getViewBy().equals("Day")) {
				// set to previous day and repaint
				cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH) - 1);
				model.updateCalendar(cal);
				view.repaint();
				view.printDayEventsText();
			}

			else if (view.getViewBy().equals("Week")) {
				// set to previous week and repaint
				cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH) - 7);
				view.updateCalendar(cal);
				model.updateCalendar(cal);
				view.repaint();
				view.printWeekEventsText();

			} else if (view.getViewBy().equals("Month")) {
				// set to previous month and repaint
				cal.set(cal.get(Calendar.YEAR), (cal.get(Calendar.MONTH)) - 1, 1);
				view.updateCalendar(cal);
				model.updateCalendar(cal);
				view.repaint();
				view.printMonthEventsText();
			}

		}

	}

	// when "Day" is selected
	public class DayListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// change the view to day
			view.setViewBy("Day");

			// set the color around selected button to blue
			view.dayButton.setForeground(Color.BLUE);
			view.weekButton.setForeground(Color.BLACK);
			view.monthButton.setForeground(Color.BLACK);

			// print the events for that day
			view.printDayEventsText();
		}
	}

	public class WeekListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// change the view to week
			view.setViewBy("Week");

			// set the color around selected button to blue
			view.dayButton.setForeground(Color.BLACK);
			view.weekButton.setForeground(Color.BLUE);
			view.monthButton.setForeground(Color.BLACK);

			// print the events for that week
			view.printWeekEventsText();
		}
	}

	public class MonthListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// change the view to month
			view.setViewBy("Month");

			// set the color around selected button to blue
			view.dayButton.setForeground(Color.BLACK);
			view.weekButton.setForeground(Color.BLACK);
			view.monthButton.setForeground(Color.BLUE);

			// print events for that month
			view.printMonthEventsText();
		}
	}

	// when "Agenda" is selected
	public class AgendaListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			// print all the events
			view.printAgenda();
		}
	}

	// when "Create" is selected
	public class CreateListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			view.createEvent(); // create event dialog box will pop up
		}
	}

	// when "Day Event" is selected on the create event dialog box
	public class DayEventListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			view.createDayEvent(); // create day event dialog box will pop up
			view.deleteOptionWindow(); // previous dialog box will close
		}
	}

	// when "Recurring Event" is selected on the create event dialog box
	public class RecurringEventListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			view.createRecurringEvent(); // create recurring event dialog box will pop up
			view.deleteOptionWindow(); // previous dialog box will close
		}

	}

	// closes the create event dialog box
	public class OptionWindowCancel implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			view.deleteOptionWindow();
		}
	}

	// closes the create day event dialog box
	public class DayEventWindowCancel implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			view.deleteDayEventWindow();
		}
	}

	// saves information entered through the day event dialog box
	public class DayEventWindowSave implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			DayEvent temp = null;

			// check to see if there is a conflict with any other day event
			for (DayEvent event : model.getAllDayEvents()) {
				if ((event.getYear() == cal.get(Calendar.YEAR) && event.getMonth() == (cal.get(Calendar.MONTH)) + 1
						&& event.getDay() == cal.get(Calendar.DAY_OF_MONTH)
						&& Integer.parseInt(view.getStartTimeTextField().getText()) >= event.getStartingTime()
						&& Integer.parseInt(view.getStartTimeTextField().getText()) < event.getEndingTime())
						|| (event.getYear() == cal.get(Calendar.YEAR)
								&& event.getMonth() == (cal.get(Calendar.MONTH)) + 1
								&& event.getDay() == cal.get(Calendar.DAY_OF_MONTH)
								&& Integer.parseInt(view.getEndTimeTextField().getText()) < event.getEndingTime()
								&& Integer.parseInt(view.getEndTimeTextField().getText()) > event.getStartingTime())) {
					view.displayErrorMessage("Time Conflict! Try Again");
					// dispose window
					view.deleteDayEventWindow();
					return;
				}

				// if there is no conflict, create a new Day Event with the given information
				else {
					temp = new DayEvent(view.getEventTextField().getText(), cal.get(Calendar.YEAR),
							(cal.get(Calendar.MONTH)) + 1, cal.get(Calendar.DAY_OF_MONTH),
							Integer.parseInt(view.getStartTimeTextField().getText()),
							Integer.parseInt(view.getEndTimeTextField().getText()));
				}
			}

			model.addDayEvent(temp);

			// sort the Day Events after adding the new one
			model.sortByDateTime();

			eventFile = new File("events.txt");
			try {
				eventFile.createNewFile();
			} catch (IOException e1) {
				e1.printStackTrace();
			}

			FileWriter fw = null;
			try {

				fw = new FileWriter(eventFile);
			} catch (IOException e1) {
				e1.printStackTrace();
			}

			// print the event to event.txt
			PrintWriter pw = new PrintWriter(fw);
			for (DayEvent event : model.getAllDayEvents()) {
				pw.write(event.getEventName() + ";" + event.getYear() + ";" + event.getMonth() + ";" + event.getDay()
						+ ";" + event.getStartingTime() + ";" + event.getEndingTime() + "\n");
			}
			pw.close();

			// repaint the view to show day, week, or month events
			if (view.getViewBy().equals("Day")) {
				view.repaint();
				view.printDayEventsText();
			}

			else if (view.getViewBy().equals("Week")) {
				view.repaint();
				view.printWeekEventsText();

			} else if (view.getViewBy().equals("Month")) {
				view.repaint();
				view.printMonthEventsText();
			}

			// dispose window
			view.deleteDayEventWindow();
		}

	}

	// saves information entered through the recurring event dialog box
	public class RecurringEventWindowSave implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			String date[] = view.getRecurringEventStartDateField().getText().split("/");
			int year = Integer.parseInt(date[2]);
			int startingMonth = Integer.parseInt(date[0]);

			String date2[] = view.getRecurringEventEndDateField().getText().split("/");
			int endingMonth = Integer.parseInt(date2[0]);

			RecurringEvent temp = new RecurringEvent(view.getRecurringEventEventField().getText(), year, startingMonth,
					endingMonth, view.getRecurringEventDayField().getText(),
					Integer.parseInt(view.getRecurringEventStartTimeField().getText()),
					Integer.parseInt(view.getRecurringEventEndTimeField().getText()));

			ArrayList<RecurringEvent> recurringList = new ArrayList<RecurringEvent>();
			recurringList.add(temp);

			// create a day events list and set it equal to a converted recurring events
			// list
			ArrayList<DayEvent> dayList = model.convert(recurringList);

			// check if there is a conflict
			boolean conflict = false;
			outerloop: for (DayEvent d1 : model.getAllDayEvents()) {
				for (DayEvent d2 : dayList) {
					if (d1.getYear() == d2.getYear() && d1.getMonth() == d2.getMonth() && d1.getDay() == d2.getDay()
							&& d2.getStartingTime() >= d1.getStartingTime() && d2.getStartingTime() < d1.getEndingTime()
							|| d1.getYear() == d2.getYear() && d1.getMonth() == d2.getMonth()
									&& d1.getDay() == d2.getDay() && d2.getEndingTime() >= d1.getEndingTime()
									&& d2.getEndingTime() < d1.getStartingTime()) {
						view.displayErrorMessage("Time Conflict! Try Again");
						conflict = true;
						break outerloop;
					}
				}
			}

			// if there was no conflict, add all of the events to the global day events list
			if (conflict == false) {
				model.dayEventsList.addAll(dayList);
				model.recurringEventsList.addAll(recurringList);
				model.sortByDateTime();
			}

			// sort the events by date then time
			model.sortByDateTime();

			// print to the input.txt file
			inputFile = new File("input.txt");
			try {
				inputFile.createNewFile();
			} catch (IOException e1) {
				e1.printStackTrace();
			}

			FileWriter fww = null;
			try {
				fww = new FileWriter(inputFile);
			} catch (IOException e1) {
				e1.printStackTrace();
			}

			PrintWriter pww = new PrintWriter(fww);
			for (RecurringEvent event : model.getAllRecurringEvents()) {
				pww.write(event.getEventName() + ";" + event.getYear() + ";" + event.getStartingMonth() + ";"
						+ event.getEndingMonth() + ";" + event.getDays() + ";" + event.getStartingTime() + ";"
						+ event.getEndingTime() + "\n");
			}
			pww.close();

			// print to the event.txt file
			eventFile = new File("events.txt");
			try {
				eventFile.createNewFile();
			} catch (IOException e1) {

				e1.printStackTrace();
			}
			FileWriter fw = null;
			try {
				fw = new FileWriter(eventFile);
			} catch (IOException e1) {
				e1.printStackTrace();
			}

			PrintWriter pw = new PrintWriter(fw);
			for (DayEvent event : model.getAllDayEvents()) {
				pw.write(event.getEventName() + ";" + event.getYear() + ";" + event.getMonth() + ";" + event.getDay()
						+ ";" + event.getStartingTime() + ";" + event.getEndingTime() + "\n");
			}

			pw.close();

			// repaint the view to show day, week, or month events
			if (view.getViewBy().equals("Day")) {
				view.repaint();
				view.printDayEventsText();
			}

			else if (view.getViewBy().equals("Week")) {
				view.repaint();
				view.printWeekEventsText();

			} else if (view.getViewBy().equals("Month")) {
				view.repaint();
				view.printMonthEventsText();
			}

			// dispose window
			view.deleteRecurringEventWindow();
		}

	}

	// closes the recurring event dialog box
	public class RecurringEventWindowCancel implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			view.deleteRecurringEventWindow();
		}
	}
}
