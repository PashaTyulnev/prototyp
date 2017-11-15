package com.getraenk.getraenkesoft;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Calendar {
	@Id
	@Column
	@GeneratedValue(strategy=GenerationType.AUTO)
	private String date;
	@Column
	private String event;
		
		
		public Calendar() {}
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


