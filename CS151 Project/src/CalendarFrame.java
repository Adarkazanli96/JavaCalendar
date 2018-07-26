import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class CalendarFrame extends JFrame implements ChangeListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	CalendarModel model;
	GregorianCalendar c;
	
	private JPanel monthDisplay;
	private JScrollPane scrollEventDisplay;
	
	MONTHS[] Months = MONTHS.values();
    DAYS[] Days = DAYS.values();
	

	static final int FRAME_WIDTH = 1000;
	static final int FRAME_HEIGHT = 600;
	
	
	/**
	 * sets up frame
	 * @param models to connect with view
	 * components added
	 */
	public CalendarFrame(CalendarModel d)
	{
		model = d;
	
		this.setSize(FRAME_WIDTH,FRAME_HEIGHT);
		this.setLayout(new BorderLayout());
		

		scrollEventDisplay = makeEventDisplay();
		monthDisplay = makeCalendarDisplay();
		JPanel controlPanel = new JPanel(new FlowLayout());
		
		
		
		JButton loadButton = new JButton("Load");
		JButton quitButton = new JButton("Quit");
		JButton createButton = new JButton("CREATE");
		JButton previousDayButton = new JButton("<");
		JButton nextDayButton = new JButton(">");
		
		
	
		quitButton.setBackground(Color.WHITE);
		loadButton.setBackground(Color.WHITE);
		
		
		loadButton.addActionListener(new
				ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent arg0)
					{
						model.load();
					}
				});
		quitButton.addActionListener(new
				ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent arg0)
					{
						model.save();
						close();
					}
				});
		createButton.addActionListener(new
				ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent arg0)
					{
						createEvent();
					}
				});
	
		previousDayButton.addActionListener(new
				ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent arg0)
					{
						model.prevDay();
					}
				});
		nextDayButton.addActionListener(new
				ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent arg0)
					{
						model.nextDay();
					}
				});

		
		
		controlPanel.add(createButton);
		controlPanel.add(previousDayButton);
		controlPanel.add(loadButton);
		controlPanel.add(nextDayButton);
		controlPanel.add(quitButton);
		
		
		this.add(controlPanel,BorderLayout.NORTH);
		this.add(monthDisplay,BorderLayout.WEST);
		this.add(scrollEventDisplay, BorderLayout.CENTER);
		
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	
	protected void close()
	{
		this.dispose();
	}

	private void createEvent()
	{
		String date = "";
		if(model.getCalendar().get(Calendar.MONTH) < 9)
			date += "0" +(model.getCalendar().get(Calendar.MONTH)+1) +"/";
		else
			date += (model.getCalendar().get(Calendar.MONTH)+1) +"/";
		
		if(model.getCalendar().get(Calendar.DAY_OF_MONTH) < 10)
			date += "0" + model.getCalendar().get(Calendar.DAY_OF_MONTH) + "/";
		else
			date += model.getCalendar().get(Calendar.DAY_OF_MONTH) + "/";
		
		date += model.getCalendar().get(Calendar.YEAR)+""; 
		

		
		JPanel createEventPanel = new JPanel(new BorderLayout());
		JTextField eventTitle = new JTextField("Untitled Event");
		JPanel startEndSavePanel = new JPanel(new FlowLayout());
		JLabel dateLabel = new JLabel(date);
		JTextField startTime = new JTextField("Start 00:00");
		JLabel toLabel = new JLabel("till");
		JTextField endTime = new JTextField("End 00:00");
		JButton saveButton = new JButton("Save");
		saveButton.setBackground(Color.WHITE);
		saveButton.addActionListener(new
			ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent arg0) 
				{
					//add(new Event("Hello", "Whats", "07/09/2016", "08/07/2016"));
					repaint();
				}
			});
		
		startEndSavePanel.add(dateLabel);
		startEndSavePanel.add(startTime);
		startEndSavePanel.add(toLabel);
		startEndSavePanel.add(endTime);
		startEndSavePanel.add(saveButton);
		
		createEventPanel.add(eventTitle, BorderLayout.NORTH);
		createEventPanel.add(startEndSavePanel, BorderLayout.SOUTH);
		
		this.add(createEventPanel, BorderLayout.SOUTH);
		this.revalidate();
		this.repaint();
	}
	
	private JScrollPane makeEventDisplay()
	{
		JPanel eventPanel = new JPanel(new BorderLayout());
		
		
		JPanel events = new JPanel(new GridLayout(0,1));
		
		JTextField eventDisplayDate = new 
				JTextField(Days[model.getCalendar().get(Calendar.DAY_OF_WEEK)-1] + " " 
				+ Months[(model.getCalendar().get(Calendar.MONTH))] + " "
				+ model.getCalendar().get(Calendar.DAY_OF_MONTH) + ", "
				+ model.getCalendar().get(Calendar.YEAR));
		
		for(int i = 1; i < 25; i++)
		{
			String date;
			if(i < 10)
			{
				String time = ("0" + i + ":00");
				date = time;
				date += " " + model.getEventDetail(time);
				
				events.add(new JLabel(date));
			}
			else
			{
				String time = i + ":00";
				date = time;
				date += " " + model.getEventDetail(time);
				
				events.add(new JLabel(date));
			}
		}
		eventPanel.add(eventDisplayDate, BorderLayout.NORTH);
		eventPanel.add(events, BorderLayout.CENTER);
		JScrollPane scrollEventDisplay = new JScrollPane(eventPanel,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		
		
		return scrollEventDisplay;
	}
	
	private JPanel makeCalendarDisplay()
	{
		
		ActionListener dateSetter = new 
				ActionListener() 
			{
				public void actionPerformed(ActionEvent e)
			    {
					String num = e.getActionCommand();
			        if (!num.equals("")) 
			        {
			          
			          model.setDay(Integer.parseInt(num));
			        }
			    }
			 };
			 
		JPanel monthDisplay = new JPanel(new BorderLayout());

		monthDisplay.add(new JLabel("" + Months[model.getCalendar().get(Calendar.MONTH)]), BorderLayout.NORTH);
		
		
		
		JPanel daysLayout = new JPanel(new GridLayout(0,7));
		
		for(int i = 0; i < 7; i++)
		{
			daysLayout.add(new JLabel(Days[i].toString().substring(0,2)));
		}
		
		
		GregorianCalendar temp = (new GregorianCalendar(model.getCalendar().get(Calendar.YEAR), 
				model.getCalendar().get(Calendar.MONTH),1));
		
		
		for(int i = 1; i < temp.get(Calendar.DAY_OF_WEEK); i++)
		{
			daysLayout.add(new JLabel(""));
		}
	
		for(int i = 0; i < model.getCalendar().getActualMaximum(Calendar.DAY_OF_MONTH); i++)
		{
			JButton calendarDay = new JButton(Integer.toString(i+1));
			if((i+1) == model.getCalendar().get(Calendar.DAY_OF_MONTH))
			{
				calendarDay.setBackground(Color.CYAN);
			}
			else
			{
				calendarDay.setBackground(Color.WHITE);
			}
			calendarDay.addActionListener(dateSetter);
			
			daysLayout.add(calendarDay);			
		}
		monthDisplay.add(daysLayout, BorderLayout.WEST);
	
		return monthDisplay;
	}
	
	@Override
	public void stateChanged(ChangeEvent e) 
	{
		remove(monthDisplay);
		monthDisplay = makeCalendarDisplay();
		this.add(monthDisplay,BorderLayout.WEST);
		
		remove(scrollEventDisplay);
		scrollEventDisplay = makeEventDisplay();
		this.add(scrollEventDisplay, BorderLayout.CENTER);
		
		this.revalidate();
		this.repaint();
	}
}
