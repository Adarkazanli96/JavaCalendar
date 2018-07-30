
// The frame has a border layout
// the frame is divided into a buttonsPanel (NORTH) and a panel (CENTER)
// Buttons are contained within the buttonSanel
// the center panel has 2 panels, one is for the day calendar, and one is for the event text area

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class CalendarView extends JFrame {
	Calendar cal = new GregorianCalendar();
	CalendarModel model;
	JLabel monthLabel;
	JPanel monthPanel;

	JButton todayButton = new JButton("Today");
	JButton prevButton = new JButton("<");
	JButton nextButton = new JButton(">");
	JButton dayButton = new JButton("Day");
	JButton weekButton = new JButton("Week");
	JButton monthButton = new JButton("Month");
	JButton agendaButton = new JButton("Agenda");
	JButton createButton = new JButton("Create");

	public CalendarView(CalendarModel model) {

		JPanel buttonsPanel = new JPanel();
		JTextArea eventTextArea = new JTextArea(20, 20);
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

		this.setLayout(new BorderLayout());
		panel.setLayout(new FlowLayout());
		calendar.setLayout(new BorderLayout());
		monthPanel.setLayout(new GridLayout(6, 15));

		// add the calendar panel and the event text area to the panel
		panel.add(calendar);
		panel.add(eventTextArea);

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

	}

	public void drawMonth() {
		monthLabel.setText(new SimpleDateFormat("MMM yyyy").format(cal.getTime()));

		String[] daysOfWeek = { "Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat" };
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
						// model.setDay(num);
						System.out.print(d);
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

	public void addListenersToButtons() {
		todayButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

			}

		});

		prevButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

			}

		});

		nextButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

			}

		});

		dayButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

			}

		});

		weekButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

			}

		});

		monthButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

			}

		});

		agendaButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

			}

		});


	}
}
