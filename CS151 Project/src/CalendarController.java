import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class CalendarController {
	private CalendarView view;
	private CalendarModel model;
	private GregorianCalendar cal;

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
		this.view.addOptionWindowCancelButton(new OptionWindowCancel());
		this.view.addDayEventWindowCancelButton(new DayEventWindowCancel());
	}

	public class TodayListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			model.resetCalendar();

			cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
			view.repaint();
			view.printDayEventsText();
		}
	}

	public class NextListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (view.getViewBy().equals("Day")) {
				// set the day to one day before and repaint
				cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH) + 1);
				model.updateCalendar(cal);
				view.repaint();
				view.printDayEventsText();

			}

			else if (view.getViewBy().equals("Week")) {
				// set to previous week and repaint
				cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH) + 7);
				view.updateCalendar(cal);
				model.updateCalendar(cal);
				view.repaint();
				view.printWeekEventsText();

			}

			else if (view.getViewBy().equals("Month")) {
				// set to previous month and repaint
				cal.set(cal.get(Calendar.YEAR), (cal.get(Calendar.MONTH)) + 1, 1);
				view.updateCalendar(cal);
				model.updateCalendar(cal);
				view.repaint();
				view.printMonthEventsText();
			}
		}
	}

	public class PrevListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (view.getViewBy().equals("Day")) {
				// set the day to one day before and repaint
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

	public class DayListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			view.setViewBy("Day");

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
			view.setViewBy("Week");

			view.dayButton.setForeground(Color.BLACK);
			view.weekButton.setForeground(Color.BLUE);
			view.monthButton.setForeground(Color.BLACK);
			
			//print the events for that week
			view.printWeekEventsText();
		}
	}

	public class MonthListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			view.setViewBy("Month");

			view.dayButton.setForeground(Color.BLACK);
			view.weekButton.setForeground(Color.BLACK);
			view.monthButton.setForeground(Color.BLUE);
			
			// print events for that month
			view.printMonthEventsText();
		}
	}

	public class AgendaListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			System.out.println("Entered agenda listener");

			model.updateCalendar(cal);

			model.getMonthEvents();
		}
	}

	public class CreateListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			view.createEvent();
		}
	}
	
	public class DayEventListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			view.createDayEvent();
		}
		
	}
	
	public class OptionWindowCancel implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			view.deleteOptionWindow();
		}
		
	}
	
	public class DayEventWindowCancel implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			view.deleteDayEventWindow();
		}
		
	}

}
