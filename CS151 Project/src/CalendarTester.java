import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

//comments
public class CalendarTester {
	public static void main(String[] args) throws IOException {
		CalendarModel model = new CalendarModel();
		CalendarView view = new CalendarView(model);
		CalendarController controller = new CalendarController(model, view);
		view.setVisible(true);

	}
}
