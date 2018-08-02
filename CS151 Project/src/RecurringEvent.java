
public class RecurringEvent {
		
		private String eventName;
		private int year;
		private int startingMonth;
		private int endingMonth;
		private String days;
		private int startingTime;
		private int endingTime;

		public RecurringEvent(String eventName, int year, int startingMonth,int endingMonth,
					String days,int startingTime,int endingTime) {
			
			this.eventName = eventName;
			this.year = year;
			this.startingMonth = startingMonth;
			this.endingMonth = endingMonth;
			this.days = days;
			this.startingTime = startingTime;
			this.endingTime = endingTime;
			
		}
		
		public String getEventName() {
			
			return eventName;
		}	
		
		public int getYear() {
			
			return year;
		}	

		public int getStartingMonth() {
			
			return startingMonth;
		}	

		public int getEndingMonth() {
			
			return endingMonth;
		}	

		public String getDays() {
			
			return days;
	
		}	

		public int getStartingTime() {
			
			return startingTime;
		}	

		public int getEndingTime() {
			
			return endingTime;
		}	
		
	}