import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CalendarController {
	private CalendarView view;
	private CalendarModel model;
	
	public CalendarController(CalendarModel model, CalendarView view) {
		this.model = model;
		this.view = view;
		
		this.view.addTodayButton(new TodayListener());
		this.view.addTodayButton(new TodayListener());
		this.view.addTodayButton(new TodayListener());
		this.view.addTodayButton(new TodayListener());
		this.view.addTodayButton(new TodayListener());
		this.view.addTodayButton(new TodayListener());
	}
	
	
	public class TodayListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			System.out.println("Entered today listener");
		}
		
		
	}
}
