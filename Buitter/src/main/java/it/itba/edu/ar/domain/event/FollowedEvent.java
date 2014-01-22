package it.itba.edu.ar.domain.event;

import it.itba.edu.ar.domain.user.User;

import java.util.Date;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorColumn(discriminatorType = DiscriminatorType.INTEGER, name = "type")
@DiscriminatorValue("1")
public class FollowedEvent extends Event{
	
	public FollowedEvent(Date date, User firer) {
		this.setDate(date);
		this.setFirer(firer);
		this.setMessage("started following you");
	}
	
	FollowedEvent() {
	}
	
}
