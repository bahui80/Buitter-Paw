package it.itba.edu.ar.model;

import java.util.Comparator;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Event extends PersistentModel {
	
	@Column(nullable = false) @Temporal(TemporalType.TIMESTAMP) private Date date;
	@Column private String message;
	@ManyToOne private User firer;

	Event(){
	}
	
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public User getFirer() {
		return firer;
	}

	public void setFirer(User firer) {
		this.firer = firer;
	}

//		public Type getType() {
//		return type;
//	}
//
//	public void setType(Type type) {
//		this.type = type;
//	}

		//COMPARATOR
		public static class EventComparator implements Comparator<Event> {
			public int compare(Event d1, Event d2) {
				Date t1 = d1.getDate();
				Date t2 = d2.getDate();
				return t2.compareTo(t1);
			}
		}
		
		//TYPE
		enum Type {
		   Follow,Mentioned,Rebuit;
		}
}
