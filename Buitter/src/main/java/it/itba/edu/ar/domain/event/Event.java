package it.itba.edu.ar.domain.event;

import it.itba.edu.ar.domain.PersistentModel;
import it.itba.edu.ar.domain.user.User;

import java.util.Comparator;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Event extends PersistentModel {

	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date date;
	@Column
	private String message;
	@ManyToOne
	private User firer;

	Event() {
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		if(date == null)
			throw new IllegalArgumentException();
		this.date = date;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		if(message == null)
			throw new IllegalArgumentException();
		this.message = message;
	}

	public User getFirer() {
		return firer;
	}

	public void setFirer(User firer) {
		if(firer == null)
			throw new IllegalArgumentException();
		this.firer = firer;
	}

	// COMPARATOR
	public static class EventComparator implements Comparator<Event> {
		public int compare(Event d1, Event d2) {
			Date t1 = d1.getDate();
			Date t2 = d2.getDate();
			return t2.compareTo(t1);
		}
	}
}
