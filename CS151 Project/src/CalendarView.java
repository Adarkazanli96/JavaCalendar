
// The frame has a border layout
// the frame is divided into a buttonsPanel (NORTH) and a panel (CENTER)
// Buttons are contained within the buttonSanel
// the center panel has 2 panels, one is for the day calendar, and one is for the event text area

import java.awt.*;
import java.awt.event.*;
import java.text.*;
import java.util.*;
import javax.swing.*;
import javax.swing.border.*;

public class CalendarView extends JFrame {
	Calendar cal;
	CalendarModel model;
	JLabel monthLabel;
	JPanel monthPanel;
	String viewBy = "Day";

	// Buttons displayed in the initial screen
	JButton todayButton = new JButton("Today");
	JButton prevButton = new JButton("<");
	JButton nextButton = new JButton(">");
	JButton dayButton = new JButton("Day");
	JButton weekButton = new JButton("Week");
	JButton monthButton = new JButton("Month");
	JButton agendaButton = new JButton("Agenda");
	JButton createButton = new JButton("Create");

	// window that pops up when create is pressed
	JFrame optionWindow = new JFrame();

	// buttons for optionWindow
	JButton optionWindowCancel = new JButton("Cancel");
	JButton dayEventButton = new JButton("Day Event");
	JButton recurringEventButton = new JButton("Recurring Event");

	// window that pops up when day event is pressed
	JFrame dayEventWindow = new JFrame();

	// panels for dayEventWindow
	JPanel dayEventTop = new JPanel();
	JPanel dayEventBottom = new JPanel();

	// buttons for dayEventWindow
	JButton dayEventSave = new JButton("Save");
	JButton dayEventCancel = new JButton("Cancel");

	// text fields for dayEventWindow
	JTextField dayEventEventField = new JTextField(40);
	JTextField dayEventStartTimeField = new JTextField(10);
	JTextField dayEventEndTimeField = new JTextField(10);
	JTextArea dayEventDateField = new JTextArea();

	// window that pops up when recurring event is pressed
	JFrame recurringEventWindow = new JFrame();

	// panels for recurring event window
	JPanel recurringEventTop = new JPanel();
	JPanel recurringEventBottom = new JPanel();
	JPanel recurringEventBottomLeft = new JPanel();
	JPanel recurringEventBottomRight = new JPanel();

	// buttons for recurring event window
	JButton recurringEventSave = new JButton("Save");
	JButton recurringEventCancel = new JButton("Cancel");

	// text fields for recurring event window
	JTextField recurringEventEventField = new JTextField(40);
	JTextField recurringEventStartTimeField = new JTextField(10);
	JTextField recurringEventEndTimeField = new JTextField(10);
	JTextField recurringEventDayField = new JTextField(2);
	JTextArea recurringEventStartDateField = new JTextArea();
	JTextArea recurringEventEndDateField = new JTextArea(1, 6);

	JTextArea eventTextArea;

	public CalendarView(CalendarModel model) {

		cal = model.getCal();

		JPanel buttonsPanel = new JPanel();
		eventTextArea = new JTextArea(20, 20);
		JPanel calendar = new JPanel();
		JPanel panel = new JPanel();

		monthLabel = new JLabel();
		monthPanel = new JPanel();
		this.model = model;

		buttonsPanel.add(todayButton);
		buttonsPanel.add(prevButton);
		buttonsPanel.add(nextButton);
		buttonsPanel.add(dayButton);
		buttonsPanel.add(weekButton);
		buttonsPanel.add(monthButton);
		buttonsPanel.add(agendaButton);
		calendar.add(createButton);
		dayButton.setForeground(Color.BLUE);

		this.setLayout(new BorderLayout());
		panel.setLayout(new FlowLayout());
		calendar.setLayout(new BorderLayout());
		monthPanel.setLayout(new GridLayout(0, 7, 5, 5));
		monthPanel.setBorder(new EmptyBorder(0, 10, 0, 0));

		// add the calendar panel and the scroll panel to the panel
		panel.add(calendar);
		JScrollPane sp = new JScrollPane(eventTextArea);
		panel.add(sp);
		sp.setVerticalScrollBarPolicy(ScrollPaneLayout.VERTICAL_SCROLLBAR_ALWAYS);

		this.add(buttonsPanel, BorderLayout.NORTH);
		this.add(panel, BorderLayout.CENTER);

		calendar.add(createButton, BorderLayout.NORTH);
		calendar.add(monthLabel, BorderLayout.CENTER);
		calendar.add(monthPanel, BorderLayout.SOUTH);

		panel.setSize(600, 400);
		calendar.setSize(300, 300);
		this.setSize(800, 800);

		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

		drawMonth();
		printDayEventsText();
		this.setResizable(false);

	}

