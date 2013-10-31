package it.itba.edu.ar.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class Event extends PersistentModel {
	
	@Column() private Date date;
	@Column private String message;
	@ManyToOne private User user;
	
}
