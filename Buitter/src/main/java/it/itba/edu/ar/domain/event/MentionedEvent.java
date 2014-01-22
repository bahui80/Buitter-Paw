package it.itba.edu.ar.domain.event;

import it.itba.edu.ar.domain.user.User;

import java.util.Date;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorColumn(discriminatorType = DiscriminatorType.INTEGER, name = "type")
@DiscriminatorValue("2")
public class MentionedEvent extends Event {
	
	public MentionedEvent(Date date, User firer) {
		this.setDate(date);
		this.setFirer(firer);
		this.setMessage("mentioned you");
	}
	
	MentionedEvent() {
	}
}
