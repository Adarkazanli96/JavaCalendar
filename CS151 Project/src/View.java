import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class View extends JFrame{
	
	public View() {
	JFrame frame = new JFrame();
	JPanel calendarWindow = new JPanel();
	JTextField field = new JTextField();
	
	frame.setSize(400,400);
	calendarWindow.setSize(300,300);
	field.setSize(200,100);
	
	frame.add(calendarWindow);
	frame.add(field);
	
	calendarWindow.setVisible(true);
	field.setVisible(true);
	frame.setVisible(true);
	frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
	
	JButton button1 = new JButton();
	}
}
