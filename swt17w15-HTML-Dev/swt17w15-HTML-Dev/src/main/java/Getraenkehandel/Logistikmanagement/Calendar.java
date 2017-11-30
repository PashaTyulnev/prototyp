package Getraenkehandel.Logistikmanagement;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


public class Calendar {
	
	private String date;
	private String event;
		
		
	    public Calendar(String date, String event) {
		super();
		this.date = date;
		this.event = event;
	}

		

		public String getDate() {
			return date;
		}
		public void setDate(String date) {
			this.date = date;
		}
		public String getEvent() {
			return event;
		}
		public void setEvent(String event) {
			this.event = event;
		}
		public void setContent(String content) {
	       
	    }

	}


