package it.itba.edu.ar.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public abstract class Event {
	
	@Id @GeneratedValue(strategy=javax.persistence.GenerationType.AUTO)	private Integer eventid;
	private Date date;
	private String message;
	@OneToOne private User user;
	
}
