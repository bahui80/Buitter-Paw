package it.itba.edu.ar.model;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name="buits")
public class Buit extends PersistentModel {

	@Column(length=500, nullable=false, updatable = false)	private String message;
	@ManyToOne private User creator;
	@ManyToMany private Set<Hashtag> hashtags;
	@Temporal(TemporalType.TIMESTAMP) @Column(nullable=false)private Date date;
	
	Buit(){
	}
	
	public Buit(String message, User creator, Set<Hashtag> hashtags, Date date){
		if(message == null || message.length() < 1 || creator == null || date == null || hashtags == null) {
			throw new IllegalArgumentException();
		}
		this.message = message;
		this.hashtags = hashtags;
		this.creator = creator;
		this.date = date;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		if(message == null || message.length() < 1) {
			throw new IllegalArgumentException();
		}
		this.message = message;
	}

	public User getUser() {
		return creator;
	}

	public void setUser(User user) {
		if(user == null)
			throw new IllegalArgumentException();
		this.creator = user;
	}


	
	@Override
	public String toString() {
		return "Buit [message=" + message + ", username="
				+ creator.getUsername() + ", date=" + date + "]";
	}

	public String getDate(){
		return date.toString();
	}
}

