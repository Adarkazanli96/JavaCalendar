import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class CalendarView extends JFrame {

	public CalendarView() {

		JPanel buttonsPanel = new JPanel();
		JTextField field = new JTextField("                                             ");
		JPanel calendar = new JPanel();
		JPanel panel = new JPanel();

		JButton todayButton = new JButton("Today");
		JButton prevButton = new JButton("<");
		JButton nextButton = new JButton(">");
		JButton dayButton = new JButton("Day");
		JButton weekButton = new JButton("Week");
		JButton monthButton = new JButton("Month");
		JButton agendaButton = new JButton("Agenda");
		JButton createButton = new JButton("Create");

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
		
		panel.add(calendar);
		panel.add(field);
		
		this.add(buttonsPanel, BorderLayout.NORTH);
		this.add(panel, BorderLayout.CENTER);

		panel.setSize(600,400);
		field.setSize(300, 300);
		calendar.setSize(300,300);
		this.setSize(800,800);
		
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

	}
}