	public void drawMonth() {
		monthLabel.setText(new SimpleDateFormat("MMM yyyy").format(cal.getTime()));

		String[] daysOfWeek = { "Sun  ", "Mon  ", "Tue  ", "Wed  ", "Thu  ", "Fri  ", "Sat  " };
		for (int i = 0; i < 7; i++) {
			JLabel day = new JLabel(daysOfWeek[i]);
			monthPanel.add(day);
		}
		int daysInMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		Calendar getStart = new GregorianCalendar(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), 1);
		int startDay = getStart.get(Calendar.DAY_OF_WEEK);

		for (int i = 1; i < daysInMonth + startDay; i++) {
			if (i < startDay) {
				final JLabel day = new JLabel("");
				monthPanel.add(day);
			} else {
				int d = i - startDay + 1;
				final JLabel day = new JLabel(Integer.toString(d));
				day.addMouseListener(new MouseListener() {
					@Override
					public void mouseClicked(MouseEvent e) {
						int num = Integer.parseInt(day.getText());
						model.setDay(num);
						repaint();
					}

					@Override
					public void mousePressed(MouseEvent e) {
					}

					@Override
					public void mouseReleased(MouseEvent e) {
					}

					@Override
					public void mouseEntered(MouseEvent e) {
					}

					@Override
					public void mouseExited(MouseEvent e) {
					}
				});
				if (d == cal.get(Calendar.DAY_OF_MONTH)) {
					day.setBorder(BorderFactory.createLineBorder(Color.black));
				}
				monthPanel.add(day);
			}
		}
	}

	public void printDayEventsText() {
		eventTextArea.removeAll();

		String s = "";

		for (DayEvent dayEvent : model.getDayEvents()) {
			s = s + dayEvent + "\n";
		}

		eventTextArea.setText(s);
	}

	public void printWeekEventsText() {
		eventTextArea.removeAll();

		String s = "";

		for (DayEvent weekEvent : model.getWeekEvents()) {
			s = s + weekEvent + "\n";
		}

		eventTextArea.setText(s);
	}

	public void printMonthEventsText() {
		eventTextArea.removeAll();

		String s = "";

		for (DayEvent monthEvent : model.getMonthEvents()) {
			s = s + monthEvent + "\n";
		}

		eventTextArea.setText(s);
	}

	public void printAgenda() {
		eventTextArea.removeAll();

		String s = "";

		for (DayEvent event : model.getAllDayEvents()) {
			s = s + event + "\n";
		}

		eventTextArea.setText(s);
	}

	public void updateCalendar(GregorianCalendar cal) {
		this.cal = cal;
	}

	public void repaint() {
		monthPanel.removeAll();
		monthPanel.revalidate();
		monthPanel.repaint();

		monthLabel.removeAll();
		monthLabel.revalidate();
		monthLabel.repaint();

		drawMonth();
	}

	public void createEvent() {
		setResizable(false);

		optionWindow.setLayout(new BorderLayout());

		optionWindow.add(dayEventButton, BorderLayout.NORTH);

		optionWindow.add(recurringEventButton, BorderLayout.CENTER);

		optionWindow.add(optionWindowCancel, BorderLayout.SOUTH);

		optionWindow.pack();

		optionWindow.setVisible(true);
		optionWindow.setResizable(false);

	}

	public void createDayEvent() {

		dayEventEventField.setText("Enter event here");

		dayEventDateField.setText(
				(cal.get(Calendar.MONTH)) + 1 + "/" + cal.get(Calendar.DAY_OF_MONTH) + "/" + cal.get(Calendar.YEAR));


		dayEventStartTimeField.setText("HH");

		JLabel to = new JLabel("  to  ");

		dayEventEndTimeField.setText("HH");

		dayEventTop.add(dayEventEventField);

		dayEventBottom.setLayout(new FlowLayout());
		
		dayEventBottom.add(new JLabel("Date: "));
		dayEventBottom.add(dayEventDateField);
		dayEventBottom.add(new JLabel("Time: "));
		dayEventBottom.add(dayEventStartTimeField);
		dayEventBottom.add(to);
		dayEventBottom.add(dayEventEndTimeField);
		dayEventBottom.add(dayEventSave);
		dayEventBottom.add(dayEventCancel);

		dayEventWindow.setLayout(new BorderLayout());
		dayEventWindow.add(dayEventTop, BorderLayout.NORTH);
		dayEventWindow.add(dayEventBottom, BorderLayout.SOUTH);
		dayEventWindow.pack();

		dayEventWindow.setVisible(true);
		dayEventWindow.setResizable(false);
	}

	public void createRecurringEvent() {
		recurringEventEventField.setText("Enter event here");

		JPanel datePanel = new JPanel();
		JPanel timePanel = new JPanel();
		JPanel dayPanel = new JPanel();

		recurringEventStartDateField.setText(
				(cal.get(Calendar.MONTH)) + 1 + "/" + cal.get(Calendar.DAY_OF_MONTH) + "/" + cal.get(Calendar.YEAR));

		recurringEventEndDateField.setText("");

		recurringEventStartTimeField.setText("14:00");

		recurringEventEndTimeField.setText("16:00");

		recurringEventTop.add(recurringEventEventField);

		recurringEventBottom.setLayout(new BorderLayout());
		recurringEventBottomLeft.setLayout(new BorderLayout());
		recurringEventBottomRight.setLayout(new BorderLayout());

		datePanel.add(new JLabel("Date: "));
		datePanel.setLayout(new FlowLayout());
		datePanel.add(recurringEventStartDateField);
		datePanel.add(new JLabel("  to  "));
		datePanel.add(recurringEventEndDateField);
		datePanel.add(new JLabel("Days: "));
		datePanel.add(recurringEventDayField);

		timePanel.add(new JLabel("Time: "));
		timePanel.setLayout(new FlowLayout());
		timePanel.add(recurringEventStartTimeField);
		timePanel.add(new JLabel(" to "));
		timePanel.add(recurringEventEndTimeField);

		recurringEventBottomLeft.add(datePanel, BorderLayout.NORTH);
		recurringEventBottomLeft.add(timePanel, BorderLayout.SOUTH);

		recurringEventBottomRight.add(recurringEventSave, BorderLayout.NORTH);
		recurringEventBottomRight.add(recurringEventCancel, BorderLayout.SOUTH);

		recurringEventBottom.add(recurringEventBottomRight, BorderLayout.EAST);
		recurringEventBottom.add(recurringEventBottomLeft, BorderLayout.WEST);

		recurringEventWindow.setLayout(new BorderLayout());
		recurringEventWindow.add(recurringEventTop, BorderLayout.NORTH);
		recurringEventWindow.add(recurringEventBottom, BorderLayout.SOUTH);

		recurringEventWindow.pack();

		recurringEventWindow.setVisible(true);
		recurringEventWindow.setResizable(false);

	}

	public void displayErrorMessage(String errorMessage) {
		JOptionPane.showMessageDialog(this, errorMessage);
	}

	public void deleteOptionWindow() {

		optionWindow.dispose();
	}

	public void deleteDayEventWindow() {
		dayEventTop.removeAll();
		dayEventTop.revalidate();
		dayEventTop.repaint();

		dayEventBottom.removeAll();
		dayEventBottom.revalidate();
		dayEventBottom.repaint();

		dayEventWindow.dispose();
	}

	public void deleteRecurringEventWindow() {
		recurringEventBottomLeft.removeAll();
		recurringEventBottomLeft.revalidate();
		recurringEventBottomLeft.repaint();

		recurringEventBottomRight.removeAll();
		recurringEventBottomRight.revalidate();
		recurringEventBottomRight.repaint();

		recurringEventTop.removeAll();
		recurringEventTop.revalidate();
		recurringEventTop.repaint();

		recurringEventBottom.removeAll();
		recurringEventBottom.revalidate();
		recurringEventBottom.repaint();

		recurringEventWindow.dispose();
	}

	public void setViewBy(String viewBy) {
		this.viewBy = viewBy;
	}

	public String getViewBy() {
		return viewBy;
	}

	public JTextField getEventTextField() {
		return dayEventEventField;
	}

	public JTextField getStartTimeTextField() {
		return dayEventStartTimeField;
	}

	public JTextField getEndTimeTextField() {
		return dayEventEndTimeField;
	}

	public JTextArea getDateTextField() {
		return dayEventDateField;
	}

	public void addTodayButton(ActionListener listener) {

		todayButton.addActionListener(listener);
	}

	public void addPrevButton(ActionListener listener) {

		prevButton.addActionListener(listener);
	}

	public void addNextButton(ActionListener listener) {

		nextButton.addActionListener(listener);
	}

	public void addDayButton(ActionListener listener) {

		dayButton.addActionListener(listener);
	}

	public void addWeekButton(ActionListener listener) {

		weekButton.addActionListener(listener);
	}

	public void addMonthButton(ActionListener listener) {

		monthButton.addActionListener(listener);
	}

	public void addAgendaButton(ActionListener listener) {

		agendaButton.addActionListener(listener);
	}

	public void addCreateButton(ActionListener listener) {

		createButton.addActionListener(listener);
	}

	public void addDayEventButton(ActionListener listener) {
		dayEventButton.addActionListener(listener);
	}

	public void addRecurringEventButton(ActionListener listener) {
		recurringEventButton.addActionListener(listener);
	}

	public void addOptionWindowCancelButton(ActionListener listener) {
		optionWindowCancel.addActionListener(listener);
	}

	public void addDayEventWindowSaveButton(ActionListener listener) {
		dayEventSave.addActionListener(listener);
	}

	public void addDayEventWindowCancelButton(ActionListener listener) {
		dayEventCancel.addActionListener(listener);
	}

	public void addRecurringEventWindowSaveButton(ActionListener listener) {
		recurringEventSave.addActionListener(listener);
	}

	public void addRecurringEventWindowCancelButton(ActionListener listener) {
		recurringEventCancel.addActionListener(listener);
	}

}
