import java.io.IOException;

import javax.*;

// Main method
public class CalendarTester {
	public static void main(String[] args) throws IOException {
		CalendarModel model = new CalendarModel();
		CalendarView view = new CalendarView(model);
		CalendarController controller = new CalendarController(model, view);
	}
}
